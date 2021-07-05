package com.shaksham.presenter.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.shaksham.BuildConfig;
import com.shaksham.R;
import com.shaksham.model.database.BaselineSyncData;
import com.shaksham.model.database.BaslineQuestionSyncData;
import com.shaksham.model.database.BlockData;
import com.shaksham.model.database.BlockLevelData;
import com.shaksham.model.database.EvaluationSyncQuestionData;
import com.shaksham.model.database.EvaluationSyncShgData;
import com.shaksham.model.database.EvaluationMasterLocationData;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterTrainingData;
import com.shaksham.model.database.GpData;
import com.shaksham.model.database.LoginInfo;
import com.shaksham.model.database.ModuleData;
import com.shaksham.model.database.QuestionInfoDetail;
import com.shaksham.model.database.QuestionInfoDetailDao;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgMemberData;
import com.shaksham.model.database.ShgModuleData;
import com.shaksham.model.database.TitleInfoDetail;
import com.shaksham.model.database.TitleInfoDetailDao;
import com.shaksham.model.database.TrainingInfoData;
import com.shaksham.model.database.TrainingLocationInfo;
import com.shaksham.model.database.TrainingModuleInfo;
import com.shaksham.model.database.TrainingShgAndMemberData;
import com.shaksham.model.database.ViewReportData;
import com.shaksham.model.database.ViewReportModuleData;
import com.shaksham.model.database.ViewReportMonthData;
import com.shaksham.model.database.ViewReportTrainingData;
import com.shaksham.model.database.VillageData;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.Cryptography;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.FileManager;
import com.shaksham.utils.FileUtility;
import com.shaksham.utils.GPSTracker;
import com.shaksham.utils.PermissionHelper;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SingletonVolley;
import com.shaksham.utils.SyncData;
import com.shaksham.view.adaptors.SelectLanguageAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.prefs.PreferencesFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.shaksham.R.string.SERVER_ERROR_MESSAGE;
import static com.shaksham.utils.AppConstant.MAX_LOGIN_ATTEMPTS;
public class LoginActivity extends AppCompatActivity {
    public static Context context;
    private String userId, password, mobileNoFromFile;
    private TextInputEditText user_IdTIET, passwordTIET, mobileNoTIET;
    private Button loginBTN, sendOtpBTN;
    private boolean checkPermision;
    private TextView forgotPasswordTV, selectLanguageTV,login_attempts_remainTV;
    private Dialog dialog;
    private TelephonyManager telephonyManager;
    private ProgressDialog progressDialog;
    private int lastLoginCount;
    Runnable r1;
    Handler handler;
    String loginStatus="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (AppConstant.DEMO.equals("demo"))
            findViewById(R.id.demo_image).setVisibility(View.VISIBLE);
        context = LoginActivity.this;

        handler =new Handler();
        user_IdTIET = (TextInputEditText) findViewById(R.id.user_IdTIET);
        passwordTIET = (TextInputEditText) findViewById(R.id.passwordTIET);
        selectLanguageTV = (TextView) findViewById(R.id.select_languageTV);
        loginBTN = (Button) findViewById(R.id.loginBTN);
        forgotPasswordTV = (TextView) findViewById(R.id.forgotPasswordTV);
        if (AppConstant.DEMO.equalsIgnoreCase("demo"))
            forgotPasswordTV.setVisibility(View.GONE);
        login_attempts_remainTV=(TextView)findViewById(R.id.login_attempts_remainTV);
        progressDialog = DialogFactory.getInstance().showProgressDialog(LoginActivity.this, false);

        checkPermision = PermissionHelper.getInstance(context).checkAndRequestPermissions();
       // AppUtility.getInstance().showLog("checkPermision" + checkPermision, LoginActivity.class);
       // setSpinnerView();
        setSelectedlanguageView(selectLanguageTV);
        //handleSSLHandshake();

