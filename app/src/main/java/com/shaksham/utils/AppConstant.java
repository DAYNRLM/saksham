package com.shaksham.utils;

public class AppConstant {

    public static final String language_english[] = {"English", "Hindi","Bengali","Tamil","Gujarati","Khasi","Malayalam","Odia","Punjabi","Assamese","Mizo","Marathi"};
    public static final String local_language[] = {"English", "हिन्दी","বাঙালি","Tamil","ગુજરાતી","Khasi","മലയാളം","ଓଡ଼ିଆ","ਪੰਜਾਬੀ"," অসমীয়া","Mizo","Marathi"};
    public static final String language_id[] = {"0", "1","2", "3","4", "5","6", "7","8", "9","10","11"};
    public static final String language_code[] =  {"en", "hi","bn", "ta","gu", "kha","ml", "or","pa","as","mi","mr"};
    public static final String shg_member_Detail[] =  {"en", "hi","bn", "tm","gu", "ks","ml", "or","pa","as","mr"};
    public static final String baslineFileName = "baslineSyncBackup.json";
    public static final String evaluationFileName = "evaluationSyncBackup.json";
    public static final String trainingFileName = "trainingSyncBackup.json";
    public static final String mpinFileName = "mpinBackup.json";
    public static final String BASELINE_SYNC_KEY = "BLSK";
    public static final String ADDTRAINING_SYNC_KEY = "ATSK";
    public static final String EVALUATION_SYNC_KEY = "ESK";
    //public static final long BACKGROUND_TIME =1000*60*5 ;
    public static final long BACKGROUND_TIME =60000 ;
    public static final int MAX_LOGIN_ATTEMPTS =6 ;
    //600000
  //  public static final byte[] ENCRPT_DECRYPT_KEY = "95674019".getBytes();
    public static final byte[] ENCRPT_DECRYPT_KEY = "78965412".getBytes();
    public static final String BAD_NETWORK = "Info";
    public static final String NO_OF_EVALUATIONS = "3";



    // Local on Aanand System

/*     public static final String DEMO="Local";
    public static final String API_TYPE="nrlmwebservice";
    public static final String HTTP_TYPE="http";
    public static final String IP_ADDRESS="10.197.183.105:8080";*/

     //  Demo live server

     /* public static final String DEMO="demo";
      public static final String API_TYPE="nrlmwebservicedemo";
      public static final String HTTP_TYPE="https";
      public static final String IP_ADDRESS="nrlm.gov.in";*/

    //  Live Server

    public static final String DEMO="Live";
    public static final String HTTP_TYPE="https";
    public static final String IP_ADDRESS="nrlm.gov.in";
    public static final String API_TYPE="nrlmwebservice";

}
