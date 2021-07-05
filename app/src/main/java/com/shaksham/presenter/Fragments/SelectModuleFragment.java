package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.SelectModulePojo;
import com.shaksham.model.database.ModuleData;
import com.shaksham.model.database.ModuleDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.SelectModuleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SelectModuleFragment extends Fragment implements HomeActivity.OnBackPressedListener {

    @BindView(R.id.selectmd_back)
    Button btBack;

    @BindView(R.id.selectmd_next_btn)
    Button btNext;
    @BindView(R.id.select_module_rcv)
    RecyclerView rvSelectModule;
    private Unbinder unbinder;
    List<ModuleData> moduleInfo;
    List<ModuleData> moduleInfoSorted;
    public static SelectModuleFragment getInstance() {
        SelectModuleFragment selectModuleFragment = new SelectModuleFragment();
        return selectModuleFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),SelectModuleFragment.class);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_module_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        moduleInfoSorted=new ArrayList<>();
/*
        String langCode =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLanguageId(),getContext());;
        if(langCode.equalsIgnoreCase("")){
            langCode="0";
        }
        moduleInfo = SplashActivity.getInstance().getDaoSession().getModuleDataDao().queryBuilder().where(ModuleDataDao.Properties.LanguageId.eq(langCode)).build().list();
        AppUtility.getInstance().showLog("moduleInfo" + moduleInfo, SelectModuleFragment.class);*/
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String langCode =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLanguageId(),getContext());;
        if(langCode.equalsIgnoreCase("")){
            langCode="0";
        }
        moduleInfo = SplashActivity.getInstance().getDaoSession().getModuleDataDao().queryBuilder().where(ModuleDataDao.Properties.LanguageId.eq(langCode)).build().list();
           for(int i=1;i<=moduleInfo.size();i++) {
           for (int j = 0; j < moduleInfo.size(); j++) {
              int minfo= Integer.parseInt(moduleInfo.get(j).getModuleCode());
               if(i==minfo) {
                   ModuleData moduleData = moduleInfo.get(j);
                    moduleInfoSorted.add(moduleData);
               }else
               {
                   continue;

               }
           }
       }
       /* List<SelectModulePojo> selectModulePojos=new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            SelectModulePojo selectModulePojo = new SelectModulePojo();
            selectModulePojo.setModuleName((list.get(i)));
            selectModulePojo.setPosition("" + i);
            selectModulePojos.add(selectModulePojo);
        }*/
     /*   ArrayList<String> list=new ArrayList<>();
        list.add("Financial Planning");
        list.add("Savings");
        list.add("Credit");
        list.add("Insurance");
        list.add("Pension");
        list.add("BC Model");


              for(int i=0;i<list.size();i++) {
            SelectModulePojo selectModulePojo=new SelectModulePojo();
            selectModulePojo.setModuleName(list.get(i));
            selectModulePojo.setPosition(""+i);
            moduleInfo.add(selectModulePojo);
        }*/
        SelectModuleAdapter selectModuleAdapter = new SelectModuleAdapter(moduleInfoSorted, getContext(), btNext);
        rvSelectModule.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSelectModule.setAdapter(selectModuleAdapter);
        selectModuleAdapter.notifyDataSetChanged();
    }
    @OnClick(R.id.selectmd_back)
    void addLocationFr() {
        AddLocation addLocation = AddLocation.getInstance();

        AppUtility.getInstance().replaceFragment(getFragmentManager(), addLocation, AddLocation.class.getSimpleName(), false, R.id.fragmentContainer);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }
    @Override
    public void doBack() {
        AddLocation addLocation = AddLocation.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), addLocation, AddLocation.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
