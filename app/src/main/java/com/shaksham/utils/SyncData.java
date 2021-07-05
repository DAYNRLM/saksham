package com.shaksham.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;
import com.shaksham.R;
import com.shaksham.model.database.BaselineSyncData;
import com.shaksham.model.database.BaselineSyncDataDao;
import com.shaksham.model.database.BaslineQuestionSyncData;
import com.shaksham.model.database.BaslineQuestionSyncDataDao;
import com.shaksham.model.database.EvaluationSyncQuestionData;
import com.shaksham.model.database.EvaluationSyncQuestionDataDao;
import com.shaksham.model.database.EvaluationSyncShgData;
import com.shaksham.model.database.EvaluationSyncShgDataDao;
import com.shaksham.model.database.TrainingInfoData;
import com.shaksham.model.database.TrainingInfoDataDao;
import com.shaksham.model.database.TrainingLocationInfo;
import com.shaksham.model.database.TrainingLocationInfoDao;
import com.shaksham.model.database.TrainingModuleInfo;
import com.shaksham.model.database.TrainingModuleInfoDao;
import com.shaksham.model.database.TrainingShgAndMemberData;
import com.shaksham.model.database.TrainingShgAndMemberDataDao;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.presenter.Fragments.BaselineFragment;
import com.shaksham.presenter.Fragments.EvaluationFormFragment;
import com.shaksham.presenter.Fragments.PhotoGps;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SyncData {
    //private Result result;
    private static SyncData syncDataInstance = null;
    private Context context;
    private int success = 0;
    private String loginId;
    private String shgCodeForBaseLine;
    private List<BaslineQuestionSyncData> baslineQuestionSyncDataList;
    private List<BaselineSyncData> baselineSyncDataList;
    private List<TrainingLocationInfo> trainingLocationInfos;
    private List<EvaluationSyncShgData> evaluationSyncData;
    private String BASELINE_URL = AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/sakshamoffline/datasync";
    private String ADD_TRAINING_URL = AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/sakshamofflinetrain/datasync";
    private String EVALUATION_URL = AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/sakshamofflineevaluation/datasync";

    private Handler handler = new Handler();


    public SyncData(Context context) {
        this.context = context;
    }

    public static SyncData getInstance(Context context) {
        if (syncDataInstance == null) {
            syncDataInstance = new SyncData(context);
        }
        return syncDataInstance;
    }

    public synchronized void syncData() {

        if (NetworkFactory.isInternetOn(context.getApplicationContext())) {
            initializeList();

            if (AppConstant.BASELINE_SYNC_KEY.equalsIgnoreCase(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyBaselineSync(), context.getApplicationContext()))) {

                if (baselineSyncDataList != null && baselineSyncDataList.size() > 0) {
                    syncBaseLineData();
                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyBaselineSync(), context.getApplicationContext());
                }
            } else if (AppConstant.ADDTRAINING_SYNC_KEY.equalsIgnoreCase(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyAddTrainningSync(), context.getApplicationContext()))) {

                if (trainingLocationInfos != null && trainingLocationInfos.size() > 0) {
                    syncAddTrainingData();
                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyAddTrainningSync(), context.getApplicationContext());
                }
            } else if (AppConstant.EVALUATION_SYNC_KEY.equalsIgnoreCase(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyEvaluationSync(), context.getApplicationContext()))) {

                if (evaluationSyncData != null && evaluationSyncData.size() > 0) {
                    syncEvaluationData();
                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyEvaluationSync(), context.getApplicationContext());
                }
            } else {
                if (baselineSyncDataList != null && baselineSyncDataList.size() > 0) {
                    syncBaseLineData();
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (trainingLocationInfos != null && trainingLocationInfos.size() > 0) {
                            syncAddTrainingData();
                        }
                    }
                }, 2000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (evaluationSyncData != null && evaluationSyncData.size() > 0) {
                            syncEvaluationData();
                        }
                    }
                }, 2000);

            }
        }
        // showNotification("Info", "work done");
    }

    public void initializeList() {
        loginId = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(), context.getApplicationContext());
        baselineSyncDataList = getbaslineSyncListFromLocalDb();
        trainingLocationInfos = getUnSyncTrainingsFromLocal();
        evaluationSyncData = getEvaluationSyncData();
    }

    private void syncBaseLineData() {

        JSONObject baseLineSyncDataObject = baselineSyncDataObject();
        if (baseLineSyncDataObject != null) {

            /*******create basline dsata is encrypted****/
            JSONObject dataObject = new JSONObject();
            Cryptography cryptography = null;
            try {
                cryptography = new Cryptography();
                dataObject.accumulate("data",cryptography.encrypt(baseLineSyncDataObject.toString()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            /**************************************/



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASELINE_URL, dataObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("baseline_data_Sync").equalsIgnoreCase("Success")) {
                            updateBaselineSyncStatus();
                            // Toast.makeText(context, "baselineResponse" + response, Toast.LENGTH_LONG).show();
                            AppUtility.getInstance().showLog("BaselineSyncDataResponse" + response, SyncData.class);
                        }
                    } catch (JSONException e) {
                        AppUtility.getInstance().showLog("BaselineSyncDataResponseJsonExc"+e,SyncData.class);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AppUtility.getInstance().showLog("BaselineSyncDataResponseError" + error, SyncData.class);
                }
            });
            SingletonVolley.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }


    private synchronized void syncAddTrainingData() {

        JSONArray addTrainingJSONArray = getAddTrainingJSONArray();

        /*******create basline dsata is encrypted****/
        JSONArray dataObject = new JSONArray();
        Cryptography cryptography = null;
        try {
            cryptography = new Cryptography();
            JSONObject jsonObject =new JSONObject();
            jsonObject.accumulate("data",cryptography.encrypt(addTrainingJSONArray.toString()));
            dataObject.put(jsonObject);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**************************************/

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ADD_TRAINING_URL, dataObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.getJSONObject(0).getString("Training Data Sync: ").equalsIgnoreCase("Success")) {
                        updateTrainingSyncTables();
                        //  Toast.makeText(context, "addTrainingResponse" + response, Toast.LENGTH_LONG).show();
                        AppUtility.getInstance().showLog("addTrainingResponse" + response, SyncData.class);
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppUtility.getInstance().showLog("addTrainingError" + error, SyncData.class);
            }
        });
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;

            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                AppUtility.getInstance().showLog("VolleyError"+error,SyncData.class);
            }
        });
        SingletonVolley.getInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private synchronized void syncEvaluationData() {

        /*  JSONObject demo=null;*/
     /*   String s="{\"login_id\":\"MPBOLINCON\",\"evaluationSync\":[{\"shgCode\":\"197731\",\"eval_done\":\"1\",\"eval_year\":\"1\",\"gpslocationcordinate\":\"lat 28.59624309,long 77.19711067\",\"villageCode\":\"1751003057002\",\"evaluationDate\":\"21-04-2020\",\"evaluationShgSyncData\":[{\"questionId\":\"1\",\"answerValue\":\"1\"},{\"questionId\":\"2\",\"answerValue\":\"1\"},{\"questionId\":\"3\",\"answerValue\":\"1\"},{\"questionId\":\"4\",\"answerValue\":\"1\"},{\"questionId\":\"5\",\"answerValue\":\"1\"},{\"questionId\":\"6\",\"answerValue\":\"1\"},{\"questionId\":\"7\",\"answerValue\":\"1\"},{\"questionId\":\"8\",\"answerValue\":\"1\"},{\"questionId\":\"9\",\"answerValue\":\"1\"},{\"questionId\":\"10\",\"answerValue\":\"1\"}]}]}";
        try {
           demo=new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        JSONObject syncEvaluationDataJSONObject = syncEvaluationJSONObject();

        /*******create sync dsata is encrypted****/
        JSONObject dataObject = new JSONObject();
        Cryptography cryptography = null;
        try {
            cryptography = new Cryptography();
            dataObject.accumulate("data",cryptography.encrypt(syncEvaluationDataJSONObject.toString()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**************************************/



        JsonObjectRequest syncBaselineDataRequest = new JsonObjectRequest(Request.Method.POST, EVALUATION_URL, dataObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("evaluationSync").equalsIgnoreCase("Success"))
                        updateEvaluationSyncData();
                    //Toast.makeText(context, "evaluationSyncResponse" + response, Toast.LENGTH_LONG).show();
                    AppUtility.getInstance().showLog("EvalutionResponse" + response, SyncData.class);
                } catch (JSONException e) {
                    AppUtility.getInstance().showLog("EvalutionResponseJsonExc"+e,SyncData.class);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppUtility.getInstance().showLog("EvalutionError" + error, SyncData.class);
            }
        });
        SingletonVolley.getInstance(context.getApplicationContext()).addToRequestQueue(syncBaselineDataRequest);
    }


    public JSONObject baselineSyncDataObject() {

        JSONObject baslineSyncDataObject = new JSONObject();
        if (loginId != null) {
            AppUtility.getInstance().showLog("loginId" + loginId, SyncData.class);
            try {

                baslineSyncDataObject.accumulate("login_id", loginId);
                baslineSyncDataObject.accumulate("imei_no", PrefrenceFactory.getInstance()
                        .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceImei(),context));
                baslineSyncDataObject.accumulate("device_name", PrefrenceFactory.getInstance()
                        .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceInfo(),context));
                baslineSyncDataObject.accumulate("location_coordinate", getCordinates());
                baslineSyncDataObject.accumulate("baseline_data", getBaslineArray());
                AppUtility.getInstance().showLog("baslineSyncDataObject" + baslineSyncDataObject, SyncData.class);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return baslineSyncDataObject;
    }


    public JSONArray getAddTrainingJSONArray() {
        JSONArray jsonArray = new JSONArray();

        if (trainingLocationInfos.size() != 0) {

            JSONObject userInfo = new JSONObject();
            try {

                userInfo.accumulate("login_id", loginId);
                userInfo.accumulate("imei_no", PrefrenceFactory.getInstance()
                        .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceImei(),context));
                userInfo.accumulate("device_name", PrefrenceFactory.getInstance()
                        .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceInfo(),context));
                userInfo.accumulate("location_coordinate", getCordinates());

                jsonArray.put(userInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < trainingLocationInfos.size(); i++) {
                TrainingLocationInfo trainingLocationInfo = trainingLocationInfos.get(i);
                String trainingId = trainingLocationInfo.getTrainingId();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("trainingId", trainingId);
                    jsonObject.accumulate("trainingCreatedTimeStamp", trainingLocationInfo.getDate());
                    jsonObject.accumulate("blockCode", trainingLocationInfo.getBlockId());
                    jsonObject.accumulate("gpCode", trainingLocationInfo.getGpId());
                    jsonObject.accumulate("villageCode", trainingLocationInfo.getVillageId());
                    jsonObject.accumulate("selectedShgCount", Integer.parseInt(trainingLocationInfo.getSelectedShgCount()));
                    jsonObject.accumulate("subselectedShgCount", Integer.parseInt(trainingLocationInfo.getMemberParticipant()));
                    jsonObject.accumulate("otherParticipants", Integer.parseInt(trainingLocationInfo.getOtherParticipant()));
                    jsonObject.accumulate("totalParticipants", Integer.parseInt(trainingLocationInfo.getTotalParticipant()));
                    jsonObject.accumulate("gpslocationcordinate", trainingLocationInfo.getGpsLocation());
                    jsonObject.accumulate("trainingImage", Base64.encodeToString(trainingLocationInfo.getImage(), Base64.DEFAULT));
                    jsonObject.accumulate("selectedShgList", getSelecteShgList(trainingId));

                } catch (JSONException jsonException) {

                    AppUtility.getInstance().showLog("AddTrainingJSONEcxeption" + jsonException, SyncData.class);
                }
                jsonArray.put(jsonObject);
            }

        }
        return jsonArray;
    }



    private List<TrainingLocationInfo> getUnSyncTrainingsFromLocal() {
        SplashActivity.getInstance().getDaoSession().getTrainingLocationInfoDao().detachAll();
        return SplashActivity.getInstance().getDaoSession().getTrainingLocationInfoDao().queryBuilder()
                .where(TrainingLocationInfoDao.Properties.SyncStatus.eq("0"))
                .build().list();
    }

    private JSONArray getSelecteShgList(String trainingId) {

        JSONArray jsonArray = new JSONArray();
        List<TrainingInfoData> trainingInfoDataList = getSelectedShgfromLocal(trainingId);

        for (int i = 0; i < trainingInfoDataList.size(); i++) {
            TrainingInfoData trainingInfoData = trainingInfoDataList.get(i);
            String shgCode = trainingInfoData.getShgCode();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("shgCode", shgCode);
                jsonObject.accumulate("memberList", getSelectedShgMembers(trainingId, shgCode));
                jsonObject.accumulate("selectedModulesList", getSelectedModules(trainingId, shgCode));
            } catch (JSONException e) {
                AppUtility.getInstance().showLog("selectedShgsJSONEXp" + e, SyncData.class);
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    private List<TrainingInfoData> getSelectedShgfromLocal(String trainingId) {
        SplashActivity.getInstance().getDaoSession()
                .getTrainingInfoDataDao().detachAll();
        return SplashActivity.getInstance().getDaoSession()
                .getTrainingInfoDataDao().queryBuilder()
                .where(TrainingInfoDataDao.Properties.TrainingId.eq(trainingId))
                .build().list();
    }

    private JSONArray getSelectedShgMembers(String trainingId, String shgCode) {
        JSONArray jsonArray = new JSONArray();

        List<TrainingShgAndMemberData> trainingShgAndMemberDataList = getSelectedShgMembersFromLocal(trainingId, shgCode);
        for (int i = 0; i < trainingShgAndMemberDataList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("memberCode", trainingShgAndMemberDataList.get(i).getShgMemberCode());
            } catch (JSONException jsonException) {
                AppUtility.getInstance().showLog("AddTrainingMemberListException" + jsonException, SyncData.class);
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    private JSONArray getSelectedModules(String trainingId, String shgCode) {
        JSONArray jsonArray = new JSONArray();
        List<TrainingModuleInfo> selectedModulesFromLocalList = getSelectedModulesFromLocal(trainingId, shgCode);
        for (int i = 0; i < selectedModulesFromLocalList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("moduleId", selectedModulesFromLocalList.get(i).getModuleCode());
            } catch (JSONException jsonExp) {
                AppUtility.getInstance().showLog("AddTrainingSelectedModuleListException" + jsonExp, SyncData.class);
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    private List<TrainingShgAndMemberData> getSelectedShgMembersFromLocal(String trainingId, String shgCode) {
        SplashActivity.getInstance()
                .getDaoSession().getTrainingShgAndMemberDataDao().detachAll();
        QueryBuilder<TrainingShgAndMemberData> trainingShgAndMemberDataQueryBuilder = SplashActivity.getInstance()
                .getDaoSession().getTrainingShgAndMemberDataDao().queryBuilder();
        return trainingShgAndMemberDataQueryBuilder.where(TrainingShgAndMemberDataDao.Properties.TrainingId.eq(trainingId)
                , trainingShgAndMemberDataQueryBuilder.and(TrainingShgAndMemberDataDao.Properties.TrainingId.eq(trainingId)
                        , TrainingShgAndMemberDataDao.Properties.ShgCode.eq(shgCode))).build().list();
    }

    private List<TrainingModuleInfo> getSelectedModulesFromLocal(String trainingId, String shgCode) {
        SplashActivity.getInstance().getDaoSession()
                .getTrainingModuleInfoDao().detachAll();
        QueryBuilder<TrainingModuleInfo> trainingModuleInfoQueryBuilder = SplashActivity.getInstance().getDaoSession()
                .getTrainingModuleInfoDao().queryBuilder();
        return trainingModuleInfoQueryBuilder.where(TrainingModuleInfoDao.Properties.TrainingId.eq(trainingId)
                , trainingModuleInfoQueryBuilder.and(TrainingModuleInfoDao.Properties.TrainingId.eq(trainingId)
                        , TrainingModuleInfoDao.Properties.ShgCode.eq(shgCode)))
                .build().list();
    }

    private Object getBaslineArray() {
        JSONArray jsonBaslineArray = new JSONArray();
        try {
            for (BaselineSyncData baselineSyncData : baselineSyncDataList) {
                JSONObject jsonObject = new JSONObject();
                shgCodeForBaseLine = baselineSyncData.getShgCode();
                String noOfMembersString = baselineSyncData.getEnterMemberValue();
                if (noOfMembersString.equalsIgnoreCase(""))
                    noOfMembersString = "0";
                jsonObject.accumulate("shg_code", shgCodeForBaseLine);
                jsonObject.accumulate("villageCode",baselineSyncData.getVillageCode());
                jsonObject.accumulate("app_created_on",baselineSyncData.getTodayDate());
                jsonObject.accumulate("app_sel_baseline_date",baselineSyncData.getUserSelectedDate());
                jsonObject.accumulate("no_of_members", Integer.parseInt(noOfMembersString));
                jsonObject.accumulate("basline_data", getQuestionArray());
                jsonBaslineArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBaslineArray;
    }

    private Object getQuestionArray() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        try {
            baslineQuestionSyncDataList = getQuestionSyncDetails(shgCodeForBaseLine);
            for (BaslineQuestionSyncData baslineQuestionSyncData : baslineQuestionSyncDataList) {
                jsonObject = new JSONObject();
                // questionId = baslineQuestionSyncData.getQuestionId();
                String answervalue = baslineQuestionSyncData.getAnswerForQuestion();
                if (answervalue == null || answervalue.equalsIgnoreCase(""))
                    answervalue = "0";
                else if (answervalue.equalsIgnoreCase("111"))
                    answervalue="Yes";
                else if (answervalue.equalsIgnoreCase("000"))
                    answervalue="No";

                AppUtility.getInstance().showLog("radio"+answervalue,SyncData.class);
                jsonObject.accumulate("question_code", Integer.parseInt(baslineQuestionSyncData.getQuestionId()));
                // jsonObject.accumulate("question_code", baslineQuestionSyncData.getQuestionId());
                // jsonObject.accumulate("answer", Integer.parseInt(answervalue));
                jsonObject.accumulate("answer", answervalue);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private List<BaselineSyncData> getbaslineSyncListFromLocalDb() {
        SplashActivity.getInstance().getDaoSession().getBaselineSyncDataDao().detachAll();

        return SplashActivity.getInstance().getDaoSession().getBaselineSyncDataDao().queryBuilder()
                .where(BaselineSyncDataDao.Properties.BasLineStatus.eq("0"))
                .build().list();
    }

    private List<BaslineQuestionSyncData> getQuestionSyncDetails(String shgcode) {
        SplashActivity.getInstance().getDaoSession()
                .getBaslineQuestionSyncDataDao().detachAll();
        QueryBuilder<BaslineQuestionSyncData> baslineQuestionSyncDataDaoQueryBuilder = SplashActivity.getInstance().getDaoSession()
                .getBaslineQuestionSyncDataDao()
                .queryBuilder();
        return baslineQuestionSyncDataDaoQueryBuilder.where(BaslineQuestionSyncDataDao.Properties.ShgCode.eq(shgcode)
                , baslineQuestionSyncDataDaoQueryBuilder.and(BaslineQuestionSyncDataDao.Properties.ShgCode.eq(shgcode)
                        , BaslineQuestionSyncDataDao.Properties.BaslineStatus.eq("0")
                )).build().list();
    }

    private List<EvaluationSyncShgData> getEvaluationSyncData() {
        SplashActivity.getInstance()
                .getDaoSession()
                .getEvaluationSyncShgDataDao().detachAll();
        return SplashActivity.getInstance()
                .getDaoSession()
                .getEvaluationSyncShgDataDao()
                .queryBuilder()
                .where(EvaluationSyncShgDataDao.Properties.EvaluationSyncStatus.eq("0"))
                .build()
                .list();
    }


    public JSONObject syncEvaluationJSONObject() {
        JSONObject evaluationSyncMainObject = new JSONObject();

        try {
            evaluationSyncMainObject.accumulate("login_id", loginId);

            evaluationSyncMainObject.accumulate("imei_no", PrefrenceFactory.getInstance()
                    .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceImei(),context));
            evaluationSyncMainObject.accumulate("device_name", PrefrenceFactory.getInstance()
                    .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceInfo(),context));
            evaluationSyncMainObject.accumulate("location_coordinate", getCordinates());

            evaluationSyncMainObject.accumulate("evaluationSync", getShgDetailArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return evaluationSyncMainObject;
    }

    private Object getShgDetailArray() {
        JSONArray evaluationTrainingSyncArray = new JSONArray();

        for (EvaluationSyncShgData evaluationSyncData : evaluationSyncData) {
            JSONObject jsonObject = new JSONObject();
            String evaluationDate  = evaluationSyncData.getEvaluationDate();
            String evaluationMemberCount  = evaluationSyncData.getEvaluationMemberCount();
            String shgCode = evaluationSyncData.getShgCode();
            try {
                jsonObject.accumulate("shgCode", shgCode);
                jsonObject.accumulate("eval_done", evaluationSyncData.getEvaluationType());
                jsonObject.accumulate("eval_year", evaluationSyncData.getEvaluationYear());
                jsonObject.accumulate("gpslocationcordinate", evaluationSyncData.getLatLong());
                jsonObject.accumulate("villageCode",evaluationSyncData.getVillageCode());
                jsonObject.accumulate("evaluationDate", evaluationDate);
                jsonObject.accumulate("evaluationShgSyncData", getQuestionArrayWithShg(shgCode));
                jsonObject.accumulate("evaluationMemberCount",evaluationMemberCount);  //getevaluationMembercount
                evaluationTrainingSyncArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return evaluationTrainingSyncArray;
    }

    private Object getQuestionArrayWithShg( String shgCode) {
        JSONArray evaluationQuestionArray = new JSONArray();

        SplashActivity.getInstance().getDaoSession()
                .getEvaluationSyncQuestionDataDao().detachAll();

        QueryBuilder<EvaluationSyncQuestionData> questionDataQueryBuilder = SplashActivity.getInstance().getDaoSession()
                .getEvaluationSyncQuestionDataDao().queryBuilder();
        List<EvaluationSyncQuestionData> evaluationSyncQuestionDataList = questionDataQueryBuilder
                .where(EvaluationSyncQuestionDataDao.Properties.ShgCode.eq(shgCode)).build().list();


        for (EvaluationSyncQuestionData evaluationSyncQuestionData : evaluationSyncQuestionDataList) {
            JSONObject jsonObject = new JSONObject();
            try {
                // jsonObject.accumulate("questionId", evaluationSyncQuestionData.getQuestionCode());
                String answervalue = evaluationSyncQuestionData.getAnswerValue();
                if (answervalue.equalsIgnoreCase("111"))
                    answervalue="Yes";
                else if (answervalue.equalsIgnoreCase("000"))
                    answervalue="No";
                jsonObject.accumulate("questionId", Integer.parseInt(evaluationSyncQuestionData.getQuestionCode()));
                jsonObject.accumulate("answerValue",answervalue );
                evaluationQuestionArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return evaluationQuestionArray;
    }

    //updating status on local databse
    private void updateBaselineSyncStatus() {

        for (int i = 0; i < baselineSyncDataList.size(); i++) {
            String shgCode = baselineSyncDataList.get(i).getShgCode();
            AppUtility.getInstance().showLog("shgCode" + shgCode, SyncData.class);
            SplashActivity.getInstance()
                    .getDaoSession()
                    .getBaselineSyncDataDao().detachAll();
            List<BaselineSyncData> baselineSyncDataListUpdate = SplashActivity.getInstance()
                    .getDaoSession()
                    .getBaselineSyncDataDao()
                    .queryBuilder()
                    .where(BaselineSyncDataDao.Properties.ShgCode.eq(shgCode))
                    .build()
                    .list();
            for (BaselineSyncData baselineSyncData : baselineSyncDataListUpdate) {
                baselineSyncData.setBasLineStatus("1");
                SplashActivity.getInstance().getDaoSession().getBaselineSyncDataDao().update(baselineSyncData);
            }
            SplashActivity.getInstance()
                    .getDaoSession()
                    .getBaslineQuestionSyncDataDao().detachAll();

            List<BaslineQuestionSyncData> baslineQuestionSyncDataList = SplashActivity.getInstance()
                    .getDaoSession()
                    .getBaslineQuestionSyncDataDao()
                    .queryBuilder()
                    .where(BaslineQuestionSyncDataDao.Properties.ShgCode.eq(shgCode))
                    .build()
                    .list();
            for (BaslineQuestionSyncData baslineQuestionSyncData:baslineQuestionSyncDataList){
                baslineQuestionSyncData.setBaslineStatus("1");
                SplashActivity.getInstance().getDaoSession().getBaslineQuestionSyncDataDao().update(baslineQuestionSyncData);
            }
        }
        baselineSyncDataList.clear();
        new BaselineFragment().saveDbDataIntoBackupFile(context.getApplicationContext());
    }

    private void updateTrainingSyncTables() {

        for (TrainingLocationInfo trainingLocationInfo : trainingLocationInfos) {

            String trainingId = trainingLocationInfo.getTrainingId();
            String gpCode = trainingLocationInfo.getGpId();
            String villageCode = trainingLocationInfo.getVillageId();
            SplashActivity.getInstance().getDaoSession()
                    .getTrainingLocationInfoDao().detachAll();
            QueryBuilder<TrainingLocationInfo> trainingLocationInfoQueryBuilder = SplashActivity.getInstance().getDaoSession()
                    .getTrainingLocationInfoDao()
                    .queryBuilder();
            List<TrainingLocationInfo> trainingLocationInfoList = trainingLocationInfoQueryBuilder
                    .where(TrainingLocationInfoDao.Properties.TrainingId.eq(trainingId)
                            , trainingLocationInfoQueryBuilder.and(TrainingLocationInfoDao.Properties.GpId.eq(gpCode)
                                    , TrainingLocationInfoDao.Properties.VillageId.eq(villageCode)))
                    .build().list();
            for (TrainingLocationInfo trainingLocationInfo1 : trainingLocationInfoList) {
                trainingLocationInfo1.setSyncStatus("1");
                SplashActivity.getInstance().getDaoSession().getTrainingLocationInfoDao().update(trainingLocationInfo1);
            }

        }
        trainingLocationInfos.clear();
        new PhotoGps().saveDbDataInBackUpFile(context.getApplicationContext());
    }

    private void updateEvaluationSyncData() {

        for (EvaluationSyncShgData evaluationSyncDataUp : evaluationSyncData) {
            SplashActivity.getInstance()
                    .getDaoSession().getEvaluationSyncShgDataDao().detachAll();
            QueryBuilder<EvaluationSyncShgData> evaluationSyncDataQueryBuilder = SplashActivity.getInstance()
                    .getDaoSession().getEvaluationSyncShgDataDao().queryBuilder();


            List<EvaluationSyncShgData> evaluationSyncDataList = evaluationSyncDataQueryBuilder
                    .where( EvaluationSyncShgDataDao.Properties.ShgCode.eq(evaluationSyncDataUp.getShgCode())).build().list();


            for (EvaluationSyncShgData evaluationSyncData : evaluationSyncDataList) {
                evaluationSyncData.setEvaluationSyncStatus("1");
                SplashActivity.getInstance().getDaoSession().getEvaluationSyncShgDataDao().update(evaluationSyncData);
            }
        }
        evaluationSyncData.clear();
        new EvaluationFormFragment().saveEvaluationDataInLocalFile(context.getApplicationContext());
    }
    public String getCordinates(){
        String latLong ="";
        GPSTracker gpsTracker = new GPSTracker(context);

        if(!AppUtility.isGPSEnabled(context)){

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
