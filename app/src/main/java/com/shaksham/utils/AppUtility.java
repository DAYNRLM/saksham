package com.shaksham.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shaksham.model.database.DaoSession;

import org.greenrobot.greendao.AbstractDao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AppUtility extends AppCompatActivity {
        public static AppUtility utilsInstance;
        private static boolean wantToShow = false;

        public synchronized static AppUtility getInstance() {
            if (utilsInstance == null) {
                utilsInstance = new AppUtility();
            }
            return utilsInstance;
        }

        public void showLog(String logMsg, Class application) {
            if (wantToShow) {
                Log.d(application.getName(), logMsg);
            }
        }

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, String tag, boolean addTobackStack, int container) {

        // transaction
        FragmentTransaction ft = fragmentManager.beginTransaction();
/*
        FragmentTransactionExtended fragmentTransactionExtended=new FragmentTransactionExtended(HomeActivity.context,ft, DashBoardFragment.newInstance(), AddLocation.getInstance(), R.id.fragmentContainer);
*/
        if (addTobackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public String loadAssetData(Context context, String filName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String[] getMonthList() {
        String[] monthList = {"January", "Febrary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthList;
    }

    public void makeIntent(Context context, Class activityToGo, boolean clearBackActitvity) {
        if (context != null) {
            Intent intent = new Intent(context, activityToGo);

            if (clearBackActitvity) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            context.startActivity(intent);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus()) {
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

/*    public void syncDataWorker(){

        WorkManager intanceWorkManager;
        OneTimeWorkRequest.Builder oneTimeReq;
        OneTimeWorkRequest oneTimeWorkRequest;

        intanceWorkManager = WorkManager.getInstance();
        oneTimeReq = new OneTimeWorkRequest.Builder(SyncData.class);
        oneTimeWorkRequest = oneTimeReq.build();
        intanceWorkManager.enqueue(oneTimeWorkRequest);

        intanceWorkManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {
                    WorkInfo.State state = workInfo.getState();

                }
            }
        });
    }*/


    /*public static void setLocale(String lang, Resources res) {

        Locale myLocale = new Locale(lang);
        // Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }*/
    public void setLocale(String localeName, Resources res, Context context) {

        Locale myLocale = new Locale(localeName);
        // Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

       /* if (!localeName.equals(localeName)) {
            Locale  myLocale = new Locale(localeName);
           // Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            *//*Intent refresh = new Intent(context, HomeActivity.class);
            refresh.putExtra(currentLang, localeName);
            context.startActivity(refresh);*//*
        } else {
            Toast.makeText(context, "Language already selected!", Toast.LENGTH_SHORT).show();
        }*/
    }

    public String getSha256(String plain_text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = null;
        try {
            hash = digest.digest(plain_text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytesToHex(hash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String removeCommaFromLast(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void clearDaoSession(DaoSession daoSession){
        daoSession.clear();
    }

    public void clearDAO(AbstractDao dao){
        dao.detachAll();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public  String encrypt(String actualValue) throws Exception {
        byte[] dataToBeEncrypt = actualValue.getBytes();
        byte[] key = AppConstant.ENCRPT_DECRYPT_KEY;
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        //encrypted = cipher.doFinal(dataToBeEncrypt);
        byte[] encrypted = Base64.getEncoder().encode(cipher.doFinal(dataToBeEncrypt));
        String encryptedValue = new String(encrypted);
        //Base64.getEncoder().encode(arg0);
        return encryptedValue;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  String decrypt(String encryptedValue) throws Exception {
        byte[] key = AppConstant.ENCRPT_DECRYPT_KEY;
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        //decrypted = cipher.doFinal(encryptedData);
        byte[] encryptedData = encryptedValue.getBytes();
        byte[] decrypted =cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        String decryptedValue = new String(decrypted);
        return decryptedValue;
    }

    public  String MD5Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    public void killAppInBackground(int timeInMilis){
        android.os.Handler handler= new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        },timeInMilis);
    }

    public static boolean isGPSEnabled(Context context) {
        boolean flag = false;
        // getting GPS status
        LocationManager locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;

    }



}
