package com.shaksham.presenter.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.shaksham.R;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.database.DaoMaster;
import com.shaksham.model.database.DaoSession;
import com.shaksham.model.database.LoginInfo;
import com.shaksham.presenter.Fragments.BaselineFragment;
import com.shaksham.presenter.Fragments.EvaluationFormFragment;
import com.shaksham.presenter.Fragments.PhotoGps;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.FileManager;
import com.shaksham.utils.FileUtility;
import com.shaksham.utils.GPSTracker;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import org.greenrobot.greendao.database.Database;

import java.util.Date;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static SplashActivity instance = null;
    public DaoSession daoSession;
    public Context context;
    TextView textView;

    public synchronized static SplashActivity getInstance() {
        if (instance==null)
            instance=new SplashActivity();
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = getApplicationContext();

        getLanguageCode();



        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefPinStatus(), "0", SplashActivity.this);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "saksham0-db");
        Database db = helper.getWritableDb();
        if (db != null) {
            daoSession = new DaoMaster(db).newSession();

            SplashActivity.getInstance().setDaoSession(daoSession);
        }
        if(DaycheckForLogouts()){
            String timeStamp =DateFactory.getInstance().getDateTime();
            String cordinates =getCordinates();
            PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyLogoutCordinates(),cordinates,SplashActivity.this);
            PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyLogoutTimeStamp(),timeStamp,SplashActivity.this);
            PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), SplashActivity.this);
            new HomeActivity().clearDatabaseMasterTables();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        GPSTracker gpsTracker = new GPSTracker(SplashActivity.this);
        if(!AppUtility.isGPSEnabled(SplashActivity.this)){

            DialogFactory.getInstance().showAlertDialog(SplashActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.gps_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
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
            String  latitude = String.valueOf(gpsTracker.latitude);
            String longitude = String.valueOf(gpsTracker.longitude);

            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);
            loadNextScreenWithDelay();

          /*
            //write condition for check android version and application  not run less then lolipop
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                DialogFactory.getInstance().showAlertDialog(SplashActivity.this, R.drawable.ic_launcher_background,
                        "This Application is not used in your android device please Upgrade your mobile",
                        "", "Ok",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                },false);

            }else {
                gpsTracker.getLocation();
                String  latitude = String.valueOf(gpsTracker.latitude);
                String longitude = String.valueOf(gpsTracker.longitude);

                AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);
                loadNextScreenWithDelay();
            }*/

        }

    }

    private String logOutDateLocalDB(){
        String date="";
        if (SplashActivity.getInstance().getDaoSession()
                .getLoginInfoDao()
                .queryBuilder()
                .build()
                .list().size()>0)
            date=SplashActivity.getInstance().getDaoSession()
                .getLoginInfoDao()
                .queryBuilder()
                .build()
                .list()
                .get(0)
                .getServerTimeStamp();
        if (date.equalsIgnoreCase(""))
            date=DateFactory.getInstance().getTodayDate();
        return date;
    }

    private int logOutDaysLocalDB(){
        int days=0;
        if (SplashActivity.getInstance().getDaoSession()
                .getLoginInfoDao()
                .queryBuilder()
                .build()
                .list().size()>0)
            days=Integer.parseInt(SplashActivity.getInstance().getDaoSession()
                    .getLoginInfoDao()
                    .queryBuilder()
                    .build()
                    .list()
                    .get(0)
                    .getLogoutDays());
        return days;
    }
    private void getLanguageCode() {
        String getLanguageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyLanguageCode(), SplashActivity.this);
        if (getLanguageCode.equalsIgnoreCase("")) {
            getLanguageCode = "en";
        }

        AppUtility.getInstance().setLocale(getLanguageCode, getResources(), SplashActivity.this);
    }

    private void loadNextScreenWithDelay() {
        android.os.Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNextScreen();
            }
        }, 3000);
    }
    private void goToNextScreen() {
        String loginSatus = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), SplashActivity.this);
        AppUtility.getInstance().showLog("loginStatus"+loginSatus,SplashActivity.class);
        if (loginSatus == null || loginSatus.equalsIgnoreCase("")) {
            AppUtility.getInstance().makeIntent(SplashActivity.this, LoginActivity.class, true);
        } else {
            AppUtility.getInstance().makeIntent(SplashActivity.this, MpinActivity.class, true);
        }
    }

    public void storeUnsyncedDataInFile(Context  context) {
        new BaselineFragment().saveDbDataIntoBackupFile(context);
        new PhotoGps().saveDbDataInBackUpFile(context);
        new EvaluationFormFragment().saveEvaluationDataInLocalFile(context);
    }


    private boolean DaycheckForLogouts() {
        boolean performLogout=false;
        String serverDateTimeandDays=getServerDateFromLDB();
        if (serverDateTimeandDays!=null){
            String [] serverDTAndDs=serverDateTimeandDays.split(",");
            String logoutDate= DateFactory.getInstance().getNextDate(serverDTAndDs[0],Integer.parseInt(serverDTAndDs[1]),"dd-MM-yyyy");
            Date convertedLogOutDate = DateFactory.getInstance().getDateFormate(logoutDate);
            //set today date in right formate
            String todayDate =DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate());
            Date convertedTodayDate = DateFactory.getInstance().getDateFormate(todayDate);
            AppUtility.getInstance().showLog("serverDateTime"+serverDateTimeandDays,SplashActivity.class);

            if(convertedTodayDate.compareTo(convertedLogOutDate)>=0){
                performLogout=true;
            }
        } else {
            serverDateTimeandDays="null";
            AppUtility.getInstance().showLog("serverDateTime"+serverDateTimeandDays,SplashActivity.class);
        }
        return performLogout;
    }

    private String getServerDateFromLDB(){
        List<LoginInfo> loginInfoDataList=SplashActivity.getInstance().getDaoSession().getLoginInfoDao().queryBuilder().build().list();
        if (loginInfoDataList.size()!=0) {
            return loginInfoDataList.get(0).getServerTimeStamp()+","+ loginInfoDataList.get(0).getLogoutDays();
        }
        return null;
    }
    public String getCordinates(){
        String latLong ="";
        GPSTracker gpsTracker = new GPSTracker(SplashActivity.this);
        if(!AppUtility.isGPSEnabled(SplashActivity.this)){
            DialogFactory.getInstance().showAlertDialog(SplashActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.gps_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
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
            String  latitude = String.valueOf(gpsTracker.latitude);
            String longitude = String.valueOf(gpsTracker.longitude);
            latLong =latitude+","+longitude;
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, SplashActivity.class);
        }
        return latLong;
    }

}
