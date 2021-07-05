package com.shaksham.utils;

import android.content.Context;

public class FileManager {
    private static final String absoultePath = "/storage/emulated/0/";
    public static FileManager fileManager = null;

    public static FileManager getInstance() {
        if (fileManager == null)
            fileManager = new FileManager();
        return fileManager;
    }

    //**********************get file path for for creatjing file *****************************************
    public static String uniqueFilePath(String loginId, String mobileNumber) {
        String path = "Saksham" + "/Sksm_" + loginId + "/Sksm_" + mobileNumber + "/Sksm_file";
        return path;
    }


    //**************find absolute path for reading file ***************************************************
    public String AbsoultePathForUniqueFile(String uniqueFilePath, String uniqueFileName) {
        String absoultPth = absoultePath + uniqueFilePath + "/" + uniqueFileName;
        return absoultPth;
    }

    public String AbsoultePathForMpinFile(String uniqueFilePath, String uniqueFileName) {
        String absoultPth = absoultePath + uniqueFilePath + "/" + uniqueFileName;
        return absoultPth;
    }

    //******************get filr pathe for create and save dalta in local file******************************
    public String getPathDetails(Context context) {
        String mobileNumber = null, loginId = null;
        String path = "";
        loginId = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(), context);
        mobileNumber = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyMobileNumber(), context);
        try {
            path = uniqueFilePath(loginId, mobileNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public String getMpin() {
        String path = "";
        try {
            path = "Saksham";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    //*************for getting the absolute path for retrive file data from ;local save memory ***************
    public String getAbslutePathDetails(Context context, String fileName) {
        String uniqueFilePath = getPathDetails(context);
        String finalAbsolutePath = AbsoultePathForUniqueFile(uniqueFilePath, fileName);
        return finalAbsolutePath;
    }

    public String getAbsluteMpinPathDetails(Context context, String fileName) {
        String uniqueFilePath = getMpin();
        String finalAbsolutePath = AbsoultePathForMpinFile(uniqueFilePath, fileName);
        return finalAbsolutePath;
    }

}
