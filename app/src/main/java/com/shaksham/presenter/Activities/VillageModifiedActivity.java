package com.shaksham.presenter.Activities;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.shaksham.R;
import com.shaksham.model.database.BaselineSyncDataDao;
import com.shaksham.model.database.EvaluationSyncShgDataDao;
import com.shaksham.model.database.TrainingLocationInfoDao;
import com.shaksham.model.database.WebRequestData;
import com.shaksham.presenter.Fragments.DashBoardFragment;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.GPSTracker;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SingletonVolley;
import com.shaksham.utils.SyncData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VillageModifiedActivity extends AppCompatActivity {

    @BindView(R.id.villageModifiedSync_BTN)
    Button syncDataConfirmation;

    @BindView(R.id.tbTitle)
    TextView tollbarTitle;
    @BindView(R.id.unsynced_baselineTVW)
    TextView unsyncedBaselineTV;
    @BindView(R.id.unsynced_trainingTVW)
    TextView unsyncedTrainingTV;
    @BindView(R.id.unsynced_evaluationTVW)
    TextView unsyncedEvaluationTV;

    List<WebRequestData> webRequestDataList;
    private ProgressDialog progressDialog;

    private int unsyncedBaselineCount;
    private int unsyncedTrainingCount;
    private int unsyncedEvaluationCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_modified);
        ButterKnife.bind(this);
        //syncDataConfirmation = (Button)findViewById(R.id.villageModifiedSync_BTN) ;
        tollbarTitle.setText("Sync Data");
        progressDialog = DialogFactory.getInstance().showProgressDialog(VillageModifiedActivity.this, false);

        unsyncedBaselineCount = SplashActivity.getInstance()
                .getDaoSession().getBaselineSyncDataDao().queryBuilder()
                .where(BaselineSyncDataDao.Properties.BasLineStatus.eq("0")).build().list().size();
        if (unsyncedBaselineCount == 0) {
            unsyncedBaselineTV.setTextColor(getResources().getColor(R.color.color_green));
            unsyncedBaselineTV.setText(getString(R.string.unsynced_Bshgs) + " " + unsyncedBaselineCount);
        } else {
            unsyncedBaselineTV.setTextColor(getResources().getColor(R.color.color_red));
            unsyncedBaselineTV.setText(getString(R.string.unsynced_Bshgs) + " " + unsyncedBaselineCount);
        }
        unsyncedTrainingCount = SplashActivity.getInstance()
                .getDaoSession().getTrainingLocationInfoDao().queryBuilder()
                .where(TrainingLocationInfoDao.Properties.SyncStatus.eq("0")).build().list().size();
        if (unsyncedTrainingCount == 0) {
            unsyncedTrainingTV.setTextColor(getResources().getColor(R.color.color_green));
            unsyncedTrainingTV.setText(getString(R.string.unsynced_training) + " " + unsyncedTrainingCount);
        } else {
            unsyncedTrainingTV.setTextColor(getResources().getColor(R.color.color_red));
            unsyncedTrainingTV.setText(getString(R.string.unsynced_training) + " " + unsyncedTrainingCount);
        }
        unsyncedEvaluationCount = SplashActivity.getInstance()
                .getDaoSession().getEvaluationSyncShgDataDao().queryBuilder()
                .where(EvaluationSyncShgDataDao.Properties.EvaluationSyncStatus.eq("0")).build().list().size();

        if (unsyncedEvaluationCount == 0) {
            unsyncedEvaluationTV.setTextColor(getResources().getColor(R.color.color_green));
            unsyncedEvaluationTV.setText(getString(R.string.unsynced_Eshgs) + " " + unsyncedEvaluationCount);
        } else {
            unsyncedEvaluationTV.setTextColor(getResources().getColor(R.color.color_red));
            unsyncedEvaluationTV.setText(getString(R.string.unsynced_Eshgs) + " " + unsyncedEvaluationCount);
        }
    }

    @OnClick(R.id.villageModifiedSync_BTN)
    public void test() {
       // SyncData.getInstance(VillageModifiedActivity.this.getApplicationContext()).syncData();
        SyncData.getInstance(VillageModifiedActivity.this).syncData();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (unsyncedBaselineCount == 0 && unsyncedTrainingCount == 0 && unsyncedEvaluationCount == 0) {

                    String v1 = getVillageModifiedList();
                    if (!v1.equalsIgnoreCase("") && !v1.isEmpty())
                        confirmWebRequest(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(), VillageModifiedActivity.this), AppUtility.getInstance().removeCommaFromLast(v1.trim()));
                }
            }
        },3000);

    }

    private String  getVillageModifiedList(){
        String villages="";
        webRequestDataList=SplashActivity.getInstance().getDaoSession().getWebRequestDataDao()
                .queryBuilder().build().list();
        for (int i=0;i<webRequestDataList.size();i++){
            villages+=webRequestDataList.get(i).getVillageCode()+",";
        }
        AppUtility.getInstance().showLog("requestedVillages"+AppUtility.getInstance().removeCommaFromLast(villages.trim()), DashBoardFragment.class);
        return villages;
    }

    private void confirmWebRequest(String userId,String villages){
        progressDialog.show();


        /****************************************************request for post json *********************************************/
        String JSON_CONFIRM_WEB_REQUEST= AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/sakshamupdate/assign";
        JSONObject confirmRequestObject =new JSONObject();
        try {
            confirmRequestObject.accumulate("login_id",userId);
            confirmRequestObject.accumulate("village_code",villages);
            confirmRequestObject.accumulate("imei_no", PrefrenceFactory.getInstance()
                    .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceImei(),VillageModifiedActivity.this));
            confirmRequestObject.accumulate("device_name", PrefrenceFactory.getInstance()
                    .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceInfo(),VillageModifiedActivity.this));
            confirmRequestObject.accumulate("location_coordinate", getCordinates());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.POST,JSON_CONFIRM_WEB_REQUEST, confirmRequestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                if(response.has("status")){
                    try {
                        String status =response.getString("status");
                        if(status.equalsIgnoreCase("Success")){
                            AppUtility.getInstance().showLog("response" + response, DashBoardFragment.class);
                            HomeActivity homeActivity = new HomeActivity();
                            homeActivity.clearDatabaseMasterTables();
                            PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), VillageModifiedActivity.this);
                            Intent intent = new Intent(VillageModifiedActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else {
                            DialogFactory.getInstance().showAlertDialog(VillageModifiedActivity.this, R.drawable.ic_launcher_background, getString(R.string.server_error_dialog), "Data Sync Failed, Try again!", "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            },false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    DialogFactory.getInstance().showAlertDialog(VillageModifiedActivity.this, R.drawable.ic_launcher_background, getString(R.string.server_error_dialog), "Data Sync Failed, Try again!", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    },false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AppUtility.getInstance().showLog("webRequestServerError"+error,DashBoardFragment.class);

            }
        });
        SingletonVolley.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
      /*****************************************************************************************************************/

      /******************************String request*********************************************************************/
      /*  String CONFIRM_WEB_REQUEST= AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/sakshamupdate/assign?user_id="+userId+"&village_code="+villages;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, CONFIRM_WEB_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    if (new JSONArray(response).getJSONObject(0).getString("status").equalsIgnoreCase("Success")) {
                        AppUtility.getInstance().showLog("response" + response, DashBoardFragment.class);
                        HomeActivity homeActivity = new HomeActivity();
                        homeActivity.clearDatabaseMasterTables();
                        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), VillageModifiedActivity.this);
                        Intent intent = new Intent(VillageModifiedActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else {
                        DialogFactory.getInstance().showAlertDialog(VillageModifiedActivity.this, R.drawable.ic_launcher_background, getString(R.string.server_error_dialog), "Data Sync Failed, Try again!", "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        },false);
                    }

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
            }
        });
        SingletonVolley.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);*/
    }


    public String getCordinates(){
        String latLong ="";
        GPSTracker gpsTracker = new GPSTracker(VillageModifiedActivity.this);
        if(!AppUtility.isGPSEnabled(VillageModifiedActivity.this)){

        }else {
            gpsTracker.getLocation();
            String  latitude = String.valueOf(gpsTracker.latitude);
            String longitude = String.valueOf(gpsTracker.longitude);
            latLong =latitude+","+longitude;
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, SplashActivity.class);
        }
        return latLong;
    }

}
