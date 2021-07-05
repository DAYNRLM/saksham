package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shaksham.R;
import com.shaksham.model.PojoData.SelectEvaluationShgPojo;
import com.shaksham.model.database.AddedTrainingShgData;
import com.shaksham.model.database.AddedTrainingShgMemberData;
import com.shaksham.model.database.AddedTrainingsData;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterShgDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.ShgMemberListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluationMemberFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {
 /*   @BindView(R.id.evaluation_member_nextBtn)
    Button shgEvealuationMemberNextBtn;

    @BindView(R.id.evaluation_member_backBtn)
    Button shgEvealuationMemberBackBtn;*/

    @BindView(R.id.evealuation_enterdMemberRv)
    RecyclerView enteredMemberDetailRv;

    @BindView(R.id.evaluationDoneMessg_tv)
    TextView msgTv;





    List<SelectEvaluationShgPojo> shgMemberDataList;
    public ShgMemberListAdapter selectMemberShgListAdapter;
    List<AddedTrainingShgData> trainingShgDataList;
    List<AddedTrainingShgData> trainingShgGataListForAdapter;
    List<AddedTrainingsData> trainingEvaluationDataItemList;
    List<AddedTrainingShgMemberData> trainingShgMemberDataList;
    String shgCode, trainingId, villageCode;
    //changed HAPPEN
    List<EvaluationMasterShgData> evaluationMasterShgDataList;

    public static EvaluationMemberFragment newInstance() {
        EvaluationMemberFragment EvaluationMemberFragment = new EvaluationMemberFragment();
        return EvaluationMemberFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.evaluation_member;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.module_Evaluation));
        AppUtility.getInstance().clearDaoSession(SplashActivity.getInstance().daoSession);
        AppUtility.getInstance().showLog("loginStatus" + PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()), EvaluationMemberFragment.class);
        showData();

    }

    private void showData() {
        if(evaluationMasterShgDataList.size()>0){
            selectMemberShgListAdapter = new ShgMemberListAdapter(getContext(), this, evaluationMasterShgDataList);
            enteredMemberDetailRv.setLayoutManager(new LinearLayoutManager(getContext()));
            enteredMemberDetailRv.setAdapter(selectMemberShgListAdapter);
            selectMemberShgListAdapter.notifyDataSetChanged();
        }else {
            msgTv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
        trainingId = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyTrainingidForEvealuation(), getContext());
        villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForEvaluation(), getContext());
        getShgListData();
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
    }

    private void initializeList() {
        shgMemberDataList = new ArrayList<>();
        trainingShgGataListForAdapter = new ArrayList<>();
    }


    private void getShgListData() {
        SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().detachAll();
        evaluationMasterShgDataList = SplashActivity.getInstance()
                .getDaoSession().getEvaluationMasterShgDataDao()
                .queryBuilder().where(EvaluationMasterShgDataDao.Properties.VillageCode.eq(villageCode),EvaluationMasterShgDataDao.Properties.Evaluationdonestatus.eq("0"))
                .build().list();
        AppUtility.getInstance().showLog("trainingSelectedShgSize:-" + evaluationMasterShgDataList.size(), EvaluationMemberFragment.class);
    }

    private void getListFromPreference() {
        Gson gson = new Gson();
        String memberList = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPREF_KEY_SELECTED_SHGs(), getContext());
        AppUtility.getInstance().showLog("member list string" + memberList, EvaluationFragment.class);
        Type type = new TypeToken<List<SelectEvaluationShgPojo>>() {
        }.getType();
        shgMemberDataList = gson.fromJson(memberList, type);
        AppUtility.getInstance().showLog("shg member list size" + shgMemberDataList.size(), EvaluationFragment.class);
    }

/*
    @OnClick(R.id.evaluation_member_nextBtn)

    public void saveClicked() {
        Toast.makeText(getContext(), "nothing happen ", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.evaluation_member_backBtn)
    public void saveClicked1() {
        EvaluationFragment evaluationFragment = EvaluationFragment.newInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), evaluationFragment, EvaluationFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }*/


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void doBack() {
        AppUtility.getInstance().showLog("Total member in selected Shg--", EvaluationMemberFragment.class);
        EvaluationFragment evaluationFragment = EvaluationFragment.newInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), evaluationFragment, EvaluationFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
