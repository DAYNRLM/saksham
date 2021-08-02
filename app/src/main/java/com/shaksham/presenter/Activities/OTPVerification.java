package com.shaksham.presenter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.shaksham.R;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SyncData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OTPVerification extends AppCompatActivity {

    private TextInputEditText enterPasswordTIET, confirmPasswordTIET,enter_UserIdEt;
    private PinEntryEditText enterOtpPEET;
    private Button resetPassBTN,new_passwordBtn;
    private String enteredOTP;
    private String generatedOTP;
    private LinearLayout passwordCreation;
    private  String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        enter_UserIdEt = (TextInputEditText) findViewById(R.id.enter_UserIdEt);
        enterPasswordTIET = (TextInputEditText) findViewById(R.id.enter_passwordTIET);
        confirmPasswordTIET = (TextInputEditText) findViewById(R.id.confirm_passwordTIET);
        enterOtpPEET = (PinEntryEditText) findViewById(R.id.enter_otpPEET);
        resetPassBTN = (Button) findViewById(R.id.reset_PassBTN);
        new_passwordBtn = (Button) findViewById(R.id.new_passwordBtn);
        passwordCreation = (LinearLayout) findViewById(R.id.reset_passwordLL);

        enterOtpPEET.setAnimateText(true);
        if (enterOtpPEET != null) {
            enterOtpPEET.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    enteredOTP = str.toString().trim();
                }
            });

            new_passwordBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String enteredPassword = enterPasswordTIET.getText().toString().trim();
                    String confirmPassword = confirmPasswordTIET.getText().toString().trim();
                    String userId = enter_UserIdEt.getText().toString().trim().toUpperCase();

                    if(userId == null || userId.equalsIgnoreCase("") || userId.length() < 4){
                        enter_UserIdEt.setError(getResources().getString(R.string.et_error_msg));
                    } else if (enteredPassword.length() < 6) {
                        enterPasswordTIET.setError(getString(R.string.error_password));
                    } else if (confirmPassword.length() < 6) {
                        confirmPasswordTIET.setError(getString(R.string.error_confirm_password));
                    } else {
                        if (enteredPassword.equalsIgnoreCase(confirmPassword)) {
                            String generatedOTP = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyOtp(), OTPVerification.this);
                            AppUtility.getInstance().showLog("enteredOTP=" + enteredOTP + "generatedOTP=" + generatedOTP, OTPVerification.class);
                            if (enteredOTP.equalsIgnoreCase(generatedOTP)) {
                                    registerPasswordOnServer(PrefrenceFactory.getInstance()
                                            .getSharedPrefrencesData(PrefrenceManager.getPrfKeyMobileNumber(),
                                            OTPVerification.this), confirmPassword,userId);
                            } else {
                                Toast.makeText(OTPVerification.this, getString(R.string.toast_otp_wrong), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            confirmPasswordTIET.setError(getString(R.string.error_confirm_password_not_matched));
                        }
                    }

                }
            });

            resetPassBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generatedOTP = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyOtp(), OTPVerification.this);
                    if(enteredOTP.equalsIgnoreCase(generatedOTP))
                    {
                        passwordCreation.setVisibility(View.VISIBLE);
                        resetPassBTN.setVisibility(View.GONE);
                        new_passwordBtn.setVisibility(View.VISIBLE);
                    }else
                    {
                        Toast.makeText(OTPVerification.this,getResources().getString(R.string.toast_otp_msg),Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });

            /*resetPassBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String enteredPassword = enterPasswordTIET.getText().toString().trim();
                    String confirmPassword = confirmPasswordTIET.getText().toString().trim();

                    if (enteredPassword.length() < 6) {
                        enterPasswordTIET.setError(getString(R.string.error_password));
                    }
                    if (confirmPassword.length() < 6) {
                        confirmPasswordTIET.setError(getString(R.string.error_confirm_password));
                    } else {

                        if (enteredPassword.equalsIgnoreCase(confirmPassword)) {

                            String generatedOTP = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyOtp(), OTPVerification.this);
                            AppUtility.getInstance().showLog("enteredOTP=" + enteredOTP + "generatedOTP=" + generatedOTP, OTPVerification.class);
                            if (enteredOTP.equalsIgnoreCase(generatedOTP)) {

                                registerPasswordOnServer(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyMobileNumber(), OTPVerification.this), confirmPassword,PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(),OTPVerification.this));

                            } else {
                                Toast.makeText(OTPVerification.this, getString(R.string.toast_otp_wrong), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            confirmPasswordTIET.setError(getString(R.string.error_confirm_password_not_matched));
                        }
                    }
                }
            });*/
        }
    }

    private void registerPasswordOnServer(String mobileNo, String registerePassword,String userId) {

        if (!NetworkFactory.isInternetOn(OTPVerification.this)) {
            DialogFactory.getInstance().showErrorAlertDialog(OTPVerification.this, getString(R.string.NO_INTERNET_TITLE), getString(R.string.INTERNET_MESSAGE), "OK");
        } else {
            ProgressDialog progressDialog = DialogFactory.getInstance().showProgressDialog(OTPVerification.this, false);
            progressDialog.show();
            /*************************json request*****************************************************************************************/
            String json_reset_paas_url = AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/forgotpassword/resetPassword";

            JSONObject restObject =new JSONObject();
            try {
                restObject.accumulate("mobileno",mobileNo);
                restObject.accumulate("password",AppUtility.getInstance().getSha256(registerePassword));
                restObject.accumulate("login_id",userId);
                restObject.accumulate("imei_no", new LoginActivity().getIMEINo1());
                restObject.accumulate("device_name", new LoginActivity().getDeviceInfo());
                restObject.accumulate("location_coordinate", SyncData.getInstance(OTPVerification.this).getCordinates());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest restRequest = new JsonObjectRequest(Request.Method.POST, json_reset_paas_url, restObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //{"status":"Updated Successfully!!!"}
                    progressDialog.dismiss();
                    try {
                        status = response.getString("status");
                        if(!status.equalsIgnoreCase("Updated Successfully!!!")){
                            status =  response.getString("status") + getResources().getString(R.string.dialog_msg);
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerification.this);
                        builder.setTitle(R.string.reset_pass_TV);
                        builder.setCancelable(false);
                        builder.setMessage(status);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (status.equalsIgnoreCase("Updated Successfully!!!")) {
                                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyOtp(), OTPVerification.this);
                                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyMobileNumber(), OTPVerification.this);
                                    AppUtility.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
                                }else {
                                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyOtp(), OTPVerification.this);
                                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyMobileNumber(), OTPVerification.this);
                                    AppUtility.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
                                }
                                dialogInterface.dismiss();
                            }
                        }).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    DialogFactory.getInstance().showErrorAlertDialog(OTPVerification.this, getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "ok");
                    AppUtility.getInstance().showLog("volleyError.getMessage()" + error.getMessage(), OTPVerification.class);
                }
            });

            RequestQueue requestQueue1 = Volley.newRequestQueue(this);
            requestQueue1.add(restRequest);


            /********************************************************************************************************************/


            /***********************************************string request for reset password**********************************************************/

          /*  String REGISTER_USER_PASS = AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/forgotpassword/resetPassword?mobileno="+mobileNo+"&password="+AppUtility.getInstance().getSha256(registerePassword)+"&userid="+userId;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_USER_PASS, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    progressDialog.dismiss();
                    try {
                        JSONArray mobileStatusJSONArray = new JSONArray(s);
                        for (int i = 0; i < mobileStatusJSONArray.length(); i++) {
                            JSONObject mobileStatusJSONObject = mobileStatusJSONArray.getJSONObject(i);
                             status = mobileStatusJSONObject.getString("status");
                            if(!status.equalsIgnoreCase("Updated Successfully!!!")){
                                status =  mobileStatusJSONObject.getString("status") + "Please try again with another userId and mobile number";
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerification.this);
                            builder.setTitle(R.string.reset_pass_TV);
                            builder.setCancelable(false);
                            builder.setMessage(status);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (status.equalsIgnoreCase("Updated Successfully!!!")) {
                                        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyOtp(), OTPVerification.this);
                                        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyMobileNumber(), OTPVerification.this);
                                        AppUtility.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
                                    }else {
                                        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyOtp(), OTPVerification.this);
                                        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyMobileNumber(), OTPVerification.this);
                                        AppUtility.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
                                    }
                                    dialogInterface.dismiss();
                                }
                            }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    DialogFactory.getInstance().showErrorAlertDialog(OTPVerification.this, getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "ok");
                    AppUtility.getInstance().showLog("volleyError.getMessage()" + volleyError.getMessage(), OTPVerification.class);
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtility.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
    }
}
