package com.shaksham.presenter.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.shaksham.BuildConfig;
import com.shaksham.R;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.PojoData.BaselineDonePojo;
import com.shaksham.model.database.DaoSession;
import com.shaksham.model.database.EvaluationMasterLocationData;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterShgDataDao;
import com.shaksham.model.database.LoginInfo;
import com.shaksham.presenter.Fragments.AddLocation;
import com.shaksham.presenter.Fragments.BaseLineFilterShgFragment;
import com.shaksham.presenter.Fragments.DashBoardFragment;
import com.shaksham.presenter.Fragments.EvaluationFragment;
import com.shaksham.presenter.Fragments.EvaluationShgDetailFragment;
import com.shaksham.presenter.Fragments.LanguageSelectionFragment;
import com.shaksham.presenter.Fragments.ViewReport;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.GPSTracker;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class HomeActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {
    public Toolbar mToolbar;
    private TextView tvToolbarTitle, tvUserName, tvUserMobile, tvAppVersion;
    public NavigationView navigationView;
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private HomeActivity.OnBackPressedListener onBackPressedListener;
    private boolean mChangeFragment;
    private int selectedItem;
    public static Context context;
    List<EvaluationMasterShgData> maximumEvaluationDateList;
    Runnable r1;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        handler =new Handler();
        AppUtility.getInstance().showLog("loginStatus" + PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), HomeActivity.this), HomeActivity.class);
        setupToolbar();
        setupNavigationView();
        AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), true, R.id.fragmentContainer);
        AddTrainingPojo.getInstance();
       /* GPSTracker gpsTracker = new GPSTracker(HomeActivity.this);
        String latitude = String.valueOf(gpsTracker.latitude);
        String longitude = String.valueOf(gpsTracker.longitude);
        AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, HomeActivity.class);
        AddTrainingPojo.addTrainingPojo.setGpsLoation("" + latitude + "," + longitude);*/
        BaselineDonePojo.getInstance();
    }
    private boolean isTimeZoneAutomatic(Context c) {
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0) == 1;
        }*/

        return true;
      }
    private boolean isAutoDateEnable(Context c) {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }*/

        return true;
       }

    private void setupToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) findViewById(R.id.tbTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
    }

    private void setupNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.setScrimColor(Color.parseColor("#99000000"));
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        View headerView = navigationView.getHeaderView(0);
        tvUserName = (TextView) headerView.findViewById(R.id.tvUserName);
        tvUserMobile = (TextView) headerView.findViewById(R.id.tvUserMobileNumber);
        tvAppVersion = (TextView) headerView.findViewById(R.id.tvAppVersion);
        tvAppVersion.setText(getResources().getString(R.string.app_version) + new LoginActivity().getAppVersionFromLocal());
        List<LoginInfo> loginInfoList = SplashActivity.getInstance().getDaoSession().getLoginInfoDao().queryBuilder().list();
        for (LoginInfo loginInfo : loginInfoList) {
            tvUserName.setText(loginInfo.getLoginId());
            tvUserMobile.setText(loginInfo.getMobileNo());
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                if (mChangeFragment) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    mChangeFragment = false;
                    switch (selectedItem) {
                        case R.id.menu_changLanguage:
                            AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), LanguageSelectionFragment.newInstance()
                                    , LanguageSelectionFragment.class.getSimpleName(), true, R.id.fragmentContainer);

                           /* if (fragmentManager.findFragmentByTag(LanguageSelectionFragment.class.getSimpleName()) == null) {
                                AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), LanguageSelectionFragment.newInstance()
                                        , LanguageSelectionFragment.class.getSimpleName(), true, R.id.fragmentContainer);

                            } else {
                                fragmentManager.popBackStack(LanguageSelectionFragment.class.getSimpleName(), 0);
                            }*/
                            break;
                        case R.id.menu_Logout:
                            DialogFactory.getInstance().showAlertDialog(HomeActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.msg_logout), getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new SplashActivity().storeUnsyncedDataInFile(HomeActivity.this);
                                    /**save time stamp and cordinate for send in login time**/
                                    String timeStamp =DateFactory.getInstance().getDateTime();
                                    String cordinates =getCordinates();
                                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyLogoutCordinates(),cordinates,HomeActivity.this);
                                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyLogoutTimeStamp(),timeStamp,HomeActivity.this);
                                    PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), HomeActivity.this);
                                    clearDatabaseMasterTables();
                                    logoutApp();
                                }
                            }, getString(R.string.str_no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, false);
                            break;
                        case R.id.menu_dashbord:
                            AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                            break;
                        case R.id.menu_baseline:
                            if (maximumEvaluationDateList.size() > 0) {
                                Toast.makeText(HomeActivity.this, HomeActivity.this.getString(R.string.evaluation_ro_evaluation_msg_first), Toast.LENGTH_SHORT).show();
                            } else {
                                AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), new BaseLineFilterShgFragment(), BaseLineFilterShgFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                            }
                            break;
                        case R.id.menu_addtraining:
                            if (maximumEvaluationDateList.size() > 0) {
                                Toast.makeText(HomeActivity.this, HomeActivity.this.getString(R.string.evaluation_ro_evaluation_msg_first), Toast.LENGTH_SHORT).show();
                            } else {
                                AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), AddLocation.getInstance(), AddLocation.class.getSimpleName(), false, R.id.fragmentContainer);
                            }
                            break;
                        case R.id.menu_evaluation:
                            //clear All preference for all evaluation fragment
                            AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), EvaluationShgDetailFragment.newInstance(""), EvaluationShgDetailFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                            break;
                        case R.id.menu_report: {
                            AppUtility.getInstance().replaceFragment(getSupportFragmentManager(), ViewReport.newInstance(), ViewReport.class.getSimpleName(), false, R.id.fragmentContainer);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                AppUtility.hideSoftKeyboard(HomeActivity.this);
                getMaximumEvaluationDataList();
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
       /* DashBoardFragment.newInstance().getAllTrainingListFromLocalDb();
       if (DashBoardFragment.newCreatedevaluationMasterShgData.size()<=0){
           actionBarDrawerToggle.syncState();
       }*/
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);
    }
    //***************************for get maximum evaluation data list *********************************
    private void getMaximumEvaluationDataList() {
        Date convertedEvaluationDate, convertedEvaluationMaximunDate, convertedTodayDate;
        String tDate = DateFactory.getInstance().getTodayDate();
        String todaydate = DateFactory.getInstance().changeDateValue(tDate);
        convertedTodayDate = DateFactory.getInstance().getDateFormate(todaydate);
        maximumEvaluationDateList = new ArrayList<>();
        List<EvaluationMasterShgData> newEvaluationDateList = new ArrayList<>();
        List<EvaluationMasterLocationData> evaluationMasterLocationData = SplashActivity.getInstance().getDaoSession().getEvaluationMasterLocationDataDao().queryBuilder().build().list();
        for (EvaluationMasterLocationData evaluationMasterLocationData1 : evaluationMasterLocationData) {
            String villageCode = evaluationMasterLocationData1.getVillageCode();
            List<EvaluationMasterShgData> evaluationMasterShgData = SplashActivity.getInstance()
                    .getDaoSession()
                    .getEvaluationMasterShgDataDao()
                    .queryBuilder().where(EvaluationMasterShgDataDao.Properties.VillageCode.eq(villageCode), EvaluationMasterShgDataDao.Properties.Evaluationdonestatus.eq("0"))
                    .build().list();
            for (EvaluationMasterShgData evaluationMasterShgData1 : evaluationMasterShgData) {
                String eDate = DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData1.getEvaluationDate());
                convertedEvaluationDate = DateFactory.getInstance().getDateFormate(eDate);
                String eMaximumdate = DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData1.getMaximunEvaluationdate());
                convertedEvaluationMaximunDate = DateFactory.getInstance().getDateFormate(eMaximumdate);
                if (convertedEvaluationDate.compareTo(convertedTodayDate) <= 0) {
                    newEvaluationDateList.add(evaluationMasterShgData1);
                    AppUtility.getInstance().showLog("newTrainingListSize" + newEvaluationDateList.size(), HomeActivity.class);
                }
            }
        }
        if (newEvaluationDateList.size() > 0) {
            AppUtility.getInstance().showLog("****SHOW LAYOUT**********" + newEvaluationDateList.size(), HomeActivity.class);
            for (EvaluationMasterShgData evaluationMasterShgData1 : newEvaluationDateList) {
                String evaluationMaximumDate = DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData1.getMaximunEvaluationdate());
                convertedEvaluationMaximunDate = DateFactory.getInstance().getDateFormate(evaluationMaximumDate);
                if (convertedEvaluationMaximunDate.compareTo(convertedTodayDate) <= 0) {
                    maximumEvaluationDateList.add(evaluationMasterShgData1);
                }
            }
        }
        AppUtility.getInstance().showLog("EVEALUATION_MAXIMUM_LIST" + maximumEvaluationDateList.size(), HomeActivity.class);
    }
    private void logoutApp() {
        finish();
        AppUtility.getInstance().makeIntent(HomeActivity.this, LoginActivity.class, true);
    }

    public void clearDatabaseMasterTables() {

        DaoSession daoSession = SplashActivity.getInstance().getDaoSession();
        /*.....................delete masters ........................*/
        daoSession.getLoginInfoDao().deleteAll();
        daoSession.getBlockDataDao().deleteAll();
        daoSession.getGpDataDao().deleteAll();
        daoSession.getVillageDataDao().deleteAll();
        daoSession.getShgDataDao().deleteAll();
        daoSession.getShgMemberDataDao().deleteAll();
        daoSession.getBlockLevelDataDao().deleteAll();
        daoSession.getModuleDataDao().deleteAll();
        daoSession.getQuestionInfoDetailDao().deleteAll();
        daoSession.getTitleInfoDetailDao().deleteAll();
        daoSession.getShgModuleDataDao().deleteAll();

        daoSession.getAddedTrainingsDataDao().deleteAll();
        daoSession.getAddedTrainingShgDataDao().deleteAll();
        daoSession.getAddedTrainingShgMemberDataDao().deleteAll();
        daoSession.getAddedTrainingShgModuleDataDao().deleteAll();

        daoSession.getViewReportDataDao().deleteAll();
        daoSession.getViewReportModuleDataDao().deleteAll();
        daoSession.getViewReportMonthDataDao().deleteAll();
        daoSession.getViewReportTrainingDataDao().deleteAll();

        daoSession.getEvaluationMasterLocationDataDao().deleteAll();
        daoSession.getEvaluationMasterShgDataDao().deleteAll();
        daoSession.getEvaluationMasterTrainingDataDao().deleteAll();

        /* ....delete sync daos....*/

        daoSession.getBaselineSyncDataDao().deleteAll();
        daoSession.getBaslineQuestionSyncDataDao().deleteAll();

        daoSession.getEvaluationSyncDataDao().deleteAll();
        daoSession.getEvaluationSyncQuestionDataDao().deleteAll();
        daoSession.getEvaluationSyncShgDataDao().deleteAll();

        daoSession.getTrainingInfoDataDao().deleteAll();
        daoSession.getTrainingLocationInfoDao().deleteAll();
        daoSession.getTrainingModuleInfoDao().deleteAll();
        daoSession.getTrainingShgAndMemberDataDao().deleteAll();

        //***********clear web request database****************
        daoSession.getWebRequestDataDao().deleteAll();
    }

    public void setToolBarTitle(String toolBarTitle) {
        tvToolbarTitle.setText(toolBarTitle);
        assert getSupportActionBar() != null;
        getSupportActionBar().show();
    }

    @Override
    public void onBackStackChanged() {
        int mBackStackCount = getSupportFragmentManager().getBackStackEntryCount();

        if (mBackStackCount > 0) {
            AppUtility.getInstance().showLog("Fragment count:-" + getSupportFragmentManager().getBackStackEntryCount(), HomeActivity.class);
            AppUtility.getInstance().showLog("fragment manager" + getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName(), HomeActivity.class);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        AppUtility.getInstance().showLog("menuItem"+menuItem, HomeActivity.class);
        menuItem.setChecked(!menuItem.isChecked());
        selectedItem = menuItem.getItemId();
        AppUtility.getInstance().showLog("selectedItem"+selectedItem, HomeActivity.class);
        mDrawerLayout.closeDrawers();
        mChangeFragment = true;
        return true;
    }

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void showActionDrawerToggleToolbar() {
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
    }

    @Override
    public void onPause() {
        super.onPause();

       /* Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.this.finish();
                System.exit(0);;
            }
        },5000);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(r1);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false); // disables default title

        GPSTracker gpsTracker = new GPSTracker(HomeActivity.this);
        if(!AppUtility.isGPSEnabled(HomeActivity.this)){
            DialogFactory.getInstance().showAlertDialog(HomeActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.gps_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
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
            String latitude = String.valueOf(gpsTracker.latitude);
            String longitude = String.valueOf(gpsTracker.longitude);
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, HomeActivity.class);
            AddTrainingPojo.addTrainingPojo.setGpsLoation("" + latitude + "," + longitude);

        }

        /**********this is used for kill app in forground after 15 min****************/
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);;
            }
        },(30*60000));*/
        if(!isTimeZoneAutomatic(HomeActivity.this)){

            DialogFactory.getInstance().showAlertDialog(HomeActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.auto_time_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
                    startActivity(intent);
                }
            }, "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            },false);
        }else if (!isAutoDateEnable(HomeActivity.this)){
            DialogFactory.getInstance().showAlertDialog(HomeActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.auto_time_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
                    startActivity(intent);
                }
            }, "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            },false);
        }
    }
    private boolean getCurrentFragmentName(String fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentTag.equalsIgnoreCase(fragmentName);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            /*int count = getSupportFragmentManager().getBackStackEntryCount();
            AppUtility.getInstance().showLog("count"+count,HomeActivity.class);
            if (count == 1) //1 == HomeFragment
            {
                DialogFactory.getInstance().showAlertDialog(HomeActivity.this, R.drawable.ic_launcher_background, "hiiii", "go to exit", "yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, "no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, false);
                AppUtility.getInstance().showLog("count fragment:-", HomeActivity.class);

            }*/
            //hiii
            if (onBackPressedListener != null) {
                onBackPressedListener.doBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
       // AppUtility.getInstance().killAppInBackground(3000);

        /* Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.this.finish();
                System.exit(0);;
            }
        },5000);*/
    }



    public void QuitApp(View view) {
        HomeActivity.this.finish();
        System.exit(0);
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
        handler.postDelayed(r1, AppConstant.BACKGROUND_TIME);
    }

    public String getCordinates(){
        String latLong ="";
        GPSTracker gpsTracker = new GPSTracker(HomeActivity.this);
        if(!AppUtility.isGPSEnabled(HomeActivity.this)){
            DialogFactory.getInstance().showAlertDialog(HomeActivity.this, R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.gps_not_enabled), "Go to seeting", new DialogInterface.OnClickListener() {
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
