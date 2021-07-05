package com.shaksham.utils;

public class PrefrenceManager {
    private static final String PREF_KEY_SELECTED_SHGs = "selectedShg";
    private static final String PREF_KEY_LANGUAGE_CODE = "languageCode";
    private static final String PREF_KEY_SELECTED_SHG_ADDTRAINING = "selectedShgaddtr";
    private static final String PREF_KEY_VILLAGECODE_FOR_ADD_TRAINING = "villageCode";
    private static final String PREF_KEY_VILLAGECODE_FOR_EVALUATION = "villageCodeEvaluation";
    private static final String PREF_KEY_BLOCKCODE_FOR_EVALUATION = "blockCodeEvaluation";
    private static final String PREF_KEY_GPCODE_FOR_EVALUATION = "GPCodeEvaluation";
    private static final String PREF_KEY_SELECTED_BLOCK_CODE_FOR_BASLINE = "selectedBlockCode";
    private static final String PREF_KEY_SELECTED_VILLAGE_CODE_FOR_BASLINE = "selecteVillageCode";
    private static final String PREF_KEY_SELECTED_GP__CODE_FOR_BASLINE = "selectedGpCode";
    private static final String PREF_KEY_SELECTED_SHG_TOTAL_MEMBERS = "selectedShgTotalMemberCode";
    private static final String PREF_KEY_SELECTED_SHG_CODE_FOR_BASLINE = "selectedShgCodeBasline";
    private static final String PREF_KEY_SELECTED_SHG_ENTER_MEMBER_FOR_BASLINE = "enterBaslineMember";
    private static final String PREF_KEY_SELECTED_SHG_ENTER_MEMBER_FOR_EVALUTION="enterEvaluationMember";  //newly added for Evaluation member
    private static final String PREF_KEY_MPIN = "prfmpin";
    private static final String PREF_PIN_STATUS = "pinstatus";

    private static final String PREF_KEY_SUM_OF_SHG = "sumSHG";

    public static final String PRF_KEY_BASLINE_USER_SELECTED_DATE = "baslineUserSelectedDate";

    //Key Added by lincon for evealuation
    private static final String PREF_KEY_SHG_CODE_FOR_EVALUATION = "evaluationShgCode";
    private static final String PREF_KEY_TRAININGID_FOR_EVEALUATION = "evaluationTrainingId";

    /*Pref key added by Abdul*/
    public static final String PRF_KEY_OTP = "otp";
    public static final String PRF_KEY_LOGIN_ID_FROM_LOCAL = "loginId";
    public static final String PRF_KEY_MOBILE_NUMBER = "mobileNumber";
    public static final String PRF_KEY_LOGIN_SESSION_DATE = "loginSession";
    public static final String PRF_KEY_LOGIN_SESSION_STATUS = "loginSessionStatus";
    public static final String PRF_KEY_LOGIN_SESSION_DATE_COUNT = "loginSessionDateCount";
    public static final String PRF_KEY_BASELINE_SYNC = "baselineSync";
    public static final String PRF_KEY_ADD_TRAINNING_SYNC = "addTrainningSync";
    public static final String PRF_KEY_EVALUATION_SYNC = "evaluationSync";
    public static final String PREF_KEY_PASSWORD = "passwrd";
    public static final String PREF_KEY_TOTAL_TRAINING = "totaTraining";

    //*********crested by lincon bhalla*********************
    public static final String PRF_KEY_TOTAL_MEMBER_FOR_EVALUATION = "evaluationTotalMemberCount";
    public static final String PRF_KEY_LANGUAGE_ID = "languageid";
    private static final String PREF_KEY_LOGOUT_CORDINATES = "logOutCordinates";
    private static final String PREF_KEY_LOGOUT_TIME_STAMP = "logOutTimeStamp";
    private static final String PREF_KEY_DEVICE_IMEI = "deviceIMEI";
    private static final String PREF_KEY_DEVICE_INFO = "deviceINFO";
    private static final String LOGIN_FAILD_COUNT="loginFaildCount";
    private static final String LAST_ATTEMT_TIME="lastattempttime";

    public static String getPrefKeySelectedShgEnterMemberForEvalution() {
        return PREF_KEY_SELECTED_SHG_ENTER_MEMBER_FOR_EVALUTION;
    }


    public static String getLoginFaildCount() {
        return LOGIN_FAILD_COUNT;
    }

    public static String getLastAttemtTime() {
        return LAST_ATTEMT_TIME;
    }

    public static String getPrefKeyDeviceImei() {
        return PREF_KEY_DEVICE_IMEI;
    }

    public static String getPrefKeyDeviceInfo() {
        return PREF_KEY_DEVICE_INFO;
    }