        user_IdTIET.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        user_IdTIET.setLongClickable(false);
        passwordTIET.setLongClickable(false);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                userId = user_IdTIET.getText().toString().trim().toUpperCase();
                password = passwordTIET.getText().toString().trim();
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyPassword(),password,LoginActivity.this);

                if (userId == null || userId.equalsIgnoreCase("") || userId.length() < 4) {
                    user_IdTIET.setError("Please enter valid user id. ");
                } else if (password.length() < 6) {
                    passwordTIET.setError(getString(R.string.error_password));
                } else {
                   // AppUtility.getInstance().showLog("userId=" + userId + "password=" + password, LoginActivity.class);
                    if (NetworkFactory.isInternetOn(LoginActivity.this)) {
                        //check remining unsync data if exist then not hit the api
                        if (checkPermision) {
                            progressDialog.show();
                            try{
                               new HomeActivity().clearDatabaseMasterTables();

                                populateDbFromLocalFile();


                                // getLocation();
                                AppUtility.getInstance().showLog("encoded password" + AppUtility.getInstance().getSha256(password), LoginActivity.class);
                                AppUtility.getInstance().showLog("deviceInfo=" + getDeviceInfo() + ",,,,,imei1=" + getIMEINo1(), LoginActivity.class);
                               // Toast.makeText(LoginActivity.this,"imei"+getIMEINo1()+"Dinfo"+getDeviceInfo(),Toast.LENGTH_SHORT).show();
                                // Toast.makeText(LoginActivity.this,"Dinfo"+getDeviceInfo(),Toast.LENGTH_SHORT).show();
                              //  AppUtility.getInstance().showLog("getAppVersionFromLocal=" + getAppVersionFromLocal(), LoginActivity.class);
                                //  AppUtility.getInstance().showLog("getMobileNoFromLocal" + getMobileNoFromLocal(), LoginActivity.class);
                              //  AppUtility.getInstance().showLog("todaydate:-" + DateFactory.getInstance().getTodayDate(), LoginActivity.class);
                              //  AppUtility.getInstance().showLog("changedValue=-" + DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()), LoginActivity.class);
                               // SyncData.getInstance(getApplicationContext()).syncData();


                                SyncData.getInstance(LoginActivity.this).syncData();

                                getMastersFromServer(userId, AppUtility.getInstance().getSha256(password) ,
                                        getIMEINo1(), getDeviceInfo(), getAppVersionFromLocal(),
                                        DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));


                            }catch (Exception e){
                                //Toast.makeText(LoginActivity.this,"ExceptionMain"+e,Toast.LENGTH_SHORT).show();
                               // AppUtility.getInstance().showLog("ExceptionMain"+e,LoginActivity.class);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Please allow the permissions.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.NO_INTERNET_TITLE), getString(R.string.INTERNET_MESSAGE), "OK");
                    }
                }
            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = DialogFactory.getInstance().showCustomDialog(LoginActivity.this, R.layout.forgot_passdialog);
                mobileNoTIET = (TextInputEditText) dialog.findViewById(R.id.mobileNoET);
                sendOtpBTN = (Button) dialog.findViewById(R.id.send_otpBTN);
                dialog.show();

                sendOtpBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mobileNo = mobileNoTIET.getText().toString().trim();
                        if (mobileNo.length() < 10) {
                            mobileNoTIET.setError(getString(R.string.error_mobileno));
                        } else {
                            JSONObject mpinFileObject= null;
                            try {
                                mpinFileObject = readMpinFile();
                               // AppUtility.getInstance().showLog("mpinFileObject"+mpinFileObject,OTPVerification.class);
                                if (mpinFileObject.toString().equalsIgnoreCase("{}")){
                                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyMobileNumber(), mobileNo, LoginActivity.this);
                                    //save userid in preference for reset password
                                    sendOTP(mobileNo);
                                    //Toast.makeText(LoginActivity.this,getString(R.string.toast_nologinid),Toast.LENGTH_SHORT).show();
                                }else {
                                    String mobileFromFile=  mpinFileObject.getString("mobileNumber");
                                   // AppUtility.getInstance().showLog("mpinFileObject"+mpinFileObject,LoginActivity.class);
                                    if(mobileNo.equalsIgnoreCase(mobileFromFile)){
                                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyMobileNumber(), mobileNo, LoginActivity.this);
                                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(),mpinFileObject.getString("loginId"),LoginActivity.this);
                                        sendOTP(mobileNo);
                                    }else {
                                        Toast.makeText(LoginActivity.this,getString(R.string.toast_registered_mobile),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                AppUtility.getInstance().showLog("readMpinFileExc"+e,LoginActivity.class);
                            }
                        }
                    }
                });
            }
        });
    }




    void getLocation() {
        GPSTracker gpsTracker = new GPSTracker(LoginActivity.this);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            String latitude = String.valueOf(gpsTracker.latitude);
            String longitude = String.valueOf(gpsTracker.longitude);
           // AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);
            // Toast.makeText(LoginActivity.this,"lat long"+latitude+"...."+longitude,Toast.LENGTH_SHORT).show();

        } else {
            gpsTracker.showSettingsAlert();

        }


    }

    public String getAppVersionFromLocal() {

        String appVersion = BuildConfig.VERSION_NAME;
       /* List<LoginInfo> loginInfoList = SplashActivity.getInstance()
                .getDaoSession()
                .getLoginInfoDao()
                .queryBuilder()
                .limit(1).list();
       // AppUtility.getInstance().showLog("loginInfoList" + loginInfoList, LoginActivity.class);
        if (loginInfoList.size() == 0) {
            try {
                if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpin(), AppConstant.mpinFileName)) {
                    JSONObject mPinFileObject = readMpinFile();
                   *//* appVersion = mPinFileObject.getString("appVersion");*//*
                    appVersion="1.0.0";
                    mobileNoFromFile = mPinFileObject.getString("mobileNumber");
                   // AppUtility.getInstance().showLog("appVersion" + appVersion + "mobileNoFromFile" + mobileNoFromFile, LoginActivity.class);
                } else appVersion = "1.0.0";

            } catch (JSONException je) {
               // AppUtility.getInstance().showLog("jsonExp" + je, LoginActivity.class);
            }
        } else {
            //PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(),loginInfoList.get(0).getLoginId(),LoginActivity.this);
            appVersion = loginInfoList.get(0).getAppVersion();
        }*/
        return appVersion;
    }

    public String getMobileNoFromLocal() {
        String mobileFromLocal = "";
        List<LoginInfo> loginInfoList = SplashActivity.getInstance()
                .getDaoSession().
                        getLoginInfoDao()
                .queryBuilder().limit(1).list();

        if (loginInfoList.size() == 0) {
            mobileFromLocal = mobileNoFromFile;
        } else {
            mobileFromLocal = loginInfoList.get(0).getMobileNo();
        }
        return mobileFromLocal;
    }

    public String getLoginIdFromLocal() {
        String loginId = "";
        List<LoginInfo> loginInfoList = SplashActivity.getInstance()
                .getDaoSession().
                        getLoginInfoDao()
                .queryBuilder().limit(1).list();

        if (loginInfoList.size() == 0) {
            loginId = getString(R.string.NOT_AVAILABLE);
        } else {
            loginId = loginInfoList.get(0).getLoginId();
        }
        return loginId;
    }

    private void setSelectedlanguageView(TextView selectLanguageTV) {

        List<String> languagesList = new ArrayList<String>();
        languagesList.add("hindi");
        languagesList.add("hindi");
        languagesList.add("hindi");
        languagesList.add("hindi");
        languagesList.add("hindi");
        languagesList.add("hindi");
        languagesList.add("hindi");
        languagesList.add("hindi");
        languagesList.add("hindi");
        selectLanguageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = DialogFactory.getInstance().showCustomDialog(LoginActivity.this, R.layout.select_language_dialog);
                RecyclerView selectLanguageRV = (RecyclerView) dialog.findViewById(R.id.select_languageRV);
                TextView selectedLanguageTV = (TextView) dialog.findViewById(R.id.selected_languageTV);
                SelectLanguageAdaptor selectLanguageAdaptor = new SelectLanguageAdaptor(LoginActivity.this, languagesList, dialog, selectedLanguageTV);
                selectLanguageRV.setLayoutManager(new LinearLayoutManager(LoginActivity.this));
                selectLanguageRV.setAdapter(selectLanguageAdaptor);
                selectLanguageAdaptor.notifyDataSetChanged();

                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });

    }

    private void setSpinnerView() {
    }

    /*  public void setUpOTP(String mNumber) {
          otp = getRandomOtp();
          encodedMsg = getEncodedMsg(otp);
          AppUtility.getInstance().showLog(""+encodedMsg,LoginActivity.class);
          ProjectPrefrences.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyOtp(), otp, context);
      }
  */
    private String getEncodedMsg(String otp) {
        String hexString = "";
        for (char ch : otp.trim().toCharArray()) {
            hexString = hexString.trim().concat(String.format("%04x", (int) ch));
        }
        return hexString;
    }


    public String getRandomOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(8999);
        Toast.makeText(context, "OTP is: " + otp + "", Toast.LENGTH_LONG).show();
        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyOtp(), String.valueOf(otp), LoginActivity.this);
        return "" + otp;
    }

    private void sendOTP(String mobileNo) {

        if (!NetworkFactory.isInternetOn(LoginActivity.this)) {
            DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.NO_INTERNET_TITLE), getString(R.string.INTERNET_MESSAGE), "OK");
        } else {
            String json_NEW_OTP_URL= AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/nrlmwebservice/services/forgotpassword/message";
            ProgressDialog progressDialog = DialogFactory.getInstance().showProgressDialog(LoginActivity.this, false);
            progressDialog.show();
            /**************************************json request for otp*********************************************/
            JSONObject otpObject =new JSONObject();
            try {
                otpObject.accumulate("mobileno",mobileNo);
                otpObject.accumulate("message",getString(R.string.otp_greeting)+ " "+ getRandomOtp()+ " "+getString(R.string.otp_massage));
              //  otpObject.accumulate("login_id", userId);
               /* otpObject.accumulate("imei_no", PrefrenceFactory.getInstance()
                        .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceImei(), LoginActivity.this));
                otpObject.accumulate("device_name", PrefrenceFactory.getInstance()
                        .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceInfo(), LoginActivity.this));
                otpObject.accumulate("location_coordinate", getCordinates());*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest otpRequest =new JsonObjectRequest(Request.Method.POST, json_NEW_OTP_URL, otpObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    try {
                        if(response.has("status")){
                           // AppUtility.getInstance().showLog("mobileresponse"+response.toString(),LoginActivity.class);
                            AppUtility.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true);
                        }


                    } catch (Exception e) {
                      //  AppUtility.getInstance().showLog("Exception" + e.toString(), LoginActivity.class);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    AppUtility.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true);

                  //  DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.SERVER_ERROR_TITLE), getString(SERVER_ERROR_MESSAGE), "ok");
                   // AppUtility.getInstance().showLog("volleyError" + error, LoginActivity.class);

                }
            });
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.getCache().clear();
                requestQueue.add(otpRequest);
            } catch (Exception e) {
                e.printStackTrace();
                AppUtility.getInstance().showLog("Volley Exception:" + e, LoginActivity.class);
            }


            /************************************************************************************************************/

            /*********************************string request***********************************************************************/

           /* String NEW_OTP_URL="https://nrlm.gov.in/nrlmwebservice/services/forgotpassword/message?mobileno="+mobileNo +"&message="+getString(R.string.otp_greeting)+ " "+ getRandomOtp()+ " "+getString(R.string.otp_massage);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, NEW_OTP_URL, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(String s) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    try {
                        AppUtility.getInstance().showLog("mobileresponse"+s,LoginActivity.class);
                        AppUtility.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true);

                    } catch (Exception e) {
                        AppUtility.getInstance().showLog("Exception" + e.toString(), LoginActivity.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.SERVER_ERROR_TITLE), getString(SERVER_ERROR_MESSAGE), "ok");
                    AppUtility.getInstance().showLog("volleyError" + volleyError, LoginActivity.class);
                }
            });
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
                AppUtility.getInstance().showLog("Volley Exception:" + e, LoginActivity.class);
            }*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(r1);
        if(!AppUtility.isGPSEnabled(LoginActivity.this)){

            DialogFactory.getInstance().showAlertDialog(LoginActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.gps_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
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
            GPSTracker gpsTracker = new GPSTracker(LoginActivity.this);
            gpsTracker.getLocation();
            String latitude = String.valueOf(gpsTracker.latitude);
            String longitude = String.valueOf(gpsTracker.longitude);
           // AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);
            // Toast.makeText(LoginActivity.this,"lat long"+latitude+"...."+longitude,Toast.LENGTH_SHORT).show();
        }

        /**********this is used for kill app in forground after 15 min****************/
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);;
            }
        },(30*60000));

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        PermissionHelper.getInstance(LoginActivity.this).requestPermissionResult(PermissionHelper.REQUEST_ID_MULTIPLE_PERMISSIONS, permissions, grantResults);
        checkPermision = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getIMEINo1() {
        String imeiNo1 = "";
        try {
            if (getSIMSlotCount() > 0) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                }
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imeiNo1 = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    //Toast.makeText(getApplicationContext(),"10serialno"+android.os.Build.getSerial(),Toast.LENGTH_LONG).show();
                    //imeiNo1 = "0361911b215ca1f6";
                   // Toast.makeText(LoginActivity.this,"deviceIMEI>10Q="+imeiNo1,Toast.LENGTH_LONG).show();
                   // AppUtility.getInstance().showLog("imeiNo1"+imeiNo1,LoginActivity.class);
                } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imeiNo1 = telephonyManager.getDeviceId(0);

                    //imeiNo1 = "356479089355768";//odisa wale ka imei8

                  //  Toast.makeText(LoginActivity.this,"deviceIMEI<10Q="+imeiNo1,Toast.LENGTH_LONG).show();
                   // AppUtility.getInstance().showLog("imeiNo1=" + imeiNo1, LoginActivity.class);
                }

            } else imeiNo1 = telephonyManager.getDeviceId();
        }catch (Exception e){
           // AppUtility.getInstance().showLog("IMEIExc"+e,LoginActivity.class);
          //  Toast.makeText(LoginActivity.this,"IMEIexception"+e,Toast.LENGTH_SHORT).show();
        }
        /*if(imeiNo1==null)
            return imeiNo1="";*/
       // Toast.makeText(LoginActivity.this,"2deviceIMEI>10Q="+imeiNo1,Toast.LENGTH_SHORT).show();
      //  return "868985038356245";862144048163516
      // return "862144048163516";
      // return "862144048163519";//manipur MNIELUCKYBH
      // return "8621440481635199";//rohtak HRROMANIKU
        //return "66147e2640df868b";  //West Bengal HRROWBBENGAL
       // return "869328021803031";  //Chhattishgarh CHHATTISGARH
        return imeiNo1;
       // return "865402033738727";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String getIMEINo2() {
        String imeiNo2 = "";
        if (getSIMSlotCount() > 1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imeiNo2 = telephonyManager.getDeviceId(1);
               // AppUtility.getInstance().showLog("imeiNo2=" + imeiNo2, LoginActivity.class);
            }

        } else imeiNo2 = telephonyManager.getDeviceId();

        return imeiNo2;
    }

    public String getDeviceInfo() {
        String deviceInfo = "";

        try{
            deviceInfo = Build.MANUFACTURER + "-" + Build.DEVICE + "-" + Build.MODEL;
            //deviceInfo = "motorola-nicklaus_f-Moto E (4) Plus";
            //{"status":"motorola-nicklaus_f-Moto E (4) Plus"}
        }catch (Exception e){
           // Toast.makeText(LoginActivity.this,"Exception"+e,Toast.LENGTH_SHORT).show();
           // AppUtility.getInstance().showLog("ExceptionMain"+e,LoginActivity.class);
        }

       // AppUtility.getInstance().showLog("deviceInfo=" + deviceInfo, LoginActivity.class);


        if (deviceInfo.equalsIgnoreCase("")|| deviceInfo==null)
            return "";
       // return "Xiaomi-riva-Redmi 5A";
       // return "vivo-1951-vivo 1951";
       // return "vivo-1951-vivo 1952";// manipur MNIELUCKYBH
      //  return "vivo-1951-vivo 19500";//rohtak HRROMANIKU
       // return "Xiaomi-olive-Redmi 8";    // West Bengal HRROWBBENGAL nrlm@123

       // return "GiONEE-GiONEE_BBL7332-M5_lite"; //Chhattishgarh CHHATTISGARH  nrlm@123
        return deviceInfo;
       // return "Xiaomi-rolex-Redmi 4A";
    }

    private int getSIMSlotCount() {
        int getPhoneCount = 0;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPhoneCount = telephonyManager.getPhoneCount();
            }

        return getPhoneCount;
    }

    public void getMastersFromServer(String userId, String encodedPassword, String imeiNo, String deviceInfo, String appVersion, String todayDate) {

        if (!NetworkFactory.isInternetOn(LoginActivity.this)) {
            DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.NO_INTERNET_TITLE), getString(R.string.INTERNET_MESSAGE), "OK");
        } else {
            String MASTER_URL = AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/saksham/data?user_id="
                    + userId + "&user_password="
                    + encodedPassword + "&IMEI="
                    + imeiNo + "&device_name="
                    + deviceInfo + "&app_version="
                    + appVersion + "&date="
                    + todayDate;
            PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyDeviceImei(),imeiNo,LoginActivity.this);
            PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyDeviceInfo(),deviceInfo,LoginActivity.this);

            /***********************************this request for post************************************************************/
            String loginURL =AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/saksham/data";
            JSONObject masterUrlObject =new JSONObject();
            try {
                masterUrlObject.accumulate("user_id",userId);
                masterUrlObject.accumulate("user_password",encodedPassword);
                masterUrlObject.accumulate("IMEI",imeiNo);//"55b27d14c744afb5"
                masterUrlObject.accumulate("device_name",deviceInfo);
                masterUrlObject.accumulate("app_version",appVersion);
                masterUrlObject.accumulate("date",todayDate);
                masterUrlObject.accumulate("logout_time",getTimeStampFromPreference());
                masterUrlObject.accumulate("location_coordinate",getCordinateFromPreference());
                masterUrlObject.accumulate("app_login_time",DateFactory.getInstance().getDateTime());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*******make json object is encrypted and *********/
            JSONObject encryptedObject =new JSONObject();
            try {
                Cryptography cryptography = new Cryptography();

                encryptedObject.accumulate("data",cryptography.encrypt(masterUrlObject.toString()));
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
            /***********************************************/


            JsonObjectRequest loginRequest =new JsonObjectRequest(Request.Method.POST, loginURL, encryptedObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        AppUtility.getInstance().showLog("response" + response, LoginActivity.class);
                        JSONObject jsonObject = null;
                        String objectResponse="";
                       // jsonObject = new JSONObject(response);
                        if(response.has("data")){
                            objectResponse=response.getString("data");
                        }else {
                            jsonObject=response;
                        }
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            try {
                                Cryptography cryptography = new Cryptography();
                                jsonObject = new JSONObject(cryptography.decrypt(objectResponse));
                                AppUtility.getInstance().showLog("responseJSON" + jsonObject.toString(), LoginActivity.class);
                            } catch (Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Data not found!", Toast.LENGTH_SHORT).show();
                                AppUtility.getInstance().showLog("DecryptEx" + e, LoginActivity.class);
                            }
                        }
                        if (!(jsonObject == null)) {
                            if (jsonObject.has("status")) {
                                int logoutCount=jsonObject.getInt("login_attempt");
                                if(logoutCount>6){
                                    login_attempts_remainTV.setText("Please wait until your credential unlocked.");
                                    login_attempts_remainTV.setVisibility(View.VISIBLE);
                                }else {
                                    login_attempts_remainTV.setText(getString(R.string.login_attempts_msgS)+" "+ String.valueOf(AppConstant.MAX_LOGIN_ATTEMPTS-(logoutCount))+" "+getString(R.string.login_attempts_msgE));
                                    login_attempts_remainTV.setVisibility(View.VISIBLE);
                                }
                                progressDialog.dismiss();
                                String status = jsonObject.getString("status");
                                if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_userId))) {
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.info), getString(R.string.server_error_invalid_userId_massege), "OK", null, null, true, false);
                                } else if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_password))){
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.info), getString(R.string.server_error_invalid_userId_massege), "OK", null, null, true, false);
                                }else if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_version))) {
                                   DialogFactory.getInstance().showAlertDialog(context, 1, getString(R.string.server_error_invalid_version), getString(R.string.server_error_invalid_version_massege),
                                           "Update", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   //write code for update application
                                                   updateApplication();


                                               }
                                           }, "Cancle", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {

                                                   dialog.dismiss();
                                               }
                                           },false
                                   );
                                } else if (status.equalsIgnoreCase("Invalid Date !!!")) {
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_invalid_date), getString(R.string.server_error_invalid_date_massege), "OK", null, null, true, false);
                                } else if(status.equalsIgnoreCase(" Please wait for 15 minutes you exceed limit more than 5 !!!")){
                                    login_attempts_remainTV.setText("Please wait until your credential unlocked.");
                                    login_attempts_remainTV.setVisibility(View.VISIBLE);
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.info),
                                            getString(R.string.login_attempt_failed), "OK",
                                            null, null, true,
                                            false);
                                }else {
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_device_info), getString(R.string.server_error_device_info_massege) + " (" + status + ") " + getString(R.string.device), "OK", null, null, false, false);
                                }
                            } else {
                                parseServerData(jsonObject);
                            }
                        }
                } catch (JSONException jsonException) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Data not found!", Toast.LENGTH_SHORT).show();
                        AppUtility.getInstance().showLog("jsonException" + jsonException, LoginActivity.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AppUtility.getInstance().showLog("error" +error, LoginActivity.class);
                }
            });
            loginRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 30000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 1;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {


                }
            });
            SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);
          /**************************************this request for get string request*********************************************************/

           /* StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, MASTER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        AppUtility.getInstance().showLog("response" + response, LoginActivity.class);
                        JSONObject jsonObject = null;
                        //jsonObject = new JSONObject(response);
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            try {
                                Cryptography cryptography = new Cryptography();
                                jsonObject = new JSONObject(cryptography.decrypt(response));
                                AppUtility.getInstance().showLog("responseJSON" + jsonObject.toString(), LoginActivity.class);
                            } catch (Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.data_not_found_tost), Toast.LENGTH_SHORT).show();
                                AppUtility.getInstance().showLog("DecryptEx" + e, LoginActivity.class);
                            }
                        }
                        if (!(jsonObject == null)) {
                            if (jsonObject.has("status")) {
                                progressDialog.dismiss();
                                String status = jsonObject.getString("status");
                                if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_userId))) {
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.info), getString(R.string.server_error_invalid_userId_massege), "OK", null, null, true, false);
                                } else if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_password))) {
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.info), getString(R.string.server_error_invalid_userId_massege), "OK", null, null, true, false);
                                } else if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_version))) {
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_invalid_version), getString(R.string.server_error_invalid_version_massege), "OK", "Cancle", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //write code for app update
                                            dialog.dismiss();
                                        }
                                    }, false, true);
                                } else if (status.equalsIgnoreCase("Invalid Date !!!")) {
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_invalid_date), getString(R.string.server_error_invalid_date_massege), "OK", null, null, true, false);
                                } else if(status.equalsIgnoreCase(" Please wait for 15 minutes you exceed limit more than 5 !!!")){
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.info),
                                            getString(R.string.login_attempt_failed), "OK",
                                            null, null, true,
                                            false);
                                } else{
                                    DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this,
                                            getString(R.string.server_error_device_info), getString(R.string.server_error_device_info_massege) + " (" + status + ") " + getString(R.string.device), "OK", null, null, false, false);
                                }
                            } else {
                                parseServerData(jsonObject);
                            }
                        }
                    } catch (JSONException jsonException) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.data_not_found_tost), Toast.LENGTH_SHORT).show();
                        AppUtility.getInstance().showLog("jsonException" + jsonException, LoginActivity.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AppUtility.getInstance().showLog("jsonException" + error, LoginActivity.class);
                    progressDialog.dismiss();
                    DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.SERVER_ERROR_TITLE), getString(SERVER_ERROR_MESSAGE), "OK");
                }
            });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 10000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 1;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                    AppUtility.getInstance().showLog("login error"+error,LoginActivity.class);

                }
            });
            SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);*/

          /****************************************************************************************************/
        }
    }

    private void updateApplication() {
        final String appPackageName = context.getPackageName();
        try {
            //((Activity) context).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
           // context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
           // context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://nrlm.gov.in/outerReportAction.do?methodName=showIndex")));
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/11ai-E0CY-RshvTO3aHAISREQi74kEhb6/view?usp=sharing")));
        } catch (android.content.ActivityNotFoundException anfe) {
            //((Activity) context).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://nrlm.gov.in/outerReportAction.do?methodName=showIndex")));
        }
        //((Activity) context).finish();
        ((Activity) context).finish();
    }

    private String getCordinateFromPreference() {
        String strCordinates="";
        String cordinates =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyLogoutCordinates(),LoginActivity.this);
        if(cordinates == null || cordinates.equalsIgnoreCase("")){
            strCordinates="";
        }else {
            strCordinates=cordinates;
        }
        return strCordinates;
       // return "28.6294922,77.2189284";
    }

    private String getTimeStampFromPreference() {
        String str="";
        String timeStamp =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyLogoutTimeStamp(),LoginActivity.this);
        if(timeStamp == null || timeStamp.equalsIgnoreCase("")){
            str="";
        }else {
            str=timeStamp;
        }
        return str;
       // return "2020-05-11 12:08:58.27473";
    }

    private void parseServerData(JSONObject jsonResponse) {
        AppUtility.getInstance().showLog("jsonResponse" + jsonResponse, LoginActivity.class);
        if (jsonResponse.toString().equalsIgnoreCase("{}")) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, getString(R.string.toast_nodata_found), Toast.LENGTH_SHORT).show();
        } else {
            LoginInfo loginInfo = new LoginInfo();
            BlockData blockData = new BlockData();
            GpData gpData = new GpData();
            VillageData villageData = new VillageData();
            ShgData shgData = new ShgData();
            ShgMemberData shgMemberData = new ShgMemberData();
            BlockLevelData blockLevelData = new BlockLevelData();
            ModuleData moduleData = new ModuleData();
            ViewReportMonthData viewReportMonthData = new ViewReportMonthData();
            ViewReportData viewReportDataDB = new ViewReportData();
            ViewReportTrainingData viewReportTrainingData = new ViewReportTrainingData();
            ViewReportModuleData viewReportModuleData = new ViewReportModuleData();
            ShgModuleData shgModuleData = new ShgModuleData();
            EvaluationMasterLocationData evaluationMasterLocationData = new EvaluationMasterLocationData();
            EvaluationMasterShgData evaluationMasterShgData = new EvaluationMasterShgData();
            EvaluationMasterTrainingData evaluationMasterTrainingData = new EvaluationMasterTrainingData();

            try {
                String loginId = jsonResponse.getString("login_id");
                String password = jsonResponse.getString("password");
                String mobileNo = jsonResponse.getString("mobile_number");
                String serverTimeStamp = jsonResponse.getString("server_date_time");
                String appVersion = jsonResponse.getString("app_version");
                String logoutDays = jsonResponse.getString("logout_days");
                String languageId = jsonResponse.getString("language_id");
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(), loginId, LoginActivity.this);
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyMobileNumber(), mobileNo, LoginActivity.this);


                if (!(loginId == null) && !loginId.isEmpty()) {
                    loginInfo.setLoginId(loginId);
                } else loginInfo.setLoginId("");

                if (password != null && !password.isEmpty())
                    loginInfo.setPassword(password);
                else loginInfo.setPassword("");

                if (mobileNo != null && !mobileNo.isEmpty())
                    loginInfo.setMobileNo(mobileNo);
                else loginInfo.setMobileNo("");

                if (serverTimeStamp != null && !serverTimeStamp.isEmpty())
                    loginInfo.setServerTimeStamp(serverTimeStamp);
                else loginInfo.setServerTimeStamp("");

                if (appVersion != null && !appVersion.isEmpty())
                    loginInfo.setAppVersion(appVersion);
                else loginInfo.setAppVersion("");

                if (logoutDays != null && !logoutDays.isEmpty())
                    loginInfo.setLogoutDays(logoutDays);
                else loginInfo.setLogoutDays("1");

                if (languageId != null && !languageId.isEmpty())
                    loginInfo.setLanguageId(languageId);
                else loginInfo.setLanguageId("0");//0 is for primary language is english

                SplashActivity.getInstance().getDaoSession().getLoginInfoDao().insert(loginInfo);

                /************block wise data insert***********************************************/
                JSONArray assignedBlocksJSONArray = jsonResponse.getJSONArray("block_assign");
                for (int i = 0; i < assignedBlocksJSONArray.length(); i++) {
                    JSONObject blocksLeveldata = assignedBlocksJSONArray.getJSONObject(i);

                    if (blocksLeveldata.has("status")) {
                       // progressDialog.dismiss();
                        loginStatus ="Block";
                        break;
                       // Toast.makeText(this, "Please Assign the block", Toast.LENGTH_LONG).show();
                    } else {

                        String blockName = blocksLeveldata.getString("block_name");
                        String blockCode = blocksLeveldata.getString("block_code");

                        if (blockCode != null && !blockCode.isEmpty())
                            blockData.setBlockCode(blockCode);
                        else blockData.setBlockCode("");

                        if (blockName != null && !blockName.isEmpty())
                            blockData.setBlockName(blockName);
                        else blockData.setBlockName("");

                        SplashActivity.getInstance().getDaoSession().getBlockDataDao().insert(blockData);

                        /**************************gp wise data insert*******************************************/
                        JSONArray gpsJSONArray = blocksLeveldata.getJSONArray("gp_assign");

                        try {

                            for (int j = 0; j < gpsJSONArray.length(); j++) {
                                JSONObject gpLevelData = gpsJSONArray.getJSONObject(j);
                                if(gpLevelData.has("status")){
                                   // loginStatus ="Gp";
                                   // break;
                                }else {


                                String gpCode = gpLevelData.getString("gp_code");
                                String gpName = gpLevelData.getString("gp_name");

                                if (gpCode != null && !gpCode.isEmpty())
                                    gpData.setGpCode(gpCode);
                                else gpData.setGpCode("");

                                if (gpName != null && !gpName.isEmpty())
                                    gpData.setGpName(gpName);
                                else gpData.setGpName("");

                                if (blockCode != null && !blockCode.isEmpty())
                                    gpData.setBlockCode(blockCode);
                                else gpData.setBlockCode("");

                                SplashActivity.getInstance().getDaoSession().getGpDataDao().insert(gpData);

                                /*****************************village vise data insert*********************************************/
                                JSONArray villagesJSONArray = gpLevelData.getJSONArray("village_assign");
                                for (int k = 0; k < villagesJSONArray.length(); k++) {

                                    try {


                                    } catch (Exception e) {
                                        AppUtility.getInstance().showLog("report moduleData" + e, LoginActivity.class);

                                    }
                                    JSONObject villageLevelData = villagesJSONArray.getJSONObject(k);

                                    if(villageLevelData.has("status")){
                                       // loginStatus= "Village";
                                       // break;
                                    }else {

                                        String villageCode = villageLevelData.getString("village_code");
                                        String villageName = villageLevelData.getString("village_name");

                                        if (gpCode != null && !gpCode.isEmpty())
                                            villageData.setGpCode(gpCode);
                                        else villageData.setGpCode("");

                                        if (villageCode != null && !villageCode.isEmpty())
                                            villageData.setVillageCode(villageCode);
                                        else villageData.setVillageCode("");

                                        if (villageName != null && !villageName.isEmpty())
                                            villageData.setGetVillageName(villageName);
                                        else villageData.setGetVillageName("");

                                        SplashActivity.getInstance().getDaoSession().getVillageDataDao().insert(villageData);
                                        //  SplashActivity.getInstance().getDaoSession().getShgModuleDataDao().detachAll();
                                        JSONArray shgsJSONArray = villageLevelData.getJSONArray("shg_details");

                                        for (int l = 0; l < shgsJSONArray.length(); l++) {
                                            String villageStatus = "";
                                            String shgName = "";
                                            String shgCode = "";
                                            String baseLineStatus = "";

                                            try {


                                            } catch (Exception e) {
                                                AppUtility.getInstance().showLog("report moduleData" + e, LoginActivity.class);

                                            }

                                            JSONObject shgLevelData = shgsJSONArray.getJSONObject(l);
                                            if (shgLevelData.has("status")) {
                                               // loginStatus ="SHG";
                                               // break;
                                            } else {
                                                villageStatus = "Contains data";
                                                shgName = shgLevelData.getString("shg_name");
                                                shgCode = shgLevelData.getString("shg_code");
                                                baseLineStatus = shgLevelData.getString("basline_status");
                                                if (villageCode != null && !villageCode.isEmpty())
                                                    shgData.setVillageCode(villageCode);
                                                else shgData.setVillageCode("");
                                                if (shgName != null && !shgName.isEmpty())
                                                    shgData.setShgName(shgName);
                                                else shgData.setShgName("");
                                                if (shgCode != null && !shgCode.isEmpty())
                                                    shgData.setShgCode(shgCode);
                                                else shgData.setShgCode("");
                                                if (baseLineStatus != null && !baseLineStatus.isEmpty())
                                                    shgData.setBaselineStatus(baseLineStatus);
                                                else shgData.setBaselineStatus("");

                                                shgData.setVillageStatus(villageStatus);
                                                shgData.setId(null);
                                                SplashActivity.getInstance().getDaoSession().getShgDataDao().insert(shgData);

                                                //********************************save shg wise module data************************************************
                                                JSONArray jsonArrayForModuleList = shgLevelData.getJSONArray("selectedModulesList");
                                                for (int moduleList = 0; moduleList < jsonArrayForModuleList.length(); moduleList++) {
                                                    try {
                                                    } catch (Exception e) {
                                                        AppUtility.getInstance().showLog("report moduleData" + e, LoginActivity.class);

                                                    }
                                                    JSONObject moduleListObject = jsonArrayForModuleList.getJSONObject(moduleList);
                                                    if (moduleListObject.has("status")) {

                                                    } else {
                                                        String moduleid = moduleListObject.getString("moduleId");
                                                        String moduleCount = moduleListObject.getString("moduleCount");
                                                        String moduleName = moduleListObject.getString("moduleName");
                                                        String lanId = moduleListObject.getString("language_id");
                                                        if (lanId != null && !lanId.isEmpty())
                                                            shgModuleData.setLanguageId(lanId);
                                                        else shgModuleData.setLanguageId("");
                                                        if (moduleName != null && !moduleName.isEmpty())
                                                            shgModuleData.setModuleName(moduleName);
                                                        else shgModuleData.setModuleName("");
                                                        if (shgCode != null && !shgCode.isEmpty())
                                                            shgModuleData.setShgCodeforModule(shgCode);
                                                        else shgModuleData.setShgCodeforModule("");
                                                        if (villageCode != null && !villageCode.isEmpty())
                                                            shgModuleData.setVillgeCode(villageCode);
                                                        else shgModuleData.setVillgeCode("");
                                                        if (moduleid != null && !moduleid.isEmpty())
                                                            shgModuleData.setModuleId(moduleid);
                                                        else shgModuleData.setModuleId("");
                                                        if (moduleCount != null && !moduleCount.isEmpty())
                                                            shgModuleData.setModuleCount(moduleCount);
                                                        else shgModuleData.setModuleCount("");
                                                        shgModuleData.setId(null);
                                                        SplashActivity.getInstance().getDaoSession().getShgModuleDataDao().insert(shgModuleData);
                                                    }
                                                }
                                                //*****************************************insert complet shg module data***************************************
                                                JSONArray shgMembersJSONArray = shgLevelData.getJSONArray("shg_members");
                                                for (int m = 0; m < shgMembersJSONArray.length(); m++) {

                                                    try {

                                                    } catch (Exception e) {
                                                        AppUtility.getInstance().showLog("report moduleData" + e, LoginActivity.class);

                                                    }


                                                    JSONObject shgMemberLevelData = shgMembersJSONArray.getJSONObject(m);

                                                    if (shgMemberLevelData.has("status")) {

                                                    } else {
                                                        String shgMemberCode = shgMemberLevelData.getString("shg_member_code");
                                                        String shgMemberName = shgMemberLevelData.getString("shg_member_name");
                                                        if (shgMemberCode != null && !shgMemberCode.isEmpty())
                                                            shgMemberData.setShgMemberCode(shgMemberCode);
                                                        else shgMemberData.setShgMemberCode("");
                                                        if (shgMemberName != null && !shgMemberName.isEmpty())
                                                            shgMemberData.setShgMemberName(shgMemberName);
                                                        else shgMemberData.setShgMemberName("");
                                                        if (shgCode != null && !shgCode.isEmpty())
                                                            shgMemberData.setShgCode(shgCode);
                                                        else shgMemberData.setShgCode("");
                                                        SplashActivity.getInstance().getDaoSession().getShgMemberDataDao().insert(shgMemberData);
                                                    }

                                                }

                                                if (blockName != null && !blockName.isEmpty())
                                                    blockLevelData.setBlockName(blockName);
                                                else blockLevelData.setBlockName("");
                                                if (blockCode != null && !blockCode.isEmpty())
                                                    blockLevelData.setBlockCode(blockCode);
                                                else blockLevelData.setBlockCode("");
                                                if (gpCode != null && !gpCode.isEmpty())
                                                    blockLevelData.setGpCode(gpCode);
                                                else blockLevelData.setGpCode("");
                                                if (gpName != null && !gpName.isEmpty())
                                                    blockLevelData.setGpName(gpName);
                                                else blockLevelData.setGpName("");

                                                if (villageName != null && !villageName.isEmpty())
                                                    blockLevelData.setVillageName(villageName);
                                                else blockLevelData.setVillageName("");

                                                if (villageCode != null && !villageCode.isEmpty())
                                                    blockLevelData.setVillageCode(villageCode);
                                                else blockLevelData.setVillageCode("");
                                                if (shgName != null && !shgName.isEmpty())
                                                    blockLevelData.setShgName(shgName);
                                                else blockLevelData.setShgName("");
                                                if (shgCode != null && !shgCode.isEmpty())
                                                    blockLevelData.setShgCode(shgCode);
                                                else blockLevelData.setShgCode("");
                                                if (baseLineStatus != null && !baseLineStatus.isEmpty())
                                                    blockLevelData.setBaseLineStatus(baseLineStatus);
                                                else blockLevelData.setBaseLineStatus("");
                                                SplashActivity.getInstance().getDaoSession().getBlockLevelDataDao().insert(blockLevelData);

                                            }
                                        }
                                    }
                                }
                                }
                            }

                        } catch (Exception e) {
                            AppUtility.getInstance().showLog("gpLevelData" + e, LoginActivity.class);

                        }
                    }

                }

