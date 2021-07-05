package com.shaksham.presenter.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shaksham.R;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.PojoData.GetQuestionValue;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterShgDataDao;
import com.shaksham.model.database.EvaluationSyncQuestionData;
import com.shaksham.model.database.EvaluationSyncShgData;
import com.shaksham.model.database.QuestionInfoDetail;
import com.shaksham.model.database.QuestionInfoDetailDao;
import com.shaksham.model.database.TitleInfoDetail;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.LoginActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.Cryptography;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.FileManager;
import com.shaksham.utils.FileUtility;
import com.shaksham.utils.GPSTracker;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SyncData;
import com.shaksham.view.adaptors.EvaluationQuestionAdapter;
import com.shaksham.view.adaptors.ShgMemberListAdapter;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluationFormFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {


    @BindView(R.id.evaluation_QuestionRv)
    RecyclerView enteredQuestionRV;

    @BindView(R.id.button_save)
    Button saveBtn;

    @BindView(R.id.header2_linearlayout)
    LinearLayout memberdetailLayout;

    @BindView(R.id.evForm_ShgNameTv)
    TextView shgNameTv;
    String evaluationYear,evaluationType,latitude,longitude;

    String shgCode,trainingId;
    List<TitleInfoDetail> titleInfoDetails;
    List<QuestionInfoDetail> questionInfoDetails;
    EvaluationQuestionAdapter evaluationQuestionAdapter;
    List<GetQuestionValue> getQuestionValues;


    public static EvaluationFormFragment newInstance() {
        EvaluationFormFragment evaluationFormFragment = new EvaluationFormFragment();
        return evaluationFormFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.evaluation_form;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.module_Evaluation));
        String shgCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyShgCodeForEvaluation(), getContext());
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),EvaluationFormFragment.class);

        memberdetailLayout.setVisibility(View.GONE);
        ShgMemberListAdapter shgMemberListAdapter = new ShgMemberListAdapter();
        shgNameTv.setText(shgMemberListAdapter.getShgNameFromLocal(shgCode));
        bindData();
        showData();
    }

    private void showData() {
        evaluationQuestionAdapter = new EvaluationQuestionAdapter(questionInfoDetails, getContext());
        enteredQuestionRV.setLayoutManager(new LinearLayoutManager(getContext()));
        enteredQuestionRV.setAdapter(evaluationQuestionAdapter);
        evaluationQuestionAdapter.notifyDataSetChanged();
    }

    private void bindData() {
        String langCode =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLanguageId(),getContext());
        if(langCode.equalsIgnoreCase("")){
            langCode="0";
        }
        titleInfoDetails = SplashActivity.getInstance().getDaoSession().getTitleInfoDetailDao().queryBuilder().build().list();
       // questionInfoDetails = SplashActivity.getInstance().getDaoSession().getQuestionInfoDetailDao().queryBuilder().build().list();
        questionInfoDetails = SplashActivity.getInstance().getDaoSession().getQuestionInfoDetailDao().queryBuilder().where(QuestionInfoDetailDao.Properties.LanguageId.eq(langCode)).build().list();

        for (int i = 0; i < titleInfoDetails.size(); i++) {
            List<QuestionInfoDetail> questionInfoDetails = titleInfoDetails.get(i).getQuestionDataList();
            Gson gson = new Gson();
            String arrayData = gson.toJson(questionInfoDetails);
            AppUtility.getInstance().showLog("data is" + arrayData, EvaluationFormFragment.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

/*
    @Override
    public void onResume() {
        super.onResume();
    }
*/

    @Override
    public void onResume() {
        super.onResume();
        GPSTracker gpsTracker = new GPSTracker(getContext());
        if(!AppUtility.isGPSEnabled(getContext())){
            DialogFactory.getInstance().showAlertDialog(getContext(), R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.gps_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }, "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            },false);
        }else {
            gpsTracker.getLocation();
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);

            //  AddTrainingPojo.addTrainingPojo.setGpsLoation(latitude + "lat"+"," + longitude+"long");
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);

        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         shgCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyShgCodeForEvaluation(),getContext());
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        initilazer();
    }

    private void initilazer() {
        titleInfoDetails = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void doBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(), EvaluationMemberFragment.newInstance(), "", false, R.id.fragmentContainer);
    }

    @OnClick(R.id.button_save)
    public void SaveDone() {
        String answerValue = "";
        getQuestionValues = evaluationQuestionAdapter.getmDataset();
        getLocation();
        for (GetQuestionValue getQuestionValue : getQuestionValues) {
            answerValue = getQuestionValue.getValue();
            if (answerValue.equalsIgnoreCase("")) {
                break;
            }
            AppUtility.getInstance().showLog("insideLoop" + getQuestionValue.getValue(), BaselineFragment.class);
        }
        if (answerValue.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), getString(R.string.toast_evaluation_form_fill_msg), Toast.LENGTH_SHORT).show();
        } else {
            DialogFactory.getInstance().showAlertDialog(getContext(), R.drawable.nrlm, getString(R.string.app_name), getString(R.string.dialog_save_data_for_evaluation)+"lat "+latitude+"long "+longitude, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveInLocalEvaluationSyncDb();
                    dialog.dismiss();
                    if (NetworkFactory.isInternetOn(getContext())) {
                        ProgressDialog progressDialog=DialogFactory.getInstance().showProgressDialog(getContext(),false);
                        progressDialog.show();
                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyEvaluationSync(), AppConstant.EVALUATION_SYNC_KEY, getContext());
                        //SyncData.getInstance(getContext().getApplicationContext()).syncData();
                        SyncData.getInstance(getContext()).syncData();
                        android.os.Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                saveEvaluationDataInLocalFile(getContext());
                                Toast.makeText(getContext(),getContext().getString(R.string.toast_data_saved),Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                AppUtility.getInstance().replaceFragment(getFragmentManager(), EvaluationMemberFragment.newInstance(), EvaluationMemberFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                            }
                        }, 3000);

                    } else {
                        saveEvaluationDataInLocalFile(getContext());
                        Toast.makeText(getContext(),getContext().getString(R.string.toast_data_saved),Toast.LENGTH_SHORT).show();
                        AppUtility.getInstance().replaceFragment(getFragmentManager(), EvaluationMemberFragment.newInstance(), EvaluationMemberFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                    }

                }
            }, "NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(getContext(), getString(R.string.dialog_evaluation_chang_answer), Toast.LENGTH_SHORT).show();
                }
            }, false);

        }

    }
    void getLocation() {
        /*GPSTracker gpsTracker = new GPSTracker(getContext());
        if (gpsTracker.getIsGPSTrackingEnabled()) {
             latitude = String.valueOf(gpsTracker.latitude);
             longitude = String.valueOf(gpsTracker.longitude);
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);
            // Toast.makeText(LoginActivity.this,"lat long"+latitude+"...."+longitude,Toast.LENGTH_SHORT).show();

        } else {
            gpsTracker.showSettingsAlert();

        }
        */
        //----------------
        GPSTracker gpsTracker = new GPSTracker(getContext());
        if(!AppUtility.isGPSEnabled(getContext())){
            DialogFactory.getInstance().showAlertDialog(getContext(), R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.gps_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }, "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            },false);
        }else {
            gpsTracker.getLocation();
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);

            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);

        }

    }


    public void saveEvaluationDataInLocalFile(Context context) {
        String getJson = "";
        getJson = getEvaluationJsonForFile();
        try {
            getJson=new Cryptography().encrypt(getJson);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        FileUtility.getInstance().createFileInMemory(FileManager.getInstance().getPathDetails(context), AppConstant.evaluationFileName, getJson);

        //read file from local file ....
        String fileName = AppConstant.evaluationFileName;
        String filePath = FileManager.getInstance().getPathDetails(context);
        String absloutePath = FileManager.getInstance().getAbslutePathDetails(context, fileName);
        String fie = FileUtility.getInstance().read_file(absloutePath, filePath, fileName);
        AppUtility.getInstance().showLog("ReadFile" + fie, EvaluationFormFragment.class);
    }

    private String getEvaluationJsonForFile() {
        SyncData.getInstance(getContext()).initializeList();
        JSONObject evaluationnDataObject = SyncData.getInstance(getContext()).syncEvaluationJSONObject();
        return evaluationnDataObject.toString();
    }

    private void saveInLocalEvaluationSyncDb() {

        EvaluationSyncQuestionData evaluationSyncQuestionData = new EvaluationSyncQuestionData();
        EvaluationSyncShgData evaluationSyncShgData = new EvaluationSyncShgData();

        getEvaluationDataFromMaster(shgCode);
        evaluationSyncShgData.setTrainingCode("0");
        evaluationSyncShgData.setShgCode(shgCode);
        evaluationSyncShgData.setLatLong("lat "+latitude+","+"long "+longitude);
        evaluationSyncShgData.setVillageCode(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(),getContext()));
        evaluationSyncShgData.setEvaluationSyncStatus("0");
        evaluationSyncShgData.setEvaluationDate(DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));
        evaluationSyncShgData.setTotalMember(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyTotalMemberForEvaluation(),getContext()));
        evaluationSyncShgData.setEvaluationYear(evaluationYear);
        evaluationSyncShgData.setEvaluationType(evaluationType);
        evaluationSyncShgData.setEvaluationMemberCount(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgEnterMemberForEvalution(),getContext())); //Added for evaluation member
        evaluationSyncShgData.setId(null);
        SplashActivity.getInstance().getDaoSession().getEvaluationSyncShgDataDao().insert(evaluationSyncShgData);

        //save question id as per training id and shgcode
        for (GetQuestionValue getQuestionValue : getQuestionValues) {
            evaluationSyncQuestionData.setShgCode(shgCode);
            evaluationSyncQuestionData.setTrainingId("0");
            evaluationSyncQuestionData.setAnswerValue(getQuestionValue.getValue());
           // evaluationSyncQuestionData.setQuestionCode(getQuestionValue.getQuestionId());
            evaluationSyncQuestionData.setQuestionCode(getQuestionValue.getQuestionMainId());
            evaluationSyncQuestionData.setEvaluationSyncStatus("0");
            evaluationSyncQuestionData.setId(null);
            SplashActivity.getInstance().getDaoSession().getEvaluationSyncQuestionDataDao().insert(evaluationSyncQuestionData);
        }

        Toast.makeText(getContext(), getContext().getString(R.string.toast_data_saved), Toast.LENGTH_SHORT).show();
        //clear all preference for evaluation formm
        //update status in add training table for shg wise status
        updateStatusInMasterTables();
       // clearAllEvaluationFragment();
    }

    private void getEvaluationDataFromMaster(String shgCode) {
        EvaluationMasterShgData evaluationMasterShgData = SplashActivity.getInstance()
                .getDaoSession().getEvaluationMasterShgDataDao()
                .queryBuilder().where(EvaluationMasterShgDataDao
                        .Properties.ShgCode.eq(shgCode)).unique();
         evaluationYear = evaluationMasterShgData.getEvaluationYear();

         evaluationType = String.valueOf(Integer.parseInt(evaluationMasterShgData.getEvaluationStatus())+1);

         if(evaluationMasterShgData.getEvaluationStatus().trim().equalsIgnoreCase("4")){
             evaluationYear  =  String.valueOf(Integer.parseInt(evaluationMasterShgData.getEvaluationYear())+1);
             evaluationType = String.valueOf(Integer.parseInt(evaluationMasterShgData.getEvaluationStatus())-3);
         }
    }

    private void clearAllEvaluationFragment() {
        //villagecode,gpcode,blockcode preference remove..
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyBlockcodeForEvaluation(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyGpcodeForEvaluation(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeyShgCodeForEvaluation(), getContext());
    }

    private void updateStatusInMasterTables() {
        //********update shg status in master Added training table if evaluation is done *************
        EvaluationMasterShgData evaluationMasterShgData  = SplashActivity.getInstance()
                .getDaoSession()
                .getEvaluationMasterShgDataDao()
                .queryBuilder().where(EvaluationMasterShgDataDao.Properties.ShgCode.eq(shgCode))
                .limit(1).unique();

        evaluationMasterShgData.setEvaluationdonestatus("1");
        SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().update(evaluationMasterShgData);
    }
}
