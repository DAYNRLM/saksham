package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.database.BlockData;
import com.shaksham.model.database.GpData;
import com.shaksham.model.database.GpDataDao;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.model.database.VillageData;
import com.shaksham.model.database.VillageDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.BaseLineDoneAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.shaksham.model.PojoData.AddTrainingPojo.addTrainingPojo;


public class AddLocation extends Fragment implements HomeActivity.OnBackPressedListener {
    @BindView(R.id.addlocfr_next_btn)
    Button Loc_next;
    @BindView(R.id.back_of_addlocation)
    Button loc_back;
    @BindView(R.id.spinner_selectblock)
    MaterialBetterSpinner spSelectBlock;
    @BindView(R.id.spinner_select_gp)
    MaterialBetterSpinner spSelectGp;
    @BindView(R.id.spinner_select_village)
    MaterialBetterSpinner spSelectVillage;
    @BindView(R.id.text_date_of_training_addlocation)
    TextView tvDateTraining;
    @BindView(R.id.noDataFoundLL)
    LinearLayout noDataFoundLL;
    @BindView(R.id.baseline_doneShg)
    RecyclerView baselineshgRv;
    @BindView(R.id.baseline_doneShglinr)
    LinearLayout baselineLiner;

    private Unbinder unbinder;
    //@BindView(R.id.spi)
    //  TextView tvSpi;
    ArrayAdapter<String> blockAdp;
    ArrayAdapter<String> gpAdp;
    List<GpData> gpInfo;
    List<VillageData> villageInfo;
    ArrayAdapter<String> villageAdp;
    List<BlockData> blockInfo;
    String selectedBlockCode,selectedGpCode,selectedVillageCode;
    List<ShgData> shgDataListDonebaseline;
    BaseLineDoneAdapter baseLineDoneAdapter;
    public static AddLocation getInstance() {
        AddLocation addLocation = new AddLocation();
        return addLocation;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        ((HomeActivity)getActivity()).setToolBarTitle(getString(R.string.title_addtraining));
         AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),AddLocation.class);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addlocation, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvDateTraining.setText(DateFactory.getInstance().getTodayDate());
        noDataFoundLL.setVisibility(View.GONE);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spSelectGp.setFocusable(false);
        spSelectVillage.setFocusable(false);
        blockInfo = SplashActivity.getInstance().getDaoSession().getBlockDataDao().queryBuilder().build().list();
        ArrayList<String> blockName = new ArrayList<>();
        for (int i = 0; i < blockInfo.size(); i++) {
            BlockData blockData = blockInfo.get(i);
            String bName = blockData.getBlockName();
            blockName.add(bName);
        }
        blockAdp = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, blockName);
        spSelectBlock.setAdapter(blockAdp);
        spSelectBlock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBlockCode = blockInfo.get(position).getBlockCode();
                AppUtility.getInstance().showLog("selectedBlockCode" + selectedBlockCode, AddLocation.class);
                AppUtility.getInstance().showLog("selectedBlockName" + blockInfo.get(position).getBlockName(), AddLocation.class);
                addTrainingPojo.setBlockCode(selectedBlockCode);
                addTrainingPojo.setBlockName(blockInfo.get(position).getBlockName());
                addTrainingPojo.setTrainingCreatedDate(DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));
                spSelectGp.setFocusable(true);
                spSelectGp.setText("");
                spSelectVillage.setText("");
                baselineLiner.setVisibility(View.GONE);
                noDataFoundLL.setSystemUiVisibility(View.GONE);
                getGp();
            }
            private void getGp() {
                gpInfo = SplashActivity.getInstance().getDaoSession().getGpDataDao().queryBuilder().where(GpDataDao.Properties.BlockCode.eq(selectedBlockCode)).build().list();
                ArrayList<String> gpName = new ArrayList<>();
                for (int i = 0; i < gpInfo.size(); i++) {
                    GpData gpData = gpInfo.get(i);
                    gpName.add(gpData.getGpName());
                }
                gpAdp = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, gpName);
                spSelectGp.setAdapter(gpAdp);
                gpAdp.notifyDataSetChanged();
                spSelectGp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedGpCode = gpInfo.get(position).getGpCode();
                        AppUtility.getInstance().showLog("selectedGpCode"+selectedGpCode,AddLocation.class);
                        AppUtility.getInstance().showLog("selectedGpName"+gpInfo.get(position).getGpName(),AddLocation.class);
                        shgDataListDonebaseline=new ArrayList<>();
                        AddTrainingPojo.addTrainingPojo.setGpCode(selectedGpCode);
                        AddTrainingPojo.addTrainingPojo.setGpName(gpInfo.get(position).getGpName());
                        spSelectVillage.setFocusable(true);
                        spSelectVillage.setText("");
                       // shgDataListDonebaseline.clear();
                        //baseLineDoneAdapter.notifyDataSetChanged();\
                        noDataFoundLL.setVisibility(View.GONE);
                        baselineLiner.setVisibility(View.GONE);

                        getVillage();
                    }
                    private void getVillage() {
                        villageInfo = SplashActivity.getInstance().getDaoSession().getVillageDataDao().queryBuilder().where(VillageDataDao.Properties.GpCode.eq(selectedGpCode)).build().list();

                        ArrayList<String> villageName = new ArrayList<>();
                        for (int i = 0; i < villageInfo.size(); i++) {
                            VillageData villageData = villageInfo.get(i);
                            villageName.add(villageData.getGetVillageName());
                            AppUtility.getInstance().showLog("selectedVillageCode"+villageData.getVillageCode(),AddLocation.class);
                        }
                        villageAdp = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, villageName);
                        spSelectVillage.setAdapter(villageAdp);
                        villageAdp.notifyDataSetChanged();
                        spSelectVillage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                             selectedVillageCode=villageInfo.get(position).getVillageCode();
                             addTrainingPojo.setVillageCode(selectedVillageCode);
                             AppUtility.getInstance().showLog("addvillagecode"+villageInfo.get(position).getVillageCode(),AddLocation.class);
                             AppUtility.getInstance().showLog("addvillageNme"+villageInfo.get(position).getGetVillageName(),AddLocation.class);
                             addTrainingPojo.setVillageName(villageInfo.get(position).getGetVillageName());
                             PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyVillagecodeForAddTraining(), villageInfo.get(position).getVillageCode(), getContext());
                             ShgData shgData=new ShgData();
                             SplashActivity.getInstance().getDaoSession().getShgDataDao().detachAll();
                             String villageCode=PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForAddTraining(),getContext());
                             QueryBuilder<ShgData> shgDataQueryBuilder=SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder();
                             shgDataListDonebaseline=shgDataQueryBuilder.where(ShgDataDao.Properties.VillageCode.eq(villageCode),shgDataQueryBuilder.and(ShgDataDao.Properties.VillageCode.eq(villageCode),ShgDataDao.Properties.BaselineStatus.eq("1"))).build().list();
                             if(shgDataListDonebaseline.size()==0)
                                 baselineLiner.setVisibility(View.GONE);
                             else {
                                 baselineLiner.setVisibility(View.VISIBLE);
                                 noDataFoundLL.setVisibility(View.GONE);
                                 BaseLineDoneAdapter baseLineDoneAdapter = new BaseLineDoneAdapter(shgDataListDonebaseline, getContext());
                                 baselineshgRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                 baselineshgRv.setAdapter(baseLineDoneAdapter);
                                 baseLineDoneAdapter.notifyDataSetChanged();
                             }
                            }
                        });
                    }
                });
            }
        });
    }
    @OnClick(R.id.addlocfr_next_btn)
    public void addSHGFragmentOnNextClick() {
        if (selectedVillageCode != null && !selectedVillageCode.equalsIgnoreCase("")) {
            String villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForAddTraining(), getContext());
            AppUtility.getInstance().showLog("value of village" + villageCode, AddShg.class);
            QueryBuilder<ShgData> shgDataQueryBuilder = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder();
            List<ShgData> shgDataList = shgDataQueryBuilder.where(ShgDataDao.Properties.VillageCode.eq(villageCode)
                    , shgDataQueryBuilder.and(ShgDataDao.Properties.VillageCode.eq(villageCode)
                            , ShgDataDao.Properties.BaselineStatus.eq("1"))).build().list();
            if (shgDataList.size() > 0) {
                SelectModuleFragment selectModuleFragment = SelectModuleFragment.getInstance();
                AppUtility.getInstance().replaceFragment(getFragmentManager(), selectModuleFragment, AddShg.class.getSimpleName(), true, R.id.fragmentContainer);
            } else {
                noDataFoundLL.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.please_fill_proper_details), Toast.LENGTH_SHORT).show();
        }

    }
    @OnClick(R.id.back_of_addlocation)
    public void openDashboard() {
        DashBoardFragment dashBoardFragment = DashBoardFragment.newInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), dashBoardFragment, DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }

    @Override
    public void doBack() {
        DashBoardFragment dashBoardFragment = DashBoardFragment.newInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), dashBoardFragment, DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
