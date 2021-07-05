package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.LanguagePojo;
import com.shaksham.model.database.LoginInfo;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.LanguageSelectionAdapter;
import com.shaksham.view.adaptors.SelectShgListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LanguageSelectionFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {

    @BindView(R.id.changLanguage_Rv)
    RecyclerView changeLanguageRv;


    List<LanguagePojo> languageDataList;
    LanguageSelectionAdapter languageSelectionAdapter;

    public static LanguageSelectionFragment newInstance() {
        LanguageSelectionFragment languageSelectionFragment = new LanguageSelectionFragment();
        return languageSelectionFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.language_selection_fragment;
    }

    @Override
    public void onFragmentReady() {

        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.menu_change_Language));
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),LanguageSelectionFragment.class);

        bindDate();
        showData();

    }

    private void bindDate() {

        languageDataList = new ArrayList<>();
        String index = "";

        LoginInfo loginInfo = SplashActivity.getInstance().getDaoSession().getLoginInfoDao().queryBuilder().build().unique();
        String languageId = loginInfo.getLanguageId();

        for(int i=0;i<AppConstant.language_id.length;i++){
            if(AppConstant.language_id[i].equalsIgnoreCase(languageId)){
                index = String.valueOf(i);
            }
        }
        String languageCode = AppConstant.language_code[Integer.parseInt(index)];
        String localLanguage = AppConstant.local_language[Integer.parseInt(index)];
        String language = AppConstant.language_english[Integer.parseInt(index)];
        String lanId = AppConstant.language_id[Integer.parseInt(index)];

        LanguagePojo englisgLang = new LanguagePojo();
        englisgLang.setLanguagecode(AppConstant.language_code[0]);
        englisgLang.setLocalLanguage(AppConstant.local_language[0]);
        englisgLang.setEnglishLanguage(AppConstant.language_english[0]);
        englisgLang.setLanguageid(AppConstant.language_id[0]);
        languageDataList.add(englisgLang);


        LanguagePojo localLangFromDb = new LanguagePojo();
        localLangFromDb.setLanguagecode(languageCode);
        localLangFromDb.setLocalLanguage(localLanguage);
        localLangFromDb.setEnglishLanguage(language);
        localLangFromDb.setLanguageid(lanId);
        languageDataList.add(localLangFromDb);







    }

    private void showData() {
        languageSelectionAdapter = new LanguageSelectionAdapter(getContext(), languageDataList);
        changeLanguageRv.setLayoutManager(new LinearLayoutManager(getContext()));
        changeLanguageRv.setAdapter(languageSelectionAdapter);
        languageSelectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        initializeAllList();
        bindLanguageData();
    }

    private void initializeAllList() {
        languageDataList = new ArrayList<>();
    }

    private void bindLanguageData() {
        for (int i = 0; i < AppConstant.language_code.length; i++) {
            LanguagePojo languagePojo = new LanguagePojo();
            languagePojo.setEnglishLanguage(AppConstant.language_english[i]);
            languagePojo.setLocalLanguage(AppConstant.local_language[i]);
            languagePojo.setLanguagecode(AppConstant.language_code[i]);
            languageDataList.add(languagePojo);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void doBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(),DashBoardFragment.newInstance(),DashBoardFragment.class.getSimpleName(),false,R.id.fragmentContainer);

    }
}
