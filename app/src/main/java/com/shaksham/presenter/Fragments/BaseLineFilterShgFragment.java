package com.shaksham.presenter.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.shaksham.R;
import com.shaksham.model.PojoData.GetQuestionValue;
import com.shaksham.model.database.BlockData;
import com.shaksham.model.database.BlockDataDao;
import com.shaksham.model.database.GpData;
import com.shaksham.model.database.GpDataDao;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.model.database.VillageData;
import com.shaksham.model.database.VillageDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DatePicker;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.NetworkFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.utils.SyncData;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseLineFilterShgFragment extends Fragment implements HomeActivity.OnBackPressedListener {

    @BindView(R.id.back_of_addlocation)
    Button btnBack;

    @BindView(R.id.addlocfr_next_btn)
    Button btnNext;

    @BindView(R.id.spinner_selectblock)
    MaterialBetterSpinner selectBlockSpinner;

    @BindView(R.id.spinner_select_gp)
    MaterialBetterSpinner selectGpSpinner;

    @BindView(R.id.spinner_select_village)
    MaterialBetterSpinner selectVillageSpinner;

    @BindView(R.id.date_TextView)
    TextView dateSelectionTv;

    @BindView(R.id.text_date_of_training_addlocation)
    TextView dateOfBaseline;

    @BindView(R.id.baslineStatusRelativeLayout)
    RelativeLayout baslineRelativeLayout;

    @BindView(R.id.baslineShgErrorTv)
    TextView baslineErrorTV;

    @BindView(R.id.baslineShgDetilsLinearLayout)
    LinearLayout baslineShgDetailsLinearLayout;

    @BindView(R.id.shgLocationTv)
    TextView shgLocationTv;

    @BindView(R.id.basline_totalShgTv)
    TextView baslineTotalShg;

    @BindView(R.id.basline_doneShgTv)
    TextView baslineCompletedShg;

    @BindView(R.id.basline_pendingShgTV)
    TextView baslineUnCompletShg;


    int totalShg, baslineDoneShg, baslinePandingShg;
    String userSelectedDate;


    //list for vilage block gp
    private List<BlockData> blockListDataItemList;
    private List<GpData> gpListDataItemList;
    private List<VillageData> villageListDataItemList;

    //string array for set on adapter block village gp
    private String[] blockNameList;
    private String[] gramPanchytNameList;
    private String[] villageNameList;

    //get block gp and volage code click on item on spinner
    private String blockCode = "";
    private String gpCode = "";
    private String villageCode = "";
    private String blockNameF = "";
    private String gpNameF = "";
    private String villageNameF = "";

    //array adapter set on spinner for gp, block and village
    ArrayAdapter<String> blockListDataItemArrayAdapter;
    ArrayAdapter<String> gramPanchyatListDataItemArrayAdapter;
    ArrayAdapter<String> villageListDataItemArrayAdapter;

    public static BaseLineFilterShgFragment getInstance() {
        return new BaseLineFilterShgFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.title_baseline));
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),BaseLineFilterShgFragment.class);

        initializeList();
    }

    private void initializeList() {
        blockListDataItemList = new ArrayList<>();
        gpListDataItemList = new ArrayList<>();
        villageListDataItemList = new ArrayList<>();
    }



    @OnClick(R.id.text_date_of_training_addlocation)
    void getDateFragment() {
        DialogFragment dateFragment = new DatePicker(dateOfBaseline);
        dateFragment.show(getFragmentManager(), "date picker");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addlocation, container, false);
        ButterKnife.bind(this, view);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),BaseLineFilterShgFragment.class);
        selectBlockSpinner.setFocusableInTouchMode(false);
        selectGpSpinner.setFocusableInTouchMode(false);
        selectVillageSpinner.setFocusableInTouchMode(false);
        dateSelectionTv.setText(getContext().getString(R.string.baslineDate));
        dateOfBaseline.setText(DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));
        blockCode =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedBlockCodeForBasline(), getContext());
        gpCode =  PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedGp_codeForBasline(), getContext());
        villageCode =  PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(), getContext());
        if(villageCode.equalsIgnoreCase("")){

        }else {
            Toast.makeText(getContext(),getContext().getString(R.string.toast_location_msg),Toast.LENGTH_SHORT).show();
            getName();
            showLayout();
        }
        bindBlockData();
        setListner();
        return view;
    }

    private void getName() {
        blockNameF = SplashActivity.getInstance().getDaoSession().getBlockDataDao().queryBuilder().where(BlockDataDao.Properties.BlockCode.eq(blockCode)).limit(1).unique().getBlockName();
        gpNameF = SplashActivity.getInstance().getDaoSession().getGpDataDao().queryBuilder().where(GpDataDao.Properties.GpCode.eq(gpCode)).limit(1).unique().getGpName();
        villageNameF = SplashActivity.getInstance().getDaoSession().getVillageDataDao().queryBuilder().where(VillageDataDao.Properties.VillageCode.eq(villageCode)).limit(1).unique().getGetVillageName();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick(R.id.addlocfr_next_btn)
    void selectShgFrg() {
        if (villageCode.equalsIgnoreCase("") || villageCode == null) {
            Toast.makeText(getContext(), getContext().getString(R.string.toast_select_village_msg), Toast.LENGTH_SHORT).show();
        } else {
            if (totalShg > 0) {
                userSelectedDate  =dateOfBaseline.getText().toString().trim();
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyBaslineUserSelectedDate(),userSelectedDate,getContext());
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedBlockCodeForBasline(), blockCode, getContext());
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedGp_codeForBasline(), gpCode, getContext());
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(), villageCode, getContext());
                BaselineSelectShg baselineSelectShg = BaselineSelectShg.getInstance();
                if (baslinePandingShg==0){
                    Toast.makeText(getContext(),getString(R.string.no_pending_shgB),Toast.LENGTH_LONG).show();
                }else
                AppUtility.getInstance().replaceFragment(getFragmentManager(), baselineSelectShg, BaselineSelectShg.class.getSimpleName(), true, R.id.fragmentContainer);
            } else {
                Toast.makeText(getContext(), getContext().getString(R.string.toast_erros_shg_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.back_of_addlocation)
    void dashboardFragment() {
        DashBoardFragment dashBoardFragment = DashBoardFragment.newInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), dashBoardFragment, DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void doBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }

    private void bindBlockData() {
        blockListDataItemList = SplashActivity.getInstance().getDaoSession().getBlockDataDao().queryBuilder().build().list();
        blockNameList = new String[blockListDataItemList.size()];
        for (int i = 0; i < blockNameList.length; i++) {
            blockNameList[i] = blockListDataItemList.get(i).getBlockName();
        }
        blockListDataItemArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, blockNameList);
        selectBlockSpinner.setAdapter(blockListDataItemArrayAdapter);
    }

    private void setListner() {
        selectBlockSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearFocus(1);
                String blockName = selectBlockSpinner.getText().toString();
                blockNameF = blockName;
                blockCode = getBlockCode(blockName);
                fillGramPanchyatList();
                bindGramPanchyatData();
            }
        });

        selectGpSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearFocus(2);
                String gramPanchyt = selectGpSpinner.getText().toString();
                gpNameF = gramPanchyt;
                gpCode = getGpCode(gramPanchyt);
                fillVillageList();
                bindVillageData();
            }
        });

        selectVillageSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  clearFocus(3);
                String vilage = selectVillageSpinner.getText().toString();
                villageNameF = vilage;
                villageCode = getVillageCode(vilage);
                showLayout();
                compareDate();
            }
        });
    }

    private void showLayout() {
        baslineRelativeLayout.setVisibility(View.VISIBLE);
        shgLocationTv.setText(blockNameF + ", " + gpNameF + ", " + villageNameF);
        List<ShgData> shgData = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder().where(ShgDataDao.Properties.VillageCode.eq(villageCode)).build().list();
        totalShg = shgData.size();

        //************remove element during iteration***************
        Iterator<ShgData> itr = shgData.iterator();
        while (itr.hasNext()) {
            ShgData loan = itr.next();
            String s = loan.getBaselineStatus();
            if (s.equals("1")) {
                itr.remove();
            }
        }
        baslinePandingShg = shgData.size();
        baslineDoneShg = totalShg - baslinePandingShg;

        if (totalShg > 0) {
            baslineErrorTV.setVisibility(View.GONE);
            baslineShgDetailsLinearLayout.setVisibility(View.VISIBLE);
            baslineTotalShg.setText("" + totalShg);
            baslineCompletedShg.setText("" + baslineDoneShg);
            baslineUnCompletShg.setText("" + baslinePandingShg);

            AppUtility.getInstance().showLog("totalshg--" + totalShg, BaseLineFilterShgFragment.class);
            AppUtility.getInstance().showLog("baslinePending--" + baslinePandingShg, BaseLineFilterShgFragment.class);
            AppUtility.getInstance().showLog("baslineDone--" + baslineDoneShg, BaseLineFilterShgFragment.class);
        } else {
            baslineShgDetailsLinearLayout.setVisibility(View.GONE);
            baslineErrorTV.setVisibility(View.VISIBLE);
            AppUtility.getInstance().showLog("DATA NOT FOUND" + totalShg, BaseLineFilterShgFragment.class);
        }


    }

    private void compareDate() {
        Date date1 = null, date2 = null;
        String givendate = "20-01-2020";
        String todayDate = DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date1 = sdf.parse(givendate);
            sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = sdf.parse(todayDate);
            sdf.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date2.compareTo(date1) >= 0) {
            AppUtility.getInstance().showLog("dateIsLonger", BaseLineFilterShgFragment.class);
        } else {
            AppUtility.getInstance().showLog("dateIsShorter", BaseLineFilterShgFragment.class);
        }

    }

    private String getVillageCode(String vilage) {
        String villageCode = "";
        for (int i = 0; i < villageListDataItemList.size(); i++) {
            if (vilage.equalsIgnoreCase(villageListDataItemList.get(i).getGetVillageName())) {
                villageCode = (villageListDataItemList.get(i).getVillageCode());
            }
        }
        return villageCode;
    }

    private String getBlockCode(String stateName) {
        String blockCode = "";
        for (int i = 0; i < blockListDataItemList.size(); i++) {
            if (stateName.equalsIgnoreCase(blockListDataItemList.get(i).getBlockName())) {
                blockCode = ((blockListDataItemList.get(i).getBlockCode()));
            }
        }
        return blockCode;
    }

    private void fillGramPanchyatList() {
        gpListDataItemList = SplashActivity.getInstance().getDaoSession()
                .getGpDataDao().queryBuilder().where(GpDataDao.Properties.BlockCode.eq(blockCode))
                .build().list();
    }

    private void bindGramPanchyatData() {
        gramPanchytNameList = new String[gpListDataItemList.size()];
        for (int i = 0; i < gramPanchytNameList.length; i++) {
            gramPanchytNameList[i] = gpListDataItemList.get(i).getGpName();
        }
        gramPanchyatListDataItemArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, gramPanchytNameList);
        selectGpSpinner.setAdapter(gramPanchyatListDataItemArrayAdapter);
    }

    private void clearFocus(int i) {
        switch (i) {
            case 1:
                gpCode = "";
                villageCode = "";
                gpNameF = "";
                villageNameF = "";

                selectGpSpinner.setFocusableInTouchMode(false);
                selectVillageSpinner.setFocusableInTouchMode(false);

                gramPanchytNameList = new String[0];
                villageNameList = new String[0];

                gpListDataItemList.clear();
                villageListDataItemList.clear();

                selectGpSpinner.setText("");
                selectVillageSpinner.setText("");

                ArrayAdapter<String> gpListDataItemArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, gramPanchytNameList);
                selectGpSpinner.setAdapter(gpListDataItemArrayAdapter1);

                ArrayAdapter<String> villageListDataItemArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, villageNameList);
                selectVillageSpinner.setAdapter(villageListDataItemArrayAdapter1);

                //*****details layout visibility gone********
                baslineErrorTV.setVisibility(View.GONE);
                baslineShgDetailsLinearLayout.setVisibility(View.GONE);
                baslineRelativeLayout.setVisibility(View.GONE);


                break;
            case 2:
                villageCode = "";
                villageNameF = "";

                selectVillageSpinner.setFocusableInTouchMode(false);

                villageNameList = new String[0];

                villageListDataItemList.clear();

                selectVillageSpinner.setText("");

                ArrayAdapter<String> villageListDataItemArrayAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, villageNameList);
                selectVillageSpinner.setAdapter(villageListDataItemArrayAdapter2);

                //*****details layout visibility gone********
                baslineErrorTV.setVisibility(View.GONE);
                baslineShgDetailsLinearLayout.setVisibility(View.GONE);
                baslineRelativeLayout.setVisibility(View.GONE);
                break;
            case 3:
                blockCode = "";
                gpCode = "";
                villageCode = "";
                blockNameF = "";
                gpNameF = "";
                villageNameF = "";

                selectBlockSpinner.setFocusableInTouchMode(false);
                selectGpSpinner.setFocusableInTouchMode(false);
                selectVillageSpinner.setFocusableInTouchMode(false);

                blockNameList = new String[0];
                gramPanchytNameList = new String[0];
                villageNameList = new String[0];

                blockListDataItemList.clear();
                gpListDataItemList.clear();
                villageListDataItemList.clear();

                selectBlockSpinner.setText("");
                selectGpSpinner.setText("");
                selectVillageSpinner.setText("");

                ArrayAdapter<String> blockListDataItemArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, blockNameList);
                selectGpSpinner.setAdapter(blockListDataItemArrayAdapter1);

                ArrayAdapter<String> gpListDataItemArrayAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, gramPanchytNameList);
                selectGpSpinner.setAdapter(gpListDataItemArrayAdapter2);

                ArrayAdapter<String> villageListDataItemArrayAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, villageNameList);
                selectVillageSpinner.setAdapter(villageListDataItemArrayAdapter3);


                break;
        }
    }

    private String getGpCode(String gramPanchyt) {
        String gpCode = "";
        for (int i = 0; i < gpListDataItemList.size(); i++) {
            if (gramPanchyt.equalsIgnoreCase(gpListDataItemList.get(i).getGpName())) {
                gpCode = (gpListDataItemList.get(i).getGpCode());
            }
        }
        return gpCode;
    }

    private void bindVillageData() {
        villageNameList = new String[villageListDataItemList.size()];
        for (int i = 0; i < villageNameList.length; i++) {
            villageNameList[i] = villageListDataItemList.get(i).getGetVillageName();
        }
        villageListDataItemArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, villageNameList);
        selectVillageSpinner.setAdapter(villageListDataItemArrayAdapter);
    }

    private void fillVillageList() {
        //bind for village
        villageListDataItemList = SplashActivity.getInstance().getDaoSession()
                .getVillageDataDao().queryBuilder().where(VillageDataDao.Properties.GpCode.eq(gpCode))
                .build().list();
    }

}
