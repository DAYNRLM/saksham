package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.SelectEvaluationShgPojo;
import com.shaksham.model.database.AddedTrainingsData;
import com.shaksham.model.database.BlockData;
import com.shaksham.model.database.BlockDataDao;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterShgDataDao;
import com.shaksham.model.database.GpData;
import com.shaksham.model.database.GpDataDao;
import com.shaksham.model.database.VillageData;
import com.shaksham.model.database.VillageDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DatePicker;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.SelectShgListAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluationFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {

    @BindView(R.id.evealuation_shgSelectRecyclerView)
    RecyclerView evaluationShgRv;

    @BindView(R.id.evaluation_dateSelectedTv)
    TextView dateSelectedtv;

    @BindView(R.id.evaluation_selectBlockSpinner)
    MaterialBetterSpinner selectBlockSpinner;

    @BindView(R.id.evaluation_selectGpSpinner)
    MaterialBetterSpinner selectGpSpinner;

    @BindView(R.id.evaluation_SelectVillageSpinner)
    MaterialBetterSpinner selectVillageSpinner;

    @BindView(R.id.evaluationTrainingRelativeLayout)
    RelativeLayout trainingErrorRelativelayout;

    @BindView(R.id.evaluation_trainingListLL)
    LinearLayout trainingListLinearLayout;

    @BindView(R.id.shgEvLocationTv)
    TextView shgErrorviewTv;

    @BindView(R.id.listTrainingTv)
    TextView listTrainingTv;

    @BindView(R.id.shgEvaluation_nextBtn)
    Button nextBtn;

    List<SelectEvaluationShgPojo> finalSelectionShgDataList;
    private SelectShgListAdapter selectShgListAdapter;
    List<AddedTrainingsData> trainingEvaluationDataItemList;
    List<EvaluationMasterShgData> evaluationMasterShgData;


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
    private String blockNameF= "";
    private String gpNameF = "";
    private String villageNameF = "";

    public static boolean evaluationStartStatus=false;



    //array adapter set on spinner for gp, block and village
    ArrayAdapter<String> blockListDataItemArrayAdapter;
    ArrayAdapter<String> gramPanchyatListDataItemArrayAdapter;
    ArrayAdapter<String> villageListDataItemArrayAdapter;

    public static EvaluationFragment newInstance() {
        EvaluationFragment evaluationFragment = new EvaluationFragment();
        return evaluationFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.evaluation_shg_list_fragment;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.module_Evaluation));
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),EvaluationFragment.class);


        villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(),getContext());
        gpCode =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyGpcodeForEvaluation(),getContext());
        blockCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyBlockcodeForEvaluation(),getContext());
        if(!villageCode.equalsIgnoreCase("")){
            Toast.makeText(getContext(),getContext().getString(R.string.toast_location_msg),Toast.LENGTH_SHORT).show();
            getName();
            showData();
        }
        setTodayDate();
        selectBlockSpinner.setFocusableInTouchMode(false);
        selectGpSpinner.setFocusableInTouchMode(false);
        selectVillageSpinner.setFocusableInTouchMode(false);
        bindBlockData();
        setListner();
    }

    private void getName() {
        SplashActivity.getInstance().getDaoSession().getBlockDataDao().detachAll();
        SplashActivity.getInstance().getDaoSession().getGpDataDao().detachAll();
        SplashActivity.getInstance().getDaoSession().getVillageDataDao().detachAll();

        blockNameF = SplashActivity.getInstance().getDaoSession().getBlockDataDao().queryBuilder().where(BlockDataDao.Properties.BlockCode.eq(blockCode)).limit(1).unique().getBlockName();
        gpNameF = SplashActivity.getInstance().getDaoSession().getGpDataDao().queryBuilder().where(GpDataDao.Properties.GpCode.eq(gpCode)).limit(1).unique().getGpName();
        villageNameF = SplashActivity.getInstance().getDaoSession().getVillageDataDao().queryBuilder().where(VillageDataDao.Properties.VillageCode.eq(villageCode)).limit(1).unique().getGetVillageName();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        initializeList();
        villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(),getContext());
        blockCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyBlockcodeForEvaluation(),getContext());
        gpCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyGpcodeForEvaluation(),getContext());
        bindTrainingData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void doBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), "", false, R.id.fragmentContainer);
    }

    private void bindBlockData() {
        SplashActivity.getInstance().getDaoSession().getBlockDataDao().detachAll();
        blockListDataItemList = SplashActivity.getInstance().getDaoSession().getBlockDataDao().queryBuilder().build().list();
       /* Collections.sort(blockListDataItemList, new Comparator<BlockData>() {
            @Override
            public int compare(BlockData o1, BlockData o2) {
                return 0;
            }
        });*/
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
                gpNameF =gramPanchyt;
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
                villageNameF=vilage;
                villageCode = getVillageCode(vilage);
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(),villageCode,getContext());
                bindTrainingData();
                showData();
            }
        });
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
        SplashActivity.getInstance().getDaoSession().getVillageDataDao().detachAll();
        villageListDataItemList = SplashActivity.getInstance().getDaoSession()
                .getVillageDataDao().queryBuilder().where(VillageDataDao.Properties.GpCode.eq(gpCode))
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

    private void fillGramPanchyatList() {
        SplashActivity.getInstance().getDaoSession().getGpDataDao().detachAll();
        gpListDataItemList = SplashActivity.getInstance().getDaoSession()
                .getGpDataDao().queryBuilder().where(GpDataDao.Properties.BlockCode.eq(blockCode))
                .build().list();
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

    private void clearFocus(int i) {
        switch (i) {
            case 1:
                gpCode = "";
                villageCode = "";
                gpNameF = "";
                villageNameF="";

                selectGpSpinner.setFocusableInTouchMode(false);
                selectVillageSpinner.setFocusableInTouchMode(false);

                trainingErrorRelativelayout.setVisibility(View.GONE);

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

                break;
            case 2:
                villageCode = "";
                villageNameF="";

                selectVillageSpinner.setFocusableInTouchMode(false);
                trainingErrorRelativelayout.setVisibility(View.GONE);


                villageNameList = new String[0];

                villageListDataItemList.clear();

                selectVillageSpinner.setText("");

                ArrayAdapter<String> villageListDataItemArrayAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, villageNameList);
                selectVillageSpinner.setAdapter(villageListDataItemArrayAdapter2);
                break;
            case 3:
                blockCode = "";
                gpCode = "";
                villageCode = "";
                gpNameF = "";
                villageNameF="";
                blockNameF = "";

                selectBlockSpinner.setFocusableInTouchMode(false);
                selectGpSpinner.setFocusableInTouchMode(false);
                selectVillageSpinner.setFocusableInTouchMode(false);

                trainingErrorRelativelayout.setVisibility(View.GONE);

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

    private void setTodayDate() {
        dateSelectedtv.setText(DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));
    }

   /* @OnClick(R.id.evaluation_dateSelectedTv)
    public void dateSelected() {
        DialogFragment dateFragment = new DatePicker(dateSelectedtv);
        dateFragment.show(getFragmentManager(), "date picker");
    }*/

    private void initializeList() {
        finalSelectionShgDataList = new ArrayList<>();
        blockListDataItemList = new ArrayList<>();
        gpListDataItemList = new ArrayList<>();
        villageListDataItemList = new ArrayList<>();
        trainingEvaluationDataItemList = new ArrayList<>();
    }

    private void showData() {
        if(evaluationMasterShgData.size()>0){
            trainingErrorRelativelayout.setVisibility(View.GONE);
            trainingListLinearLayout.setVisibility(View.VISIBLE);
            //write a code for get name
            listTrainingTv.setText(blockNameF+", "+gpNameF+", "+villageNameF);
            selectShgListAdapter = new SelectShgListAdapter(getContext(),this,evaluationMasterShgData);
            evaluationShgRv.setLayoutManager(new LinearLayoutManager(getContext()));
            evaluationShgRv.setAdapter(selectShgListAdapter);
            selectShgListAdapter.notifyDataSetChanged();
        }else {
            trainingListLinearLayout.setVisibility(View.GONE);
            trainingErrorRelativelayout.setVisibility(View.VISIBLE);
            shgErrorviewTv.setText(blockNameF+", "+gpNameF+", "+villageNameF);
        }
    }

    public void bindTrainingData() {
        if(!villageCode.equalsIgnoreCase("")){
            SplashActivity.getInstance().getDaoSession()
                    .getEvaluationMasterShgDataDao().detachAll();
            evaluationMasterShgData  = SplashActivity.getInstance().getDaoSession()
                    .getEvaluationMasterShgDataDao()
                    .queryBuilder()
                    .where(EvaluationMasterShgDataDao.Properties.VillageCode.eq(villageCode))
                    .build().list();
        }else {
            AppUtility.getInstance().showLog("Village Code is not found",EvaluationFragment.class);
        }
    }


    @OnClick(R.id.shgEvaluation_nextBtn)
    public void nextBtn() {
        if(!villageCode.equalsIgnoreCase("")){
            if(evaluationStartStatus){
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(),villageCode,getContext());
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyGpcodeForEvaluation(),gpCode,getContext());
                PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyBlockcodeForEvaluation(),blockCode,getContext());
                AppUtility.getInstance().replaceFragment(getFragmentManager(), EvaluationMemberFragment.newInstance(), EvaluationMemberFragment.class.getSimpleName(), true, R.id.fragmentContainer);

            }else {
                //evaluation_shg_not_found_msg
                Toast.makeText(getContext(),getContext().getString(R.string.evaluation_shg_not_found_msg),Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(),getContext().getString(R.string.toast_select_village_msg),Toast.LENGTH_SHORT).show();
        }
    }
}
