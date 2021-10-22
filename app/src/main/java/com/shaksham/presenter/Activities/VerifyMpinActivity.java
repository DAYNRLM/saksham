package com.shaksham.presenter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.shaksham.R;
import com.shaksham.model.database.WebRequestData;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.GPSTracker;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SingletonVolley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class  VerifyMpinActivity extends AppCompatActivity {
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private final static String TAG = VerifyMpinActivity.class.getSimpleName();
    private static String TRUE_CODE;
    private TextView fgtPin;
    EditText idFgd, passwordFgd;
    private ProgressDialog progressDialog;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mpin);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        TRUE_CODE = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyMpin(), VerifyMpinActivity.this);
        fgtPin = (TextView) findViewById(R.id.forgot_pin);
        fgtPin.setPaintFlags(fgtPin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        progressDialog = DialogFactory.getInstance().showProgressDialog(VerifyMpinActivity.this, false);
        SplashActivity.getInstance().getDaoSession().getWebRequestDataDao().deleteAll();
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLength(4);
        fgtPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyMpinActivity.this, ChangeMpinActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        mPinLockView.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                Log.d(TAG, "lock code: " + pin);
                if (pin.equals(TRUE_CODE)) {
                    checkWebRequest(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(), VerifyMpinActivity.this), pin);
                } else {
                    Toast.makeText(VerifyMpinActivity.this, VerifyMpinActivity.this.getString(R.string.faild_code), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerifyMpinActivity.this, VerifyMpinActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }

            @Override
            public void onEmpty() {
                Log.d(TAG, "lock code is empty!");
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    private void checkWebRequest(String userId, String mpin) {
        /**********************************************request for post json ***********************************************/
        String JSON_REQUEST_URL = AppConstant.HTTP_TYPE + "://" + AppConstant.IP_ADDRESS + "/" + AppConstant.API_TYPE + "/services/sakshamchk/assignuser";
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.accumulate("login_id", userId);
            requestObject.accumulate("imei_no", PrefrenceFactory.getInstance()
                    .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceImei(), VerifyMpinActivity.this));
            requestObject.accumulate("device_name", PrefrenceFactory.getInstance()
                    .getSharedPrefrencesData(PrefrenceManager.getPrefKeyDeviceInfo(), VerifyMpinActivity.this));
            requestObject.accumulate("location_coordinate", getCordinates());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (NetworkFactory.isInternetOn(this)) {
            progressDialog.show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, JSON_REQUEST_URL, requestObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray responseArray = response.getJSONArray("data");
                        if(responseArray.getJSONObject(0).has("status")){
                            JSONObject object = responseArray.getJSONObject(0);
                            String status = object.getString("status");
                            if (status.equalsIgnoreCase("OK !!!")) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(VerifyMpinActivity.this, HomeActivity.class);
                                intent.putExtra("code", mpin);
                                startActivity(intent);
                                finish();
                            }

                        }else {
                            WebRequestData webRequestData = new WebRequestData();
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                if (jsonObject.has("village_code")) {
                                    webRequestData.setVillageCode(jsonObject.getString("village_code"));
                                    webRequestData.setStatus(jsonObject.getString("village_status"));
                                    SplashActivity.getInstance().getDaoSession().getWebRequestDataDao().insert(webRequestData);
                                }
                            }
                            progressDialog.dismiss();
                            Intent intent = new Intent(VerifyMpinActivity.this, VillageModifiedActivity.class);
                            intent.putExtra("code", mpin);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        AppUtility.getInstance().showLog("webRequestExc" + e, VerifyMpinActivity.class);
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    AppUtility.getInstance().showLog("webRequestServerError" + error, VerifyMpinActivity.class);
                    Intent intent = new Intent(VerifyMpinActivity.this, HomeActivity.class);
                    intent.putExtra("code", mpin);
                    startActivity(intent);
                    finish();

                }
            });
            SingletonVolley.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        } else {
            Intent intent = new Intent(VerifyMpinActivity.this, HomeActivity.class);
            intent.putExtra("code", mpin);
            startActivity(intent);
            finish();
        }
    }

    public String getCordinates() {
        String latLong = "";
        GPSTracker gpsTracker = new GPSTracker(VerifyMpinActivity.this);
        if (!AppUtility.isGPSEnabled(VerifyMpinActivity.this)) {

        } else {
            gpsTracker.getLocation();
            String latitude = String.valueOf(gpsTracker.latitude);
            String longitude = String.valueOf(gpsTracker.longitude);
            latLong = latitude + "," + longitude;
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, SplashActivity.class);
        }
        return latLong;
    }
}