    public static String getPrefKeyLogoutCordinates() {
        return PREF_KEY_LOGOUT_CORDINATES;
    }

    public static String getPrefKeyLogoutTimeStamp() {
        return PREF_KEY_LOGOUT_TIME_STAMP;
    }

    public static String getPrefPinStatus() {
        return PREF_PIN_STATUS;
    }

    public static String getPrefKeyVillagecodeForEvaluation() {
        return PREF_KEY_VILLAGECODE_FOR_EVALUATION;
    }

    public static String getPrefKeyMpin() {
        return PREF_KEY_MPIN;
    }

    public static String getPrefKeyBlockcodeForEvaluation() {
        return PREF_KEY_BLOCKCODE_FOR_EVALUATION;
    }

    public static String getPrefKeyGpcodeForEvaluation() {
        return PREF_KEY_GPCODE_FOR_EVALUATION;
    }

    public static String getPrefKeyShgCodeForEvaluation() {
        return PREF_KEY_SHG_CODE_FOR_EVALUATION;
    }

    public static String getPrefKeyTrainingidForEvealuation() {
        return PREF_KEY_TRAININGID_FOR_EVEALUATION;
    }

    public static String getPrefKeyVillagecodeForAddTraining() {
        return PREF_KEY_VILLAGECODE_FOR_ADD_TRAINING;
    }

    public static String getPrefKeySelectedShgCodeForBasline() {
        return PREF_KEY_SELECTED_SHG_CODE_FOR_BASLINE;
    }

    public static String getPrefKeySelectedShgEnterMemberForBasline() {
        return PREF_KEY_SELECTED_SHG_ENTER_MEMBER_FOR_BASLINE;
    }

    public static String getPrefKeySelectedShgTotalMembers() {
        return PREF_KEY_SELECTED_SHG_TOTAL_MEMBERS;
    }

    public static String getPrefKeySelectedBlockCodeForBasline() {
        return PREF_KEY_SELECTED_BLOCK_CODE_FOR_BASLINE;
    }

    public static String getPrefKeySelectedVillageCodeForBasline() {
        return PREF_KEY_SELECTED_VILLAGE_CODE_FOR_BASLINE;
    }

    public static String getPrefKeySelectedGp_codeForBasline() {
        return PREF_KEY_SELECTED_GP__CODE_FOR_BASLINE;
    }


    public static String getPrefKeySumOfShg() {
        return PREF_KEY_SUM_OF_SHG;
    }

    public static String getPrefKeySelectedShgAddtraining() {
        return PREF_KEY_SELECTED_SHG_ADDTRAINING;
    }


    public static String getPrefKeyTotalTraining() {
        return PREF_KEY_TOTAL_TRAINING;
    }

    public static String getPrefKeyPassword() {
        return PREF_KEY_PASSWORD;
    }


    public static String getPrfKeyBaselineSync() {
        return PRF_KEY_BASELINE_SYNC;
    }

    public static String getPrfKeyAddTrainningSync() {
        return PRF_KEY_ADD_TRAINNING_SYNC;
    }

    public static String getPrfKeyEvaluationSync() {
        return PRF_KEY_EVALUATION_SYNC;
    }

    public static String getPrfKeyLoginSessionDateCount() {
        return PRF_KEY_LOGIN_SESSION_DATE_COUNT;
    }

    public static String getPrfKeyLoginIdFromLocal() {
        return PRF_KEY_LOGIN_ID_FROM_LOCAL;
    }

    public static String getPrfKeyLoginSessionStatus() {
        return PRF_KEY_LOGIN_SESSION_STATUS;
    }

    public static String getPrfKeyLoginSessionDate() {
        return PRF_KEY_LOGIN_SESSION_DATE;
    }

    public static String getPrfKeyMobileNumber() {
        return PRF_KEY_MOBILE_NUMBER;
    }

    public static String getPrfKeyOtp() {
        return PRF_KEY_OTP;
    }

    public static String getPrefKeyLanguageCode() {
        return PREF_KEY_LANGUAGE_CODE;
    }

    public static String getPREF_KEY_SELECTED_SHGs() {
        return PREF_KEY_SELECTED_SHGs;
    }

    public static String getPrfKeyBaslineUserSelectedDate() {
        return PRF_KEY_BASLINE_USER_SELECTED_DATE;
    }

    public static String getPrfKeyLanguageId() {
        return PRF_KEY_LANGUAGE_ID;
    }

    public static String getPrfKeyTotalMemberForEvaluation() {
        return PRF_KEY_TOTAL_MEMBER_FOR_EVALUATION;
    }

}
