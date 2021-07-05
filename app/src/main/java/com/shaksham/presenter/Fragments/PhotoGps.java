package com.shaksham.presenter.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonIOException;
import com.shaksham.R;
import com.shaksham.R2;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.database.TrainingInfoData;
import com.shaksham.model.database.TrainingLocationInfo;
import com.shaksham.model.database.TrainingModuleInfo;
import com.shaksham.model.database.TrainingShgAndMemberData;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.LoginActivity;
import com.shaksham.presenter.Activities.MpinActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.Cryptography;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.FileManager;
import com.shaksham.utils.FileUtility;
import com.shaksham.utils.GPSTracker;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SyncData;

import org.greenrobot.greendao.annotation.Convert;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.DSAKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.LogRecord;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PhotoGps extends Fragment implements HomeActivity.OnBackPressedListener {
    private String filePath;
    public final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private byte[] imageByteArray;
    private ProgressDialog progressDialog;

    @BindView(R.id.button_take_photo)
    Button btTakePhoto;
    @BindView(R.id.button_capture_gps)
    Button btCaptureGps;
    @BindView(R.id.button_upload_data)
    Button btUploadData;
    @BindView(R.id.image_on_take_photo)
    ImageView imgPhoto;

    String longitude,latitude;
    private Unbinder unbinder;
    int count=0;

    public static PhotoGps getInstance() {
        PhotoGps photoGps = new PhotoGps();
        return photoGps;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),PhotoGps.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.photo_gps, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = DialogFactory.getInstance().showProgressDialog(getContext(), false);

                if (imageByteArray != null) {

                    if( count==1 && !latitude.equalsIgnoreCase("0.0") && ! longitude.equalsIgnoreCase("0.0")) {

                        saveUserCreatedDataInLocalDB();
                        if (NetworkFactory.isInternetOn(getContext())) {
                            progressDialog.show();
                            PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyAddTrainningSync(), AppConstant.ADDTRAINING_SYNC_KEY, getContext());
                           // SyncData.getInstance(getContext().getApplicationContext()).syncData();
                            SyncData.getInstance(getContext()).syncData();
                            android.os.Handler handler = new Handler();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    saveDbDataInBackUpFile(getContext());
                                    progressDialog.dismiss();
                                     Toast.makeText(getContext(),getString(R.string.data_save_succesfully),Toast.LENGTH_SHORT).show();
                                    AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                                }
                            }, 7000);

                        } else {
                            saveDbDataInBackUpFile(getContext());
                            Toast.makeText(getContext(), getString(R.string.data_save_succesfully), Toast.LENGTH_SHORT).show();
                            AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                        }
                    }else{
                        Toast.makeText(getContext(), getString(R.string.Toast_toast_take_gps), Toast.LENGTH_SHORT).show();
                        getLocation();

                    }

                }

                else
                    Toast.makeText(getContext(), getString(R.string.toast_take_picture), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.button_capture_gps)
    void captureLatLong() {
        count=1;
        getLocation();
        Toast.makeText(getContext(), "latlong" + latitude + "...." + longitude, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.button_take_photo)
    void cameraOn() {
        count=1;
        getLocation();
     //   Toast.makeText(getContext(), "latlong" + latitude + "...." + longitude, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bmp = (Bitmap) data.getExtras().get("data");
                            imgPhoto.setImageBitmap(bmp);
                           /* storeImage(bmp);
                            Bitmap bitmap= readFilefromInternal(filePath);*/
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100 , baos);

                            imageByteArray = baos.toByteArray();
                            AppUtility.getInstance().showLog("byteArray" + imageByteArray, PhotoGps.class);
                        }
                    }, 1000);
                }

            }
    }

    @Override
    public void doBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(),  ConfirmDetails.getInstance(), ConfirmDetails.class.getSimpleName(), false, R.id.fragmentContainer);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

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

            AddTrainingPojo.addTrainingPojo.setGpsLoation(latitude + "lat"+"," + longitude+"long");
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);

        }

    }

    void getLocation() {
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
            latitude = String.valueOf(gpsTracker.latitude);                            //changed by manish
            longitude = String.valueOf(gpsTracker.longitude);                            // changed by manish

            AddTrainingPojo.addTrainingPojo.setGpsLoation(latitude + "lat"+"," + longitude+"long");
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);

        }




      /*  GPSTracker gpsTracker = new GPSTracker(getContext());

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            gpsTracker.getLocation();
             latitude = String.valueOf(gpsTracker.latitude);
             longitude = String.valueOf(gpsTracker.longitude);

            AddTrainingPojo.addTrainingPojo.setGpsLoation(latitude + "lat"+"," + longitude+"long");
            AppUtility.getInstance().showLog("location" + latitude + "  " + longitude, LoginActivity.class);

        } else {
            gpsTracker.showSettingsAlert();
        }*/
    }

    private void saveUserCreatedDataInLocalDB() {

        TrainingLocationInfo trainingLocationInfo = new TrainingLocationInfo();
        TrainingInfoData trainingInfoData = new TrainingInfoData();
        TrainingShgAndMemberData trainingShgAndMemberData = new TrainingShgAndMemberData();
        TrainingModuleInfo trainingModuleInfo = new TrainingModuleInfo();

        String trainingId = getUniqueTrainingId();

        String blockCode = AddTrainingPojo.addTrainingPojo.getBlockCode();
        String gpCode = AddTrainingPojo.addTrainingPojo.getGpCode();
        String villageCode = AddTrainingPojo.addTrainingPojo.getVillageCode();
        String trainingCreationDate = AddTrainingPojo.addTrainingPojo.getTrainingCreatedDate();
        String gpsLoation = AddTrainingPojo.addTrainingPojo.getGpsLoation();
        String members = AddTrainingPojo.addTrainingPojo.getSubTotal();
        String otherParticipant = AddTrainingPojo.addTrainingPojo.getOtherParticipant();
        String totalParticipant = AddTrainingPojo.addTrainingPojo.getTotalMembers();

        if (trainingId != null && !trainingId.equalsIgnoreCase(""))
            trainingLocationInfo.setTrainingId(trainingId);
        else {
            trainingId = getUniqueTrainingId();
            trainingLocationInfo.setTrainingId(trainingId);
        }

        if (blockCode != null && !blockCode.equalsIgnoreCase(""))
            trainingLocationInfo.setBlockId(blockCode);
        else trainingLocationInfo.setBlockId("");

        if (gpCode != null && !gpCode.equalsIgnoreCase(""))
            trainingLocationInfo.setGpId(gpCode);
        else trainingLocationInfo.setGpId("");

        if (villageCode != null && !villageCode.equalsIgnoreCase(""))
            trainingLocationInfo.setVillageId(villageCode);
        else trainingLocationInfo.setVillageId("");

        if (trainingCreationDate != null && !trainingCreationDate.equalsIgnoreCase(""))
            trainingLocationInfo.setDate(trainingCreationDate);
        else trainingLocationInfo.setDate("");

        if (members != null && !members.equalsIgnoreCase(""))
            trainingLocationInfo.setMemberParticipant(members);
        else trainingLocationInfo.setMemberParticipant("0");

        if (otherParticipant != null && !otherParticipant.equalsIgnoreCase(""))
            trainingLocationInfo.setOtherParticipant(otherParticipant);
        else trainingLocationInfo.setOtherParticipant("0");

        if (totalParticipant != null && !totalParticipant.equalsIgnoreCase(""))
            trainingLocationInfo.setTotalParticipant(totalParticipant);
        else trainingLocationInfo.setTotalParticipant("0");

        if (gpsLoation != null && !gpsLoation.equalsIgnoreCase(""))
            trainingLocationInfo.setGpsLocation(gpsLoation);
        else trainingLocationInfo.setGpsLocation("N,F");

        if (imageByteArray != null)
            trainingLocationInfo.setImage(imageByteArray);
        else {
            byte[] blank = "SamadKhan".getBytes();
            trainingLocationInfo.setImage(blank);
        }

        trainingLocationInfo.setSyncStatus("0");
        trainingLocationInfo.setSelectedShgCount(AddTrainingPojo.addTrainingPojo.getSelectedShgCount());

        SplashActivity.getInstance().getDaoSession().getTrainingLocationInfoDao().insert(trainingLocationInfo);

        List<AddTrainingPojo.SelectedShgList> selectedShgsList = AddTrainingPojo.addTrainingPojo.getSelectedShgList();
        for (int i = 0; i < selectedShgsList.size(); i++) {
            AddTrainingPojo.SelectedShgList selectedShg = selectedShgsList.get(i);
            String shgCode = selectedShg.getShgCode();

            if (shgCode != null && !shgCode.equalsIgnoreCase(""))
                trainingInfoData.setShgCode(shgCode);

            trainingInfoData.setTrainingId(trainingId);
            trainingInfoData.setSyncStatus("0");


            SplashActivity.getInstance().getDaoSession().getTrainingInfoDataDao().insert(trainingInfoData);

            List<AddTrainingPojo.SelectedShgList.ShgMemberList> shgMemberList = selectedShg.getShgMemberLists();
            for (int j = 0; j < shgMemberList.size(); j++) {
                AddTrainingPojo.SelectedShgList.ShgMemberList shgMember = shgMemberList.get(j);
                String shgMemberCode = shgMember.getMemberCode();

                if (shgCode != null && !shgCode.equalsIgnoreCase(""))
                    trainingShgAndMemberData.setShgCode(shgCode);
                else trainingShgAndMemberData.setShgCode("");

                if (shgMemberCode != null && !shgMemberCode.equalsIgnoreCase(""))
                    trainingShgAndMemberData.setShgMemberCode(shgMemberCode);
                else trainingShgAndMemberData.setShgMemberCode("");

                trainingShgAndMemberData.setTrainingId(trainingId);
                trainingShgAndMemberData.setSyncStatus("0");
                SplashActivity.getInstance().getDaoSession().getTrainingShgAndMemberDataDao().insert(trainingShgAndMemberData);
            }

            List<AddTrainingPojo.SelectedModulesList> selectedModulesList = AddTrainingPojo.addTrainingPojo.getSelectedModulesList();
            for (int k = 0; k < selectedModulesList.size(); k++) {
                AddTrainingPojo.SelectedModulesList selectedModule = selectedModulesList.get(k);

                trainingModuleInfo.setTrainingId(trainingId);
                trainingModuleInfo.setShgCode(shgCode);
                trainingModuleInfo.setModuleCode(selectedModule.getModulecode());
                trainingModuleInfo.setSyncStatus("0");

                SplashActivity.getInstance().getDaoSession().getTrainingModuleInfoDao().insert(trainingModuleInfo);
            }
        }
    }

    private String getUniqueTrainingId() {
        LoginActivity loginActivity = new LoginActivity();
        String trainingId = "";
        int increment = 0;
        String villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForAddTraining(), getContext());
        AppUtility.getInstance().showLog("value of village" + villageCode, AddShg.class);
        String userId = SplashActivity.getInstance().getDaoSession().getLoginInfoDao().queryBuilder().limit(1).unique().getLoginId();
        AppUtility.getInstance().showLog("userId" + userId, PhotoGps.class);
        try {
            JSONObject mPinFileObject = loginActivity.readMpinFile();
            increment = Integer.parseInt(mPinFileObject.getString("lastTrainingId")) + 1;
        } catch (JSONException je) {
            AppUtility.getInstance().showLog("mPinFileObjectExp" + je, PhotoGps.class);
        }

        if (villageCode != null && userId != null && String.valueOf(increment) != null) {
            trainingId = userId + villageCode + increment+","+ DateFactory.getInstance().getTodayDate()+" "+DateFactory.getInstance().getCurrentTime("hh:mm:ss");
            AppUtility.getInstance().showLog("trainingId"+trainingId,PhotoGps.class);
           // Toast.makeText(getContext(), "trainingId" + trainingId, Toast.LENGTH_SHORT).show();

            loginActivity.saveLoginDetailsInLocalFile(getContext(), loginActivity.getLoginIdFromLocal()
                    , PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyMpin()
                            , getContext()), String.valueOf(increment), loginActivity.getMobileNoFromLocal()
                    , loginActivity.getAppVersionFromLocal());
        }
        return trainingId;
    }

    public void saveDbDataInBackUpFile(Context context) {
        String trainingData="";
        SyncData syncData = SyncData.getInstance(context);
        syncData.initializeList();
        try {
          /*  String encryptData= AppUtility.getInstance().encrypt(trainingData);
            AppUtility.getInstance().showLog("encyptedData"+encryptData,PhotoGps.class);*/
            trainingData=new JSONObject().accumulate("addTrainingUnSyncedData", syncData.getAddTrainingJSONArray()).toString();
           // trainingData=new Cryptography().encrypt(trainingData);
            FileUtility.getInstance().createFileInMemory(FileManager.getInstance().getPathDetails(context), AppConstant.trainingFileName,trainingData);
            String fileName = AppConstant.trainingFileName;
            String filePath = FileManager.getInstance().getPathDetails(context);
            String absloutePath = FileManager.getInstance().getAbslutePathDetails(context, fileName);
            String fie = FileUtility.getInstance().read_file(absloutePath, filePath, fileName);
            AppUtility.getInstance().showLog("ReadFile" + fie, PhotoGps.class);

        } catch (JSONException je) {
            AppUtility.getInstance().showLog("je" + je, PhotoGps.class);
        } /*catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }*/
    }
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
           AppUtility.getInstance().showLog("Error creating media file, check storage permissions",PhotoGps.class);
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            AppUtility.getInstance().showLog("File not found: " + e.getMessage(),PhotoGps.class);
        } catch (IOException e) {
          AppUtility.getInstance().showLog("Error accessing file: " + e.getMessage(),PhotoGps.class);
        }
    }
    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Saksham/data/"
                + getContext().getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        filePath=mediaStorageDir.getPath() + File.separator + mImageName;
        mediaFile = new File(filePath);
        return mediaFile;
    }

    private Bitmap readFilefromInternal(String filePath){
        File imagefile = new File(filePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            AppUtility.getInstance().showLog("FileNotFoundException"+e,PhotoGps.class);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        return bitmap;
    }
}
