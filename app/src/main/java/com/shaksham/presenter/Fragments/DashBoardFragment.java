package com.shaksham.presenter.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shaksham.R;
import com.shaksham.model.database.AddedTrainingShgData;
import com.shaksham.model.database.AddedTrainingShgDataDao;
import com.shaksham.model.database.BaselineSyncDataDao;
import com.shaksham.model.database.EvaluationMasterLocationData;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterShgDataDao;
import com.shaksham.model.database.EvaluationSyncShgDataDao;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.model.database.TrainingLocationInfo;
import com.shaksham.model.database.TrainingLocationInfoDao;
import com.shaksham.model.database.ViewReportTrainingData;
import com.shaksham.model.database.WebRequestData;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.LoginActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.presenter.Activities.VerifyMpinActivity;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SingletonVolley;
import com.shaksham.utils.SyncData;
import com.shaksham.view.adaptors.DashBoardEvaluationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.OnClick;

public class DashBoardFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {

    @BindView(R.id.btnAddTraining)
    CardView addModuleTraining;

    @BindView(R.id.btnReports)
    CardView moduleReports;

    @BindView(R.id.btnEvaluation)
    CardView moduleEvaluation;

    @BindView(R.id.btnBaseline)
    CardView btBaseline;

    @BindView(R.id.unsynced_baselineTV)
    TextView unsyncedBaselineTV;

    @BindView(R.id.unsynced_trainingTV)
    TextView unsyncedTrainingTV;

    @BindView(R.id.unsynced_evaluationTV)
    TextView unsyncedEvaluationTV;

    @BindView(R.id.sync_dataTV)
    TextView syncDataBTN;

    @BindView(R.id.dash_pendingEvaluatingShgTv)
    TextView pendingSHGsTv;

    @BindView(R.id.dashboard_showDetailtv)
    TextView showDetailTv;

    @BindView(R.id.trainingPendingLayout)
    LinearLayout pendingTrainingLayout;

    @BindView(R.id.notification_rv)
    RecyclerView notificationRecyclerview;

    @BindView(R.id.notificationLL)
    LinearLayout notificationLL;

    @BindView(R.id.syncTest)
    Button test;

    @BindView(R.id.baslineDone_tv)
    TextView baslineDoneTv;

    @BindView(R.id.trainingDoneTv)
    TextView trainingDoneTv;
    @BindView(R.id.evaluationDone_tv)
    TextView evaluationDoneTV;


    List<WebRequestData> webRequestDataList;

    private ProgressDialog progressDialog;

    DashBoardEvaluationAdapter dashBoardEvaluationAdapter;
    private Dialog dialog;
    String layOutStatus = "";
    int totalShg,totalBaslineDone,totalTrainingDone;
    Date convertedEvaluationDate, convertedTodayDate, convertedEvaluationMaximunDate, convertedTrainingRegistredDate;

    List<EvaluationMasterLocationData> evaluationMasterLocationData;
    List<EvaluationMasterShgData> evaluationMasterShgData;

    public static List<EvaluationMasterShgData> newCreatedevaluationMasterShgData;
    public static DashBoardFragment newInstance() {
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        return dashBoardFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.dashboard_fragment_layout;
    }

    @Override
    public void onFragmentReady() {
        AppUtility.getInstance().showLog("getActivity()" + getActivity().toString(), DashBoardFragment.class);
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.title_dashbord));
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),DashBoardFragment.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        syncDataBTN.setEnabled(true);
        if (AppConstant.DEMO.equals("demo"))
            view.findViewById(R.id.demo_image).setVisibility(View.VISIBLE);
