package com.shaksham.presenter.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shaksham.R;
import com.shaksham.model.PojoData.GetQuestionValue;
import com.shaksham.model.database.BaselineSyncData;
import com.shaksham.model.database.BaslineQuestionSyncData;
import com.shaksham.model.database.QuestionInfoDetail;
import com.shaksham.model.database.QuestionInfoDetailDao;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.model.database.TitleInfoDetail;
import com.shaksham.model.database.TitleInfoDetailDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.Cryptography;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.FileManager;
import com.shaksham.utils.FileUtility;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SyncData;
import com.shaksham.view.adaptors.BaslineQuestionAdapter;
import com.shaksham.view.adaptors.BaslineSelectShgAdaptor;
import com.shaksham.view.adaptors.ShgMemberListAdapter;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONObject;

import java.sql.Date;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaselineFragment extends Fragment implements HomeActivity.OnBackPressedListener {

    @BindView(R.id.button_save)
    Button btSave;

    @BindView(R.id.evaluation_formTitleTv)
    TextView titleTv;

    @BindView(R.id.evaluation_QuestionRv)
    RecyclerView enteredQuestionRV;

    @BindView(R.id.evform_totalMemberTv)
    TextView totalMemberTv;

    @BindView(R.id.evForm_enteredMemberTv)
    TextView entredMemberTv;
    @BindView(R.id.evForm_ShgNameTv)
    TextView shgnameTv;

    List<TitleInfoDetail> titleInfoDetails;
    List<QuestionInfoDetail> questionInfoDetails;
    BaslineQuestionAdapter baslineQuestionAdapter;
    String questionId, answervalue, shgcode;
    Context context;

    public static BaselineFragment getInstance() {
        BaselineFragment baselineFragment = new BaselineFragment();
        return baselineFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        initilazer();
        AppUtility.getInstance().showLog("loginStatus" + PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()), BaselineFragment.class);
        context = getActivity().getApplicationContext();


        String blockCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedBlockCodeForBasline(), context);
        String gpCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedGp_codeForBasline(), context);
        String villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(), context);

    }

    private void initilazer() {
        titleInfoDetails = new ArrayList<>();
        questionInfoDetails = new ArrayList<>();
    }

    private void showData() {
        baslineQuestionAdapter = new BaslineQuestionAdapter(questionInfoDetails, getContext());
      /*  enteredQuestionRV.scrollToPosition(1);
        enteredQuestionRV.smoothScrollToPosition(1);*/
        enteredQuestionRV.setLayoutManager(new LinearLayoutManager(getContext()));
        enteredQuestionRV.setAdapter(baslineQuestionAdapter);
        baslineQuestionAdapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.evaluation_form, container, false);
        ButterKnife.bind(this, view);

        String blockCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedBlockCodeForBasline(), getContext());
        String gpCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedGp_codeForBasline(), getContext());
        String villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(), getContext());

        String shgCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgCodeForBasline(), getContext());
        String entredMember = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgEnterMemberForBasline(), getContext());
        ShgMemberListAdapter shgMemberListAdapter = new ShgMemberListAdapter();
        BaslineSelectShgAdaptor baslineSelectShgAdaptor = new BaslineSelectShgAdaptor();
        titleTv.setText(getString(R.string.baseline_form_title));
        shgnameTv.setText(shgMemberListAdapter.getShgNameFromLocal(shgCode));
        totalMemberTv.setText(String.valueOf(baslineSelectShgAdaptor.getShgMemberCountFromDb(shgCode)));
        entredMemberTv.setText(entredMember);
        bindData();
        showData();
        return view;
    }

    private void bindData() {
        String langCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLanguageId(), getContext());
        if (langCode.equalsIgnoreCase("")) {
            langCode = "0";
        }

        titleInfoDetails = SplashActivity.getInstance().getDaoSession().getTitleInfoDetailDao()
                .queryBuilder().where(TitleInfoDetailDao.Properties.LanguageId.eq(langCode))
                .build().list();
        Collections.sort(titleInfoDetails, new Comparator<TitleInfoDetail>() {
            @Override
            public int compare(TitleInfoDetail o1, TitleInfoDetail o2) {
                return new Long(o1.getTitleId()).compareTo(new Long(o2.getTitleId()));
            }
        });

        for (int i = 0; i < titleInfoDetails.size(); i++) {
            List<QuestionInfoDetail> questionInfoDetailList = SplashActivity.getInstance()
                    .getDaoSession().getQuestionInfoDetailDao()
                    .queryBuilder().where(QuestionInfoDetailDao.Properties.LanguageId.eq(langCode)
                            , QuestionInfoDetailDao.Properties.TitleId.eq(titleInfoDetails.get(i).getTitleId()))
                    .build()
                    .list();
            AppUtility.getInstance().showLog("data" + questionInfoDetailList, BaselineFragment.class);
            Collections.sort(questionInfoDetailList, new Comparator<QuestionInfoDetail>() {
                @Override
                public int compare(QuestionInfoDetail o1, QuestionInfoDetail o2) {
                    return new Integer(o1.getQuestionId().compareTo(o2.getQuestionId()));
                }
            });
            questionInfoDetails.addAll(questionInfoDetailList);
        }
        //questionInfoDetails = SplashActivity.getInstance().getDaoSession().getQuestionInfoDetailDao().queryBuilder().where(QuestionInfoDetailDao.Properties.LanguageId.eq(langCode)).build().list();

        for (int i = 0; i < titleInfoDetails.size(); i++) {
            List<QuestionInfoDetail> questionInfoDetails = titleInfoDetails.get(i).getQuestionDataList();
            Gson gson = new Gson();
            String arrayData = gson.toJson(questionInfoDetails);
            AppUtility.getInstance().showLog("data is" + arrayData, EvaluationFormFragment.class);
        }
        Gson gson = new Gson();
        String arrayData = gson.toJson(titleInfoDetails);
        AppUtility.getInstance().showLog("data is" + arrayData, EvaluationFormFragment.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick(R.id.button_save)
    void dashboardFragment() {

        //add condition for filling baseline
        String answerValue = "";
        List<GetQuestionValue> getQuestionValues = baslineQuestionAdapter.getmDataset();
        for (GetQuestionValue getQuestionValue : getQuestionValues) {
            answerValue = getQuestionValue.getValue();
            if (answerValue.equalsIgnoreCase("")) {
                break;
            }
            AppUtility.getInstance().showLog("insideLoop:::" + getQuestionValue.getValue(), BaselineFragment.class);
        }
        if (answerValue.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), getContext().getString(R.string.toast_enter_question), Toast.LENGTH_SHORT).show();
        } else {
            DialogFactory.getInstance().showAlertDialog(getContext(), R.drawable.ic_launcher_background, getString(R.string.app_name), getString(R.string.dialog_save_data), "YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveInLocalDb();
                    if (NetworkFactory.isInternetOn(getContext())) {
                        ProgressDialog progressDialog = DialogFactory.getInstance().showProgressDialog(getContext(), false);
                        progressDialog.show();
                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyBaselineSync(), AppConstant.BASELINE_SYNC_KEY, getContext());
                      //  SyncData.getInstance(getContext().getApplicationContext()).syncData();
                        SyncData.getInstance(getContext()).syncData();
                        android.os.Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                saveDbDataIntoBackupFile(getContext().getApplicationContext());
                                Toast.makeText(getContext(), getContext().getString(R.string.toast_data_saved), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                AppUtility.getInstance().replaceFragment(getFragmentManager(), BaselineSelectShg.getInstance(), BaselineSelectShg.class.getSimpleName(), false, R.id.fragmentContainer);
                            }
                        }, 3000);

                    } else {
                        saveDbDataIntoBackupFile(getContext().getApplicationContext());
                        Toast.makeText(getContext(), getContext().getString(R.string.toast_data_saved), Toast.LENGTH_SHORT).show();
                        AppUtility.getInstance().replaceFragment(getFragmentManager(), BaselineSelectShg.getInstance(), BaselineSelectShg.class.getSimpleName(), false, R.id.fragmentContainer);
                    }

                }
            }, "NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, false);

        }
    }

    private void saveInLocalDb() {
        String enteredMemberValue = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgEnterMemberForBasline(), getContext());
        //get shg code from selected shg;
        String selectedShgCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgCodeForBasline(), getContext());
        BaselineSyncData baselineSyncData = new BaselineSyncData();
        baselineSyncData.setShgCode(selectedShgCode);
        baselineSyncData.setVillageCode(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(), getContext()));
        if (enteredMemberValue.equalsIgnoreCase(""))
            enteredMemberValue = "0";
        baselineSyncData.setEnterMemberValue(enteredMemberValue);
        baselineSyncData.setBasLineStatus("0");
        baselineSyncData.setTodayDate(DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));
        baselineSyncData.setUserSelectedDate(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyBaslineUserSelectedDate(), getContext()));
        SplashActivity.getInstance().getDaoSession().getBaselineSyncDataDao().insert(baselineSyncData);

        //get question and answer details
        List<GetQuestionValue> getQuestionValues = baslineQuestionAdapter.getmDataset();

        for (int i = 0; i < getQuestionValues.size(); i++) {
            BaslineQuestionSyncData baslineQuestionSyncData = new BaslineQuestionSyncData();
            String questionId = getQuestionValues.get(i).getQuestionMainId();
            String value = getQuestionValues.get(i).getValue();
            baslineQuestionSyncData.setShgCode(selectedShgCode);
            baslineQuestionSyncData.setQuestionId(questionId);
            baslineQuestionSyncData.setAnswerForQuestion(value);
            baslineQuestionSyncData.setBaslineStatus("0");
            SplashActivity.getInstance().getDaoSession().getBaslineQuestionSyncDataDao().insert(baslineQuestionSyncData);
        }
        //update basline status in main table .....
        ShgData shgData = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder().where(ShgDataDao.Properties.ShgCode.eq(selectedShgCode)).limit(1).unique();
        shgData.setBaselineStatus("1");
        SplashActivity.getInstance().getDaoSession().getShgDataDao().update(shgData);
        // clearAllBaslinePreference();
        AppUtility.getInstance().showLog("shgData" + shgData.getBaselineStatus(), BaselineFragment.class);
    }

    public void clearAllBaslinePreference() {
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgCodeForBasline(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgEnterMemberForBasline(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgTotalMembers(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedBlockCodeForBasline(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedGp_codeForBasline(), getContext());
        PrefrenceFactory.getInstance().removeSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(), getContext());

    }

    public void saveDbDataIntoBackupFile(Context context) {
        String getJson = "";
        getJson = getBaslineJsonForFile(context);
        try {
            getJson = new Cryptography().encrypt(getJson);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        FileUtility.getInstance().createFileInMemory(FileManager.getInstance().getPathDetails(context), AppConstant.baslineFileName, getJson);
    }

    private String getBaslineJsonForFile(Context context) {
        SyncData.getInstance(context.getApplicationContext()).initializeList();
        JSONObject baseLineSyncDataObject = SyncData.getInstance(getContext()).baselineSyncDataObject();
        return baseLineSyncDataObject.toString();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void doBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(), BaselineSelectShg.getInstance(), BaselineSelectShg.class.getSimpleName(), false, R.id.fragmentContainer);
    }

}
