package com.shaksham.presenter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shaksham.R;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.FileManager;
import com.shaksham.utils.FileUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import org.json.JSONException;
import org.json.JSONObject;


public class MpinActivity extends AppCompatActivity {
    Button btnMpinProceed;
    PinEntryEditText pinEntryEditText, pinConfirmMpinEditText;
    LinearLayout verifieLinearLayout, topLineraLayout;
    String getMpinFromPreference, checkMpin;
    EditText id_Fgd, password_Fgd;
    Button submit_Fgd;
    TextView fgtpin;
    String mPin, confirmMpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);
        LoginActivity loginActivity = new LoginActivity();
        String mPinFromFile = "";
        topLineraLayout = (LinearLayout) findViewById(R.id.topSetLinearLayout);
        btnMpinProceed = (Button) findViewById(R.id.btnMpinProceed);
        pinEntryEditText = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        pinConfirmMpinEditText = (PinEntryEditText) findViewById(R.id.txt_pin_entry_confirm);
        verifieLinearLayout = (LinearLayout) findViewById(R.id.verifieLinerLayout);

        if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpin(), AppConstant.mpinFileName)) {
            try {
                JSONObject mPinFileObject = new LoginActivity().readMpinFile();
                AppUtility.getInstance().showLog("mPinFileObject" + mPinFileObject, MpinActivity.class);
                mPinFromFile = mPinFileObject.getString("mPin");
                AppUtility.getInstance().showLog("mPinFromFile" + mPinFromFile, MpinActivity.class);
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyMpin(), mPinFromFile, MpinActivity.this);
            } catch (JSONException j) {
                AppUtility.getInstance().showLog("nbd" + j, MpinActivity.class);
            }
        }/* else {
        LoginActivity loginActivity=new LoginActivity();
        String mPinFromFile="";
        topLineraLayout = (LinearLayout)findViewById(R.id.topSetLinearLayout);
        btnMpinProceed =(Button)findViewById(R.id.btnMpinProceed);
        pinEntryEditText  =(PinEntryEditText)findViewById(R.id.txt_pin_entry);
        pinConfirmMpinEditText = (PinEntryEditText)findViewById(R.id.txt_pin_entry_confirm);
        verifieLinearLayout = (LinearLayout)findViewById(R.id.verifieLinerLayout);
        if((PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefPinStatus(),MpinActivity.this))!="1") {
            if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpin(), AppConstant.mpinFileName)) {
                try {
                    JSONObject mPinFileObject = new LoginActivity().readMpinFile();
                    AppUtility.getInstance().showLog("mPinFileObject" + mPinFileObject, MpinActivity.class);
                    mPinFromFile = mPinFileObject.getString("mPin");
                    AppUtility.getInstance().showLog("mPinFromFile" + mPinFromFile, MpinActivity.class);
                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyMpin(), mPinFromFile, MpinActivity.this);
                } catch (JSONException j) {
                    AppUtility.getInstance().showLog("nbd" + j, MpinActivity.class);
                }
            }
        }/* else {
            PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyMpin(),"",MpinActivity.this);
        }*/
        mPIN();
    }

    private void mPIN() {

        getMpinFromPreference = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyMpin(), MpinActivity.this);
       /* if (getMpinFromPreference==null || getMpinFromPreference.equalsIgnoreCase("")){

        }*/
/*        Dialog dialog;
        dialog=new Dialog(MpinActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.forgot_mpin_dialog);
        id_Fgd=(EditText) dialog.findViewById(R.id.id_fgd);
        password_Fgd=(EditText) dialog.findViewById(R.id.passwd_fgd);
        submit_Fgd=(Button) dialog.findViewById(R.id.submit_fgd);*/

        fgtpin=(TextView)findViewById(R.id.fgt_pin);

        if((getMpinFromPreference!=null) && (getMpinFromPreference!="") &&((PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefPinStatus(),MpinActivity.this))!="1")){
/*
            verifieLinearLayout.setVisibility(View.VISIBLE);

            fgtpin.setPaintFlags(fgtpin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


            final PinEntryEditText pinEntry2 = findViewById(R.id.tvEnterMpin);
            if (pinEntry2 != null) {
                pinEntry2.setAnimateText(true);
                pinEntry2.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                    @Override
                    public void onPinEntered(CharSequence str) {

                        checkMpin = str.toString();

                        if (checkMpin.equalsIgnoreCase(getMpinFromPreference)) {
                            Toast.makeText(MpinActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                            btnMpinProceed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Toast.makeText(MpinActivity.this,"inside the button",Toast.LENGTH_LONG).show();
                                    if((checkMpin!=null)&& (checkMpin.equalsIgnoreCase(getMpinFromPreference))) {

                                        Intent intent = new Intent(MpinActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }
                                    else {

                                        Toast.makeText(MpinActivity.this,"Please enter Mpin",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            pinEntry2.setError(true);
                            Toast.makeText(MpinActivity.this, "Please Enter Corrrect Mpin", Toast.LENGTH_SHORT).show();
                            checkMpin=null;
                            pinEntry2.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pinEntry2.setText(null);
                                    checkMpin=null;
                                }
                            }, 1000);
                        }
                    }
                });

            }

*/

            AppUtility.getInstance().makeIntent(MpinActivity.this, VerifyMpinActivity.class, true);

        } else {
            topLineraLayout.setVisibility(View.VISIBLE);
        }
        final PinEntryEditText pinEntry = findViewById(R.id.txt_pin_entry);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    mPin = str.toString();
                }
            });
        }

        final PinEntryEditText pinEntry2 = findViewById(R.id.txt_pin_entry_confirm);
        if (pinEntry2 != null) {
            pinEntry2.setAnimateText(true);
            pinEntry2.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    confirmMpin = str.toString();
                    if (confirmMpin.equalsIgnoreCase(mPin)) {
                        LoginActivity loginActivity = new LoginActivity();
                       // Toast.makeText(MpinActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        new LoginActivity().saveLoginDetailsInLocalFile(MpinActivity.this, loginActivity.getLoginIdFromLocal(), confirmMpin, "0", loginActivity.getMobileNoFromLocal(), loginActivity.getAppVersionFromLocal());
                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyMpin(), confirmMpin, MpinActivity.this);

                    } else {
                        pinEntry2.setError(true);
                        Toast.makeText(MpinActivity.this, getString(R.string.toast_FAIL), Toast.LENGTH_SHORT).show();
                        pinEntry2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pinEntry2.setText(null);
                                confirmMpin = null;
                            }
                        }, 1000);
                    }
                }
            });

        }

        btnMpinProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mPin != null && confirmMpin != null) && (mPin.equalsIgnoreCase(confirmMpin))) {
                    AppUtility.getInstance().makeIntent(MpinActivity.this, HomeActivity.class, true);

                }

            }
        });


    }
}