/*
        syncDataBTN.setBackgroundColor(getResources().getColor(R.color.colorBoxLight));

*/
        progressDialog = DialogFactory.getInstance().showProgressDialog(getContext(), false);

        String villageList = getVillageModifiedList();
        if (!villageList.equalsIgnoreCase("") && !villageList.isEmpty())
            test.setVisibility(View.VISIBLE);
        int unsyncedBaselineCount = SplashActivity.getInstance()
                .getDaoSession().getBaselineSyncDataDao().queryBuilder()
                .where(BaselineSyncDataDao.Properties.BasLineStatus.eq("0")).build().list().size();
        if (unsyncedBaselineCount == 0) {
            unsyncedBaselineTV.setTextColor(getResources().getColor(R.color.color_green));
            unsyncedBaselineTV.setText( " " + unsyncedBaselineCount);
        } else {
            unsyncedBaselineTV.setTextColor(getResources().getColor(R.color.color_red));
            unsyncedBaselineTV.setText(" "+ unsyncedBaselineCount);
        }
        int unsyncedTrainingCount = SplashActivity.getInstance()
                .getDaoSession().getTrainingLocationInfoDao().queryBuilder()
                .where(TrainingLocationInfoDao.Properties.SyncStatus.eq("0")).build().list().size();
        if (unsyncedTrainingCount == 0) {
            unsyncedTrainingTV.setTextColor(getResources().getColor(R.color.color_green));
            unsyncedTrainingTV.setText( " " + unsyncedTrainingCount);
        } else {
            unsyncedTrainingTV.setTextColor(getResources().getColor(R.color.color_red));
            unsyncedTrainingTV.setText(" " + unsyncedTrainingCount);
        }
        int unsyncedEvaluationCount = SplashActivity.getInstance()
                .getDaoSession().getEvaluationSyncShgDataDao().queryBuilder()
                .where(EvaluationSyncShgDataDao.Properties.EvaluationSyncStatus.eq("0")).build().list().size();

        if (unsyncedEvaluationCount == 0) {
            unsyncedEvaluationTV.setTextColor(getResources().getColor(R.color.color_green));
            unsyncedEvaluationTV.setText( " " + unsyncedEvaluationCount);
        } else {
            unsyncedEvaluationTV.setTextColor(getResources().getColor(R.color.color_red));
            unsyncedEvaluationTV.setText( " " + unsyncedEvaluationCount);
        }
        if (unsyncedBaselineCount == 0 && unsyncedTrainingCount == 0 && unsyncedEvaluationCount == 0) {
            syncDataBTN.setVisibility(View.GONE);
        } else syncDataBTN.setVisibility(View.VISIBLE);


        String tDate = DateFactory.getInstance().getTodayDate();
        String todaydate = DateFactory.getInstance().changeDateValue(tDate);
        convertedTodayDate = DateFactory.getInstance().getDateFormate(todaydate);

        //*************
        getAllTrainingListFromLocalDb();

        getBaslineTrainingFromLocal();

    }

    private void getBaslineTrainingFromLocal() {

        List<ShgData> baslineDone = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder().where(ShgDataDao.Properties.BaselineStatus.eq("1")).build().list();
        totalBaslineDone =  baslineDone.size();
        baslineDoneTv.setText(""+totalBaslineDone);
        totalTrainingDone = SplashActivity.getInstance().getDaoSession().getViewReportTrainingDataDao().queryBuilder().build().list().size();
        int trainingLocationInfos = SplashActivity.getInstance().getDaoSession().getTrainingLocationInfoDao().queryBuilder().build().list().size();
        trainingDoneTv.setText(""+(totalTrainingDone+trainingLocationInfos));
        evaluationDoneTV.setText(""+SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().queryBuilder().build().list().size());

    }

    private String  getVillageModifiedList(){

        String villages="";
        syncDataBTN.setVisibility(View.GONE);
        webRequestDataList=SplashActivity.getInstance().getDaoSession().getWebRequestDataDao()
                .queryBuilder().build().list();
        for (int i=0;i<webRequestDataList.size();i++){
            villages+=webRequestDataList.get(i).getVillageCode()+",";
        }
        AppUtility.getInstance().showLog("requestedVillages"+AppUtility.getInstance().removeCommaFromLast(villages.trim()),DashBoardFragment.class);
        /*  String villages="2406036025002";*/
        /*http://10.24.16.2:8080/nrlmwebservice/services/sakshamupdate/assign?user_id=ORANGAJENDRA&village_code=2421004001001*/

        // syncDataBTN.setVisibility(View.GONE);
        return villages;

    }


    private void confirmWebRequest(String userId,String villages){
       /* String CONFIRL_WEB_REQUEST="http://10.24.16.2:8080/nrlmwebservice/services/sakshamupdate/assign?user_id="+userId+"&village_code="+villages;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, CONFIRL_WEB_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppUtility.getInstance().showLog("REsponse"+response,DashBoardFragment.class);

                //****************clear all tables......
                HomeActivity homeActivity = new HomeActivity();
                homeActivity.clearDatabaseMasterTables();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppUtility.getInstance().showLog("CONFIRL_WEB_REQUEST_ERROR"+error,DashBoardFragment.class);
            }
        });
        SingletonVolley.getInstance(getContext().getApplicationContext()).addToRequestQueue(stringRequest);*/

        //************************************
        String CONFIRL_WEB_REQUEST="http://10.24.16.2:8080/nrlmwebservice/services/sakshamupdate/assign?user_id="+userId+"&village_code="+villages;
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, CONFIRL_WEB_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    AppUtility.getInstance().showLog("response"+response, DashBoardFragment.class);
                    HomeActivity homeActivity = new HomeActivity();
                    homeActivity.clearDatabaseMasterTables();
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } catch (Exception e) {
                    progressDialog.dismiss();
                    AppUtility.getInstance().showLog("webRequestExc"+e,DashBoardFragment.class);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AppUtility.getInstance().showLog("webRequestServerError"+error,DashBoardFragment.class);

                //**************************

            }
        });
        SingletonVolley.getInstance(getContext().getApplicationContext()).addToRequestQueue(stringRequest);
    }


    @OnClick(R.id.syncTest)
    public void test() {
        String v = getVillageModifiedList();
        if (!v.equalsIgnoreCase("") && !v.isEmpty())
            test.setVisibility(View.VISIBLE);
        confirmWebRequest(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(), getContext()),AppUtility.getInstance().removeCommaFromLast(v.trim()));
    }




    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handleSSLHandshake();
      /* String v = getVillageModifiedList();
        if (!v.equalsIgnoreCase("") && !v.isEmpty())
            test.setVisibility(View.VISIBLE);
*/

       /* String str = "hiii";
        try {
           String enData =  Encrypt.encrypt(str);
           AppUtility.getInstance().showLog("EncryptData"+enData,DashBoardFragment.class);
           AppUtility.getInstance().showLog("DecrypttData"+ Encrypt.decryptiondata(enData),DashBoardFragment.class);
        } catch (Exception e) {
            e.printStackTrace();
            AppUtility.getInstance().showLog("EncryptData"+e,DashBoardFragment.class);
        }*/
    }

    // ***********get all training list upto today dates****************
    public void getAllTrainingListFromLocalDb() {
        newCreatedevaluationMasterShgData = new ArrayList<>();
        //get total shg pending for evaluation
        evaluationMasterLocationData = SplashActivity.getInstance().getDaoSession().getEvaluationMasterLocationDataDao().queryBuilder().build().list();
        for (EvaluationMasterLocationData evaluationMasterLocationData : evaluationMasterLocationData) {
            String villageCode = evaluationMasterLocationData.getVillageCode();
            evaluationMasterShgData = SplashActivity.getInstance()
                    .getDaoSession()
                    .getEvaluationMasterShgDataDao()
                    .queryBuilder().where(EvaluationMasterShgDataDao.Properties.VillageCode.eq(villageCode), EvaluationMasterShgDataDao.Properties.Evaluationdonestatus.eq("0"))
                    .build().list();

            for (EvaluationMasterShgData evaluationMasterShgData : evaluationMasterShgData) {
                String eDate = DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.getEvaluationDate());
                convertedEvaluationDate = DateFactory.getInstance().getDateFormate(eDate);
                String eMaximumdate = DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.getMaximunEvaluationdate());
                convertedEvaluationMaximunDate = DateFactory.getInstance().getDateFormate(eMaximumdate);
                if (convertedEvaluationDate.compareTo(convertedTodayDate) <= 0) {
                    newCreatedevaluationMasterShgData.add(evaluationMasterShgData);
                    AppUtility.getInstance().showLog("newTrainingListSize" + newCreatedevaluationMasterShgData.size(), DashBoardFragment.class);
                }
            }
        }
        getNotificationList();
    }

    private void  getNotificationList() {
        List<EvaluationMasterShgData> maximumEvaluationMasterShgDataList = new ArrayList<>();
        if (newCreatedevaluationMasterShgData.size() > 0) {
            AppUtility.getInstance().showLog("****SHOW LAYOUT************" + newCreatedevaluationMasterShgData.size(), DashBoardFragment.class);
            for (EvaluationMasterShgData evaluationMasterShgData1 : newCreatedevaluationMasterShgData) {
                String evaluationMaximumDate = DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData1.getMaximunEvaluationdate());
                convertedEvaluationMaximunDate = DateFactory.getInstance().getDateFormate(evaluationMaximumDate);

                if (convertedEvaluationMaximunDate.compareTo(convertedTodayDate) <= 0) {
                    maximumEvaluationMasterShgDataList.add(evaluationMasterShgData1);
                    layOutStatus = "1";
                }

            }
            if (layOutStatus.equalsIgnoreCase("")) {
                pendingTrainingLayout.setVisibility(View.VISIBLE);
                AppUtility.getInstance().showLog("ShowNormalLayout", DashBoardFragment.class);
                pendingSHGsTv.setText(getTotalShgFromSelectedTrainings());
                showDetailTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //************replace with evaluation detail**************
                        AppUtility.getInstance().replaceFragment(getFragmentManager(), EvaluationShgDetailFragment.newInstance("showDetail"), EvaluationShgDetailFragment.class.getSimpleName(), true, R.id.fragmentContainer);
                    }
                });

            } else {
                AppUtility.getInstance().showLog("ShowEvaluationLayout", DashBoardFragment.class);
                // pendingTrainingLayout.setVisibility(View.VISIBLE);
                addModuleTraining.setVisibility(View.GONE);
                moduleReports.setVisibility(View.GONE);
                btBaseline.setVisibility(View.GONE);
                notificationLL.setVisibility(View.VISIBLE);
                dashBoardEvaluationAdapter = new DashBoardEvaluationAdapter(maximumEvaluationMasterShgDataList, getContext());
                notificationRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                notificationRecyclerview.setAdapter(dashBoardEvaluationAdapter);
                dashBoardEvaluationAdapter.notifyDataSetChanged();
            }

        } else {
            pendingTrainingLayout.setVisibility(View.GONE);
            AppUtility.getInstance().showLog("EVALUATION DATA NOT FOUND", DashBoardFragment.class);
        }
    }

    private String getTotalShgFromSelectedTrainings() {
        return "" + newCreatedevaluationMasterShgData.size();

    }

    private void getTotalShg(String tId) {
        List<AddedTrainingShgData> addedTrainingShgData = SplashActivity.getInstance().getDaoSession()
                .getAddedTrainingShgDataDao().queryBuilder()
                .where(AddedTrainingShgDataDao.Properties.TrainingId.eq(tId), AddedTrainingShgDataDao.Properties.EvaluationStatusForShg.eq("0"))
                .build().list();
        totalShg += addedTrainingShgData.size();
        AppUtility.getInstance().showLog("TotalShgs" + totalShg, DashBoardFragment.class);
    }

    @OnClick(R.id.btnEvaluation)
    public void saveClicked() {
        clearAllEvaluationFragment();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), EvaluationShgDetailFragment.newInstance(""), EvaluationShgDetailFragment.class.getSimpleName(), true, R.id.fragmentContainer);
    }

    private void clearAllEvaluationFragment() {
        //villagecode,gpcode,blockcode preference remove..
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyBlockcodeForEvaluation(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyGpcodeForEvaluation(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyShgCodeForEvaluation(), getContext());
    }

    @OnClick(R.id.btnReports)
    public void onClickReport() {

        AppUtility.getInstance().replaceFragment(getFragmentManager(), ViewReport.newInstance(), ViewReport.class.getSimpleName(), true, R.id.fragmentContainer);

    }

    @OnClick(R.id.btnAddTraining)
    public void addLocationFr() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(), AddLocation.getInstance(), AddLocation.class.getSimpleName(), true, R.id.fragmentContainer);
    }

    @OnClick(R.id.sync_dataTV)
    protected void syncData() {

        if (!NetworkFactory.isInternetOn(getContext())) {
            DialogFactory.getInstance().showErrorAlertDialog(getContext(), getString(R.string.NO_INTERNET_TITLE), getString(R.string.INTERNET_MESSAGE), "OK");
        } else {
            syncDataBTN.setEnabled(false);
/*
            syncDataBTN.setBackgroundColor(getResources().getColor(R.color.color_report));
*/
            ProgressDialog progressDialog = DialogFactory.getInstance().showProgressDialog(getContext(), false);
            progressDialog.show();
           // SyncData.getInstance(getContext().getApplicationContext()).syncData();
            SyncData.getInstance(getContext()).syncData();
            android.os.Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                }
            }, 11000);
        }
    }

    @OnClick(R.id.btnBaseline)
    void addlocationFr() {
        clearAllBaslinePreference(getContext());
        BaseLineFilterShgFragment baseLineFilterShgFragment = BaseLineFilterShgFragment.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), baseLineFilterShgFragment, BaseLineFilterShgFragment.class.getSimpleName(), true, R.id.fragmentContainer);
    }

    public void clearAllBaslinePreference(Context context){
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgCodeForBasline(), context);
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgEnterMemberForBasline(), context);
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgTotalMembers(), context);
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedBlockCodeForBasline(),context);
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedGp_codeForBasline(), context);
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(),context);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void doBack() {
        DialogFactory.getInstance().showAlertDialog(getContext(), R.drawable.ic_launcher_background, "hiiii", "go to exit", "yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }, "no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, false);

        AppUtility.getInstance().showLog("count fragment:-", HomeActivity.class);

    }
}