//***************************************************************insert training data for evaluation**************************************************************************
                JSONArray addedTrainings = jsonResponse.getJSONArray("added_training");
                try {
                    for (int at = 0; at < addedTrainings.length(); at++) {
                        JSONObject addedtrainingObject = addedTrainings.getJSONObject(at);
                        if (addedtrainingObject.has("status")) {
                            AppUtility.getInstance().showLog("dataIsNotAvalable" + addedtrainingObject.getString("status"), LoginActivity.class);

                        } else {

                            String blockCode = addedtrainingObject.getString("block_code");
                            String villageCode = addedtrainingObject.getString("village_code");
                            String gpCode = addedtrainingObject.getString("gp_code");

                            if (blockCode != null && !blockCode.isEmpty())
                                evaluationMasterLocationData.setBlockCode(blockCode);
                            else evaluationMasterLocationData.setBlockCode("");

                            if (villageCode != null && !villageCode.isEmpty())
                                evaluationMasterLocationData.setVillageCode(villageCode);
                            else evaluationMasterLocationData.setVillageCode("");

                            if (gpCode != null && !gpCode.isEmpty())
                                evaluationMasterLocationData.setGpCode(gpCode);
                            else evaluationMasterLocationData.setGpCode("");

                            SplashActivity.getInstance().getDaoSession().getEvaluationMasterLocationDataDao().detachAll();
                            SplashActivity.getInstance().getDaoSession().getEvaluationMasterLocationDataDao().insert(evaluationMasterLocationData);

                            //**************************insert training shg data in added shg data table**************************************************

                            JSONArray selectedShgs = addedtrainingObject.getJSONArray("shg_Data");

                            try {
                                for (int shg = 0; shg < selectedShgs.length(); shg++) {
                                    JSONObject getShgDetails = selectedShgs.getJSONObject(shg);
                                    String shgCode = "";
                                    if (getShgDetails.has("status")) {
                                        AppUtility.getInstance().showLog("dataIsNotAvalable" + addedtrainingObject.getString("status"), LoginActivity.class);

                                    } else {
                                        shgCode = getShgDetails.getString("shg_code");

                                        JSONObject shgEvaluationData = getShgDetails.getJSONObject("evaluationdata");
                                        String secondEvaluationDate="";
                                        String thirdEvaluationDate="";
                                        String firstEvaluationDate="";
                                        String evaluationStatus="";
                                        String evaluationYear="";

                                        if(shgEvaluationData.has("status")){
                                            AppUtility.getInstance().showLog("dataIsNotAvalable" + addedtrainingObject.getString("status"), LoginActivity.class);
                                        }else{

                                            if (shgCode != null && !shgCode.isEmpty())
                                                evaluationMasterShgData.setShgCode(shgCode);
                                            else evaluationMasterShgData.setShgCode("");


                                            if (villageCode != null && !villageCode.isEmpty())
                                                evaluationMasterShgData.setVillageCode(villageCode);
                                            else evaluationMasterLocationData.setVillageCode("");


                                            if(shgEvaluationData.has("evaluationYear")){
                                                 evaluationYear = shgEvaluationData.getString("evaluationYear");
                                                evaluationMasterShgData.setEvaluationYear(evaluationYear);
                                            }else {
                                                evaluationMasterShgData.setEvaluationYear("");
                                            }
                                            if(shgEvaluationData.has("evaluationDate")){
                                                String evaluationDate = shgEvaluationData.getString("evaluationDate");
                                                evaluationMasterShgData.setEvaluationDate(evaluationDate);

                                            }else {
                                                evaluationMasterShgData.setEvaluationDate("");
                                            }
                                            if(shgEvaluationData.has("2nd_evaluation_date")){
                                                 secondEvaluationDate = shgEvaluationData.getString("2nd_evaluation_date");
                                                evaluationMasterShgData.setSecondEvaluationDate(secondEvaluationDate);
                                            }else {
                                                evaluationMasterShgData.setSecondEvaluationDate("");
                                            }
                                            if(shgEvaluationData.has("3rd_evaluation_date")){
                                                 thirdEvaluationDate = shgEvaluationData.getString("3rd_evaluation_date");
                                                evaluationMasterShgData.setThirdEvaluationDate(thirdEvaluationDate);
                                            }else {
                                                evaluationMasterShgData.setThirdEvaluationDate("");
                                            }
                                            if(shgEvaluationData.has("baseTrainingdate")){
                                                String baseTrainingdate = shgEvaluationData.getString("baseTrainingdate");
                                                evaluationMasterShgData.setFirstTrainingdate(baseTrainingdate);
                                            }else {
                                                evaluationMasterShgData.setFirstTrainingdate("");
                                            }
                                            if(shgEvaluationData.has("4th_evaluation_date")){
                                                String fourthEvaluationDate = shgEvaluationData.getString("4th_evaluation_date");
                                                evaluationMasterShgData.setFourthEvaluationDate(fourthEvaluationDate);
                                            }else {
                                                evaluationMasterShgData.setFourthEvaluationDate("");
                                            }
                                            if(shgEvaluationData.has("Max_evaluationDate")){
                                                String Max_evaluationDate = shgEvaluationData.getString("Max_evaluationDate");
                                                evaluationMasterShgData.setMaximunEvaluationdate(Max_evaluationDate);

                                            }else {
                                                evaluationMasterShgData.setMaximunEvaluationdate("");
                                            }
                                            if(shgEvaluationData.has("1st_evaluation_date")){
                                                 firstEvaluationDate = shgEvaluationData.getString("1st_evaluation_date");
                                                evaluationMasterShgData.setFirstEvaluationDate(firstEvaluationDate);
                                            }else {
                                                evaluationMasterShgData.setFirstEvaluationDate("");

                                            }
                                            /***ne condition****/
                                            if(shgEvaluationData.has("evaluationStatus")){
                                                 evaluationStatus = shgEvaluationData.getString("evaluationStatus");
                                                evaluationMasterShgData.setEvaluationStatus(evaluationStatus);

                                            }else {
                                                evaluationMasterShgData.setEvaluationStatus("");
                                            }

                                            if(shgEvaluationData.has("2nd_evaluation_date")
                                                    && shgEvaluationData.has("3rd_evaluation_date")
                                                    &&shgEvaluationData.has("1st_evaluation_date")
                                                    &&shgEvaluationData.getString("evaluationStatus").equalsIgnoreCase(AppConstant.NO_OF_EVALUATIONS)){

                                                evaluationStatus="0";
                                                evaluationYear= String.valueOf(Integer.parseInt(evaluationYear)+1);
                                                evaluationMasterShgData.setEvaluationYear(evaluationYear);
                                                evaluationMasterShgData.setEvaluationStatus(evaluationStatus);
                                            }
                                            evaluationMasterShgData.setEvaluationdonestatus("0");
                                            evaluationMasterShgData.setEvaluationMasterId(null);
                                            SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().detachAll();
                                            SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().insert(evaluationMasterShgData);

                                        }

                                       /* String evaluationDate = getShgDetails.getString("evaluationDate");
                                        String evaluationStatus = getShgDetails.getString("evaluationStatus");
                                        String firstTrainingDate = getShgDetails.getString("firstTrainingdate");
                                        String Max_evaluationDate = getShgDetails.getString("Max_evaluationDate");


                                        if (evaluationStatus != null && !evaluationStatus.isEmpty())
                                            evaluationMasterShgData.setEvaluationStatus(evaluationStatus);
                                        else evaluationMasterShgData.setEvaluationStatus("");

                                        if (villageCode != null && !villageCode.isEmpty())
                                            evaluationMasterShgData.setVillageCode(villageCode);
                                        else evaluationMasterShgData.setMaximunEvaluationdate("");

                                        if (shgCode != null && !shgCode.isEmpty())
                                            evaluationMasterShgData.setShgCode(shgCode);
                                        else evaluationMasterShgData.setShgCode("");

                                        if (evaluationDate != null && !evaluationDate.isEmpty())
                                            evaluationMasterShgData.setEvaluationDate(evaluationDate);
                                        else evaluationMasterShgData.setEvaluationDate("");

                                        if (firstTrainingDate != null && !firstTrainingDate.isEmpty())
                                            evaluationMasterShgData.setFirstTrainingdate(firstTrainingDate);
                                        else evaluationMasterShgData.setFirstTrainingdate("");

                                        if (Max_evaluationDate != null && !Max_evaluationDate.isEmpty())
                                            evaluationMasterShgData.setMaximunEvaluationdate(Max_evaluationDate);
                                        else evaluationMasterShgData.setMaximunEvaluationdate("");

                                        evaluationMasterShgData.setEvaluationMasterId(null);

                                        SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().detachAll();
                                        SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().insert(evaluationMasterShgData);*/

                                    }

                                    //****************************************inserting training data in master tables********************************************
                                    JSONArray trainingArray = getShgDetails.getJSONArray("trainingData");
                                    for (int shgTraining = 0; shgTraining < trainingArray.length(); shgTraining++) {
                                        JSONObject trainingDate = trainingArray.getJSONObject(shgTraining);
                                        if (trainingDate.has("status")) {
                                            AppUtility.getInstance().showLog("dataIsNotAvalable" + addedtrainingObject.getString("status"), LoginActivity.class);

                                        } else {
                                            String traininigId = trainingDate.getString("trainingId");

                                            if (shgCode != null && !shgCode.isEmpty())
                                                evaluationMasterTrainingData.setShgCode(shgCode);
                                            else evaluationMasterTrainingData.setShgCode("");

                                            if (traininigId != null && !traininigId.isEmpty())
                                                evaluationMasterTrainingData.setTrainingid(traininigId);
                                            else evaluationMasterTrainingData.setTrainingid("");

                                            SplashActivity.getInstance().getDaoSession().getEvaluationMasterTrainingDataDao().insert(evaluationMasterTrainingData);
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                AppUtility.getInstance().showLog("getShgDetails" + e, LoginActivity.class);

                            }

                        }
                    }

                } catch (Exception e) {
                    AppUtility.getInstance().showLog("addedtrainingObject" + e, LoginActivity.class);

                }

                AppUtility.getInstance().showLog("lengthOfAddedTraining" + addedTrainings.length(), LoginActivity.class);


//****************************************************************insert data for view report in local db**************************************************

                JSONArray viewReports = jsonResponse.getJSONArray("view_reports");

                try {
                    for (int vri = 0; vri < viewReports.length(); vri++) {


                        JSONObject viewReportsJSONObject = viewReports.getJSONObject(vri);
                        if (viewReportsJSONObject.has("status")) {

                        } else {
                            String monthCode = viewReportsJSONObject.getString("month");
                            String year = viewReportsJSONObject.getString("year");
                            String workinDays = viewReportsJSONObject.getString("working_days");

                            if (monthCode != null && !monthCode.isEmpty())
                                viewReportMonthData.setMonthCode(monthCode);
                            else viewReportMonthData.setMonthCode("");

                            if (year != null && !year.isEmpty())
                                viewReportMonthData.setYear(year);
                            else viewReportMonthData.setYear("");

                            if (workinDays != null && !workinDays.isEmpty())
                                viewReportMonthData.setWorkingDays(workinDays);
                            else viewReportMonthData.setWorkingDays("0");

                            viewReportMonthData.setId(null);

                            SplashActivity.getInstance().getDaoSession().getViewReportMonthDataDao().insert(viewReportMonthData);

                            //*********get report global data insert in report table********************************
                            JSONArray reportData = viewReportsJSONObject.getJSONArray("reportData");
                            for (int vrk = 0; vrk < reportData.length(); vrk++) {
                                JSONObject reportDataJSONObject = reportData.getJSONObject(vrk);
                                String baslineDone = reportDataJSONObject.getString("baseline_done");
                                String evaluationDone = reportDataJSONObject.getString("evaluation_done_shg_wise");
                                String shgUniquelyTrainingDone = reportDataJSONObject.getString("number_of_uniquely_shg_trained");

                                if (monthCode != null && !monthCode.isEmpty())
                                    viewReportDataDB.setMonthCode(monthCode);
                                else viewReportDataDB.setMonthCode("");

                                if (year != null && !year.isEmpty())
                                    viewReportDataDB.setYear(year);
                                else viewReportDataDB.setYear("");

                                if (baslineDone != null && !baslineDone.isEmpty())
                                    viewReportDataDB.setBaselineDone(baslineDone);
                                else viewReportDataDB.setBaselineDone("");

                                if (evaluationDone != null && !evaluationDone.isEmpty())
                                    viewReportDataDB.setEvaluationDone(evaluationDone);
                                else viewReportDataDB.setEvaluationDone("");

                                if (shgUniquelyTrainingDone != null && !shgUniquelyTrainingDone.isEmpty())
                                    viewReportDataDB.setShgUniquelyDone(shgUniquelyTrainingDone);
                                else viewReportDataDB.setShgUniquelyDone("");
                                viewReportDataDB.setId(null);

                                SplashActivity.getInstance().getDaoSession().getViewReportDataDao().insert(viewReportDataDB);

                                //**************get training data and insert in view report trainng data table********************************************
                                JSONArray reportTrainingData = reportDataJSONObject.getJSONArray("trainingData");
                                for (int vrl = 0; vrl < reportTrainingData.length(); vrl++) {
                                    JSONObject reportTrainingDataJSONObject = reportTrainingData.getJSONObject(vrl);
                                    String blockCode = reportTrainingDataJSONObject.getString("blockCode");
                                    String villageCode = reportTrainingDataJSONObject.getString("villageCode");
                                    String gpCode = reportTrainingDataJSONObject.getString("gpCode");
                                    String otherParticipant = reportTrainingDataJSONObject.getString("other_participant");
                                    String memberParticipant = reportTrainingDataJSONObject.getString("number_of_member_participated");
                                    String trainingId = reportTrainingDataJSONObject.getString("training_id");
                                    String dateOfTraining = reportTrainingDataJSONObject.getString("date_of_training");
                                    String shgParticipant = reportTrainingDataJSONObject.getString("number_of_shg_participated");

                                    if (monthCode != null && !monthCode.isEmpty())
                                        viewReportTrainingData.setMonthCode(monthCode);
                                    else viewReportTrainingData.setMonthCode("");

                                    if (year != null && !year.isEmpty())
                                        viewReportTrainingData.setYear(year);
                                    else viewReportTrainingData.setYear("");

                                    if (blockCode != null && !blockCode.isEmpty())
                                        viewReportTrainingData.setBlockCode(blockCode);
                                    else viewReportTrainingData.setBlockCode("");

                                    if (villageCode != null && !villageCode.isEmpty())
                                        viewReportTrainingData.setVillageCode(villageCode);
                                    else viewReportTrainingData.setVillageCode("");

                                    if (gpCode != null && !gpCode.isEmpty())
                                        viewReportTrainingData.setGpCode(gpCode);
                                    else viewReportTrainingData.setGpCode("");

                                    if (otherParticipant != null && !otherParticipant.isEmpty())
                                        viewReportTrainingData.setOtherParticipant(otherParticipant);
                                    else viewReportTrainingData.setOtherParticipant("");

                                    if (memberParticipant != null && !memberParticipant.isEmpty())
                                        viewReportTrainingData.setMemberParticipant(memberParticipant);
                                    else viewReportTrainingData.setMemberParticipant("");

                                    if (trainingId != null && !trainingId.isEmpty())
                                        viewReportTrainingData.setTrainingCode(trainingId);
                                    else viewReportTrainingData.setTrainingCode("");

                                    if (dateOfTraining != null && !dateOfTraining.isEmpty())
                                        viewReportTrainingData.setDateOfTraining(dateOfTraining);
                                    else viewReportTrainingData.setDateOfTraining("");

                                    if (shgParticipant != null && !shgParticipant.isEmpty())
                                        viewReportTrainingData.setShgParticipant(shgParticipant);
                                    else viewReportTrainingData.setShgParticipant("");
                                    viewReportTrainingData.setId(null);

                                    SplashActivity.getInstance().getDaoSession().getViewReportTrainingDataDao().insert(viewReportTrainingData);


                                    //************insert view report module data********************************************************************************

                                    JSONArray reportmoduleData = reportTrainingDataJSONObject.getJSONArray("module");
                                    for (int vkm = 0; vkm < reportmoduleData.length(); vkm++) {

                                        try {


                                        } catch (Exception e) {
                                            AppUtility.getInstance().showLog("report moduleData" + e, LoginActivity.class);

                                        }
                                        JSONObject reportModuleDataJSONObject = reportmoduleData.getJSONObject(vkm);

                                        if (reportModuleDataJSONObject.has("status")) {

                                        } else {
                                            String moduleid = reportModuleDataJSONObject.getString("module_id");

                                            if (monthCode != null && !monthCode.isEmpty())
                                                viewReportModuleData.setMonthCode(monthCode);
                                            else viewReportModuleData.setMonthCode("");

                                            if (year != null && !year.isEmpty())
                                                viewReportModuleData.setYear(year);
                                            else viewReportModuleData.setYear("");

                                            if (trainingId != null && !trainingId.isEmpty())
                                                viewReportModuleData.setTringngCode(trainingId);
                                            else viewReportModuleData.setTringngCode("");

                                            if (moduleid != null && !moduleid.isEmpty())
                                                viewReportModuleData.setModuleId(moduleid);
                                            else viewReportModuleData.setModuleId("");
                                            viewReportModuleData.setId(null);

                                            SplashActivity.getInstance().getDaoSession().getViewReportModuleDataDao().insert(viewReportModuleData);
                                        }
                                    }
                                }
                            }
                        }
                    }


                } catch (Exception e) {
                    AppUtility.getInstance().showLog("viewReportsJSONObject" + e, LoginActivity.class);

                }
//******************************************end insertion of view reports****************************************


// *****************************************start module insertion with language*************************************
                JSONArray moduleDetailsJSONArray = jsonResponse.getJSONArray("module_detail");
                for (int n = 0; n < moduleDetailsJSONArray.length(); n++) {

                    JSONObject moduleDetailsJSONObject = moduleDetailsJSONArray.getJSONObject(n);
                    String moduleCode = String.valueOf(moduleDetailsJSONObject.getInt("module_code"));
                    String moduleLangId = moduleDetailsJSONObject.getString("language_id");
                    String moduleName = moduleDetailsJSONObject.getString("module_name");

                    if (moduleLangId != null && !moduleLangId.isEmpty())
                        moduleData.setLanguageId(moduleLangId);
                    else moduleData.setLanguageId("");

                    if (moduleCode != null && !moduleCode.isEmpty())
                        moduleData.setModuleCode(moduleCode);
                    else moduleData.setModuleCode("");

                    if (moduleName != null && !moduleName.isEmpty())
                        moduleData.setModuleName(moduleName);
                    else moduleData.setModuleName("");

                    SplashActivity.getInstance().getDaoSession().insert(moduleData);
                }

//**********************************************start insertion question and titel detail with language in table*************************************
                JSONArray questionArray = jsonResponse.getJSONArray("questions_title_deatil");

                for (int quesArray = 0; quesArray < questionArray.length(); quesArray++) {
                    TitleInfoDetail titleInfoDetail = new TitleInfoDetail();
                    JSONObject questionObject = questionArray.getJSONObject(quesArray);
                    Long titleId = Long.valueOf(questionObject.getInt("question_title_id"));
                    String titleName = questionObject.getString("question_title");
                    String lanId = questionObject.getString("language_id");
                    titleInfoDetail.setTitleId(titleId);
                    titleInfoDetail.setTitleName(titleName);
                    titleInfoDetail.setLanguageId(lanId);
                    JSONArray questionDetailArray = questionObject.getJSONArray("questions");
                    AppUtility.getInstance().showLog("sizeee" + questionDetailArray.length(), LoginActivity.class);

                    for (int quesDetail = 0; quesDetail < questionDetailArray.length(); quesDetail++) {
                        QuestionInfoDetail questionInfoDetail = new QuestionInfoDetail();
                        JSONObject questionDetailObject = questionDetailArray.getJSONObject(quesDetail);
                        String questionName = questionDetailObject.getString("question_detail");
                        Long questionId = Long.valueOf(questionDetailObject.getInt("question_code"));
                        String questionMainId = questionDetailObject.getString("id");
                        String questionTypeId = questionDetailObject.getString("question_type_id");


                        questionInfoDetail.setQuestionId(questionId);
                        questionInfoDetail.setQuestionName(questionName);
                        questionInfoDetail.setTitleId(titleId);
                        questionInfoDetail.setLanguageId(lanId);
                        questionInfoDetail.setQuestionMainId(questionMainId);
                        questionInfoDetail.setQuestionTypeId(questionTypeId);
                        QuestionInfoDetailDao questionInfoDetailDao = SplashActivity.getInstance().getDaoSession().getQuestionInfoDetailDao();
                        questionInfoDetailDao.insert(questionInfoDetail);
                    }

                    TitleInfoDetailDao titleInfoDetailDao = SplashActivity.getInstance().getDaoSession().getTitleInfoDetailDao();
                    titleInfoDetailDao.insert(titleInfoDetail);
                }
                if(loginStatus.equalsIgnoreCase("")){
                    progressDialog.dismiss();
                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), "logined", LoginActivity.this);
                    AppUtility.getInstance().makeIntent(LoginActivity.this, MpinActivity.class, true);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(this,loginStatus+"Not Assign !!!",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException jsonException) {
                AppUtility.getInstance().showLog("jsonException" + jsonException, LoginActivity.class);
            }
        }
    }

    public void saveLoginDetailsInLocalFile(Context context, String loginId, String mPin, String lastTrainingId, String mobileNo, String lastUpdatedVersion) {
        String encryptedData="";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("loginId", loginId);
            jsonObject.accumulate("mPin", mPin);
            jsonObject.accumulate("lastTrainingId", lastTrainingId);
            jsonObject.accumulate("mobileNumber", mobileNo);
            jsonObject.accumulate("appVersion", lastUpdatedVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
         encryptedData=new Cryptography().encrypt(jsonObject.toString());
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

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance().getMpin(), AppConstant.mpinFileName,encryptedData );
    }

    public JSONObject readMpinFileFromInternal(Context context, String fileName) throws JSONException {
        JSONObject mPinLocalFileObject = null;
        AppUtility.getInstance().showLog("context" + LoginActivity.this, LoginActivity.class);
        String string = FileUtility.getInstance().read_file(FileManager.getInstance().getAbslutePathDetails(context, fileName), FileManager.getInstance().getPathDetails(context), fileName);
        if (!fileName.equalsIgnoreCase(AppConstant.trainingFileName)) {
            try {
                string = new Cryptography().decrypt(string);
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
        }
        mPinLocalFileObject = new JSONObject(string);
        return mPinLocalFileObject;
    }

    private void populateDbFromLocalFile() {
        try {
            if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpin(), AppConstant.mpinFileName)) {
                try {
                    JSONObject mpinFileJSONObject = readMpinFile();
                    String loginIdFromFile = mpinFileJSONObject.getString("loginId");
                    String mobileFromFile = mpinFileJSONObject.getString("mobileNumber");
                   // AppUtility.getInstance().showLog("loginIdFromFile=" + loginIdFromFile + "mobileFromFile" + mobileFromFile, LoginActivity.class);
                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(), loginIdFromFile, LoginActivity.this);
                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyMobileNumber(), mobileFromFile, LoginActivity.this);
                } catch (JSONException je) {
                   // AppUtility.getInstance().showLog("je" + je, LoginActivity.class);
                }
                if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getPathDetails(LoginActivity.this), AppConstant.baslineFileName)) {
                    JSONObject baselineObject = readMpinFileFromInternal(LoginActivity.this, AppConstant.baslineFileName);
                  //  AppUtility.getInstance().showLog("baselineObject" + baselineObject, LoginActivity.class);
                    populateBaselineSyncTableFromFile(baselineObject);
                }
                // JSONArray addTrainingArray=readMpinFileFromInternal(LoginActivity.this,AppConstant.trainingFileName);
                if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getPathDetails(LoginActivity.this), AppConstant.evaluationFileName)) {
                    JSONObject evaluationObject = readMpinFileFromInternal(LoginActivity.this, AppConstant.evaluationFileName);
                   // AppUtility.getInstance().showLog("evaluationObject" + evaluationObject, LoginActivity.class);
                    populateEvaluationSyncTableFromFile(evaluationObject);
                }
                if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getPathDetails(LoginActivity.this), AppConstant.trainingFileName)) {
                    JSONObject trainingObject = readMpinFileFromInternal(LoginActivity.this, AppConstant.trainingFileName);
                   // AppUtility.getInstance().showLog("evaluationObject" + trainingObject, LoginActivity.class);
                    populateTrainingSyncTableFromFile(trainingObject);
                }
            } else {
               // AppUtility.getInstance().showLog("not exists", LoginActivity.class);
            }
        } catch (JSONException je) {
           // AppUtility.getInstance().showLog("filereadexception" + je, LoginActivity.class);
        }
    }
/*.......................read mpin file method..................................*/
    public JSONObject readMpinFile() throws JSONException {
        String mpinFile = "{}";
        if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpin(), AppConstant.mpinFileName)) {
            mpinFile = FileUtility.getInstance().read_file(FileManager.getInstance().getAbsluteMpinPathDetails(LoginActivity.this, AppConstant.mpinFileName), FileManager.getInstance().getMpin(), AppConstant.mpinFileName);
            try {
                mpinFile=new Cryptography().decrypt(mpinFile);
                AppUtility.getInstance().showLog("mpinFile"+mpinFile,LoginActivity.class);
            } catch (InvalidKeyException e) {
                AppUtility.getInstance().showLog("DecrptionInvalidKeyException"+e,LoginActivity.class);
            } catch (UnsupportedEncodingException e) {
                AppUtility.getInstance().showLog("DecrptionUnsupportedEncodingException"+e,LoginActivity.class);
            } catch (InvalidAlgorithmParameterException e) {
                AppUtility.getInstance().showLog("DecrptionInvalidAlgorithmParameterException"+e,LoginActivity.class);
            } catch (IllegalBlockSizeException e) {
                AppUtility.getInstance().showLog("DecrptionIllegalBlockSizeException"+e,LoginActivity.class);
            } catch (BadPaddingException e) {
                AppUtility.getInstance().showLog("DecrptionBadPaddingException"+e,LoginActivity.class);
            } catch (NoSuchAlgorithmException e) {
                AppUtility.getInstance().showLog("DecrptionNoSuchAlgorithmException"+e,LoginActivity.class);
            } catch (NoSuchPaddingException e) {
                AppUtility.getInstance().showLog("DecrptionNoSuchPaddingException"+e,LoginActivity.class);
            }
        }
        return new JSONObject(mpinFile);
    }

    //**************insert basline sync data from local file to database*************************
    private void populateBaselineSyncTableFromFile(JSONObject fileJSONObject) {

        List<BaselineSyncData> baslineSyncDataList = SplashActivity.getInstance().getDaoSession().getBaselineSyncDataDao().queryBuilder().build().list();
        if (baslineSyncDataList.size() > 0) {

            AppUtility.getInstance().showLog("Data is preseent in database****", LoginActivity.class);
        } else {
            AppUtility.getInstance().showLog("********data is inserted****", LoginActivity.class);
            BaselineSyncData baselineSyncData = new BaselineSyncData();
            BaslineQuestionSyncData baslineQuestionSyncData = new BaslineQuestionSyncData();
            try {
                JSONArray jsonArray = fileJSONObject.getJSONArray("baseline_data");
                for (int ij = 0; ij < jsonArray.length(); ij++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(ij);
                    String shgCode = jsonObject.getString("shg_code");
                    String villageCode = jsonObject.getString("villageCode");
                    String todayDate = jsonObject.getString("app_created_on");
                    String selectedDate = jsonObject.getString("app_sel_baseline_date");
                    String nuberParticipant = jsonObject.getString("no_of_members");

                    baselineSyncData.setShgCode(shgCode);
                    baselineSyncData.setVillageCode(villageCode);
                    baselineSyncData.setBasLineStatus("0");
                    baselineSyncData.setTodayDate(todayDate);
                    baselineSyncData.setUserSelectedDate(selectedDate);
                    baselineSyncData.setEnterMemberValue(nuberParticipant);

                    baselineSyncData.setId(null);

                    SplashActivity.getInstance().getDaoSession().getBaselineSyncDataDao().insert(baselineSyncData);
                    JSONArray jsonArrayForBaslineQuestion = jsonObject.getJSONArray("basline_data");
                    for (int i = 0; i < jsonArrayForBaslineQuestion.length(); i++) {
                        JSONObject questionObject = jsonArrayForBaslineQuestion.getJSONObject(i);
                        String questionId = questionObject.getString("question_code");
                        String answervalue = questionObject.getString("answer");
                        if (answervalue.equalsIgnoreCase("Yes"))
                            answervalue="111";
                        else if (answervalue.equalsIgnoreCase("No"))
                            answervalue="000";

                        baslineQuestionSyncData.setBaslineStatus("0");
                        baslineQuestionSyncData.setAnswerForQuestion(answervalue);
                        baslineQuestionSyncData.setQuestionId(questionId);
                        baslineQuestionSyncData.setShgCode(shgCode);
                        baslineQuestionSyncData.setId(null);
                        SplashActivity.getInstance().getDaoSession().getBaslineQuestionSyncDataDao().insert(baslineQuestionSyncData);
                    }

                }
            } catch (JSONException je) {
                AppUtility.getInstance().showLog("je" + je, LoginActivity.class);
            }
        }
    }

    private void populateEvaluationSyncTableFromFile(JSONObject fileJSONObject) {

        EvaluationSyncQuestionData evaluationSyncQuestionData = new EvaluationSyncQuestionData();
        EvaluationSyncShgData evaluationSyncShgData = new EvaluationSyncShgData();
        try {
            JSONArray jsonArray = fileJSONObject.getJSONArray("evaluationSync");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String shgCode = jsonObject.getString("shgCode");
                String villageCode = jsonObject.getString("villageCode");
                evaluationSyncShgData.setEvaluationDate(jsonObject.getString("evaluationDate"));
                evaluationSyncShgData.setEvaluationType(jsonObject.getString("eval_done"));
                evaluationSyncShgData.setEvaluationYear(jsonObject.getString("eval_year"));
                evaluationSyncShgData.setLatLong(jsonObject.getString("gpslocationcordinate"));
                evaluationSyncShgData.setVillageCode(villageCode);
                evaluationSyncShgData.setTrainingCode("0");
                evaluationSyncShgData.setShgCode(shgCode);
                evaluationSyncShgData.setEvaluationSyncStatus("0");
                evaluationSyncShgData.setTotalMember("0");
                evaluationSyncShgData.setId(null);
                evaluationSyncShgData.setEvaluationMemberCount(jsonObject.getString("evaluationMemberCount"));
                SplashActivity.getInstance().getDaoSession().getEvaluationSyncShgDataDao().insert(evaluationSyncShgData);


                //***********insert question data**********************************
                JSONArray shgWiseQuestionArray = jsonObject.getJSONArray("evaluationShgSyncData");
                for (int question = 0; question < shgWiseQuestionArray.length(); question++) {
                    JSONObject questionData = shgWiseQuestionArray.getJSONObject(question);
                    String questionId = questionData.getString("questionId");
                    String answervalue = questionData.getString("answerValue");
                    if (answervalue.equalsIgnoreCase("Yes"))
                        answervalue="111";
                    else if (answervalue.equalsIgnoreCase("No"))
                        answervalue="000";
                    evaluationSyncQuestionData.setEvaluationSyncStatus("0");
                    evaluationSyncQuestionData.setShgCode(shgCode);
                    evaluationSyncQuestionData.setTrainingId("0");
                    evaluationSyncQuestionData.setQuestionCode(questionId);
                    evaluationSyncQuestionData.setAnswerValue(answervalue);
                    evaluationSyncQuestionData.setId(null);
                    SplashActivity.getInstance().getDaoSession().getEvaluationSyncQuestionDataDao().insert(evaluationSyncQuestionData);
                }
            }

        } catch (JSONException e) {
            AppUtility.getInstance().showLog("JSONException" + e, LoginActivity.class);
        }
    }

    private void populateTrainingSyncTableFromFile(JSONObject trainingObject) {
        TrainingLocationInfo trainingLocationInfo = new TrainingLocationInfo();
        TrainingInfoData trainingInfoData = new TrainingInfoData();
        TrainingShgAndMemberData trainingShgAndMemberData = new TrainingShgAndMemberData();
        TrainingModuleInfo trainingModuleInfo = new TrainingModuleInfo();
        try {
            JSONArray trainingJSONArray = trainingObject.getJSONArray("addTrainingUnSyncedData");

            for (int i = 0; i < trainingJSONArray.length(); i++) {
                JSONObject trainingJSONObject = trainingJSONArray.getJSONObject(i);

                String trainingId = trainingJSONObject.getString("trainingId");
                trainingLocationInfo.setId(null);
                trainingLocationInfo.setTrainingId(trainingId);
                trainingLocationInfo.setDate(trainingJSONObject.getString("trainingCreatedTimeStamp"));
                trainingLocationInfo.setBlockId(trainingJSONObject.getString("blockCode"));
                trainingLocationInfo.setGpId(trainingJSONObject.getString("gpCode"));
                trainingLocationInfo.setVillageId(trainingJSONObject.getString("villageCode"));
                trainingLocationInfo.setSelectedShgCount(trainingJSONObject.getString("selectedShgCount"));
                trainingLocationInfo.setMemberParticipant(trainingJSONObject.getString("subselectedShgCount"));
                trainingLocationInfo.setOtherParticipant(trainingJSONObject.getString("otherParticipants"));
                trainingLocationInfo.setTotalParticipant(trainingJSONObject.getString("totalParticipants"));
                trainingLocationInfo.setGpsLocation(trainingJSONObject.getString("gpslocationcordinate"));
                trainingLocationInfo.setImage(bitmapToByteArray(stringToBitMap(trainingJSONObject.getString("trainingImage"))));
                trainingLocationInfo.setSyncStatus("0");
                SplashActivity.getInstance().getDaoSession().getTrainingLocationInfoDao().insert(trainingLocationInfo);

                JSONArray selectedShgsArray = trainingJSONObject.getJSONArray("selectedShgList");
                for (int j = 0; j < selectedShgsArray.length(); j++) {
                    JSONObject selectedShgsObject = selectedShgsArray.getJSONObject(j);
                    String shgCode = selectedShgsObject.getString("shgCode");
                    trainingInfoData.setTrainingId(trainingId);
                    trainingInfoData.setShgCode(shgCode);
                    trainingInfoData.setSyncStatus("0");
                    SplashActivity.getInstance().getDaoSession().getTrainingInfoDataDao().insert(trainingInfoData);

                    JSONArray membersArray = selectedShgsObject.getJSONArray("memberList");
                    for (int k = 0; k < membersArray.length(); k++) {
                        JSONObject memberObject = membersArray.getJSONObject(k);
                        trainingShgAndMemberData.setShgMemberCode(memberObject.getString("memberCode"));
                        trainingShgAndMemberData.setTrainingId(trainingId);
                        trainingShgAndMemberData.setShgCode(shgCode);
                        trainingShgAndMemberData.setSyncStatus("0");
                        SplashActivity.getInstance().getDaoSession().getTrainingShgAndMemberDataDao().insert(trainingShgAndMemberData);
                    }

                    JSONArray modulesArray = selectedShgsObject.getJSONArray("selectedModulesList");
                    for (int l = 0; l < modulesArray.length(); l++) {
                        JSONObject moduleObject = modulesArray.getJSONObject(l);
                        trainingModuleInfo.setTrainingId(trainingId);
                        trainingModuleInfo.setShgCode(shgCode);
                        trainingModuleInfo.setModuleCode(moduleObject.getString("moduleId"));
                        trainingModuleInfo.setSyncStatus("0");
                        SplashActivity.getInstance().getDaoSession().getTrainingModuleInfoDao().insert(trainingModuleInfo);
                    }
                }
            }
        } catch (JSONException pe) {
            AppUtility.getInstance().showLog("trainingJSONArray Exception" + pe, LoginActivity.class);
        }
    }


    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = android.util.Base64.decode(encodedString,0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
 /*   public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b);
        return temp;
    }*/

 private byte [] bitmapToByteArray(Bitmap bmp){
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     bmp.compress(Bitmap.CompressFormat.PNG, 100 , baos);
     return baos.toByteArray();
 }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        r1=new Runnable() {
            @Override
            public void run() {
                System.exit(0);;
            }
        };
        handler.postDelayed(r1,AppConstant.BACKGROUND_TIME);
    }
}
