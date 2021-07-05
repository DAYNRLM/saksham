package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.EvaluationMasterLocationData;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.EvaluationCompletShgAdapter;
import com.shaksham.view.adaptors.ShgMemberListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluationShgDetailFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {

    String status;


    @BindView(R.id.shg_detailRV)
    RecyclerView evaluationShgLocationRv;

    @BindView(R.id.evaluation_member_nextBtn)
    Button evaluationNext;

    @BindView(R.id.evaluation_member_backBtn)
    Button evaluationBack;

    @BindView(R.id.middleLayout)
    LinearLayout errorLayout;

    @BindView(R.id.titleLayout)
    TextView titletv;

    List<EvaluationMasterLocationData> evaluationMasterLocationData;
    EvaluationCompletShgAdapter evaluationCompletShgAdapter;

    public static EvaluationShgDetailFragment newInstance(String status) {
        EvaluationShgDetailFragment evaluationShgDetailFragment = new EvaluationShgDetailFragment(status);
        return evaluationShgDetailFragment;
    }

    public EvaluationShgDetailFragment(String status) {
        this.status =status;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.common_layout_for_shg_description;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.module_Evaluation));
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),EvaluationShgDetailFragment.class);

        SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().detachAll();
        titletv.setText(getContext().getString(R.string.evaluation_shg_list));
        if(status.equalsIgnoreCase("showDetail")){
            evaluationNext.setVisibility(View.GONE);
        }else {
            evaluationNext.setVisibility(View.VISIBLE);
        }
        showData();
    }

    private void showData() {
        if(evaluationMasterLocationData.size()>0){
            errorLayout.setVisibility(View.GONE);
            evaluationShgLocationRv.setVisibility(View.VISIBLE);
            evaluationCompletShgAdapter = new EvaluationCompletShgAdapter(getContext(),evaluationMasterLocationData);
            evaluationShgLocationRv.setLayoutManager(new LinearLayoutManager(getContext()));
            evaluationShgLocationRv.setAdapter(evaluationCompletShgAdapter);
            evaluationCompletShgAdapter.notifyDataSetChanged();

        }else {
            evaluationShgLocationRv.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        bindShgLocationData();
    }

    private void bindShgLocationData() {
        SplashActivity.getInstance().getDaoSession().getEvaluationMasterLocationDataDao().detachAll();
        evaluationMasterLocationData = SplashActivity.getInstance().getDaoSession().getEvaluationMasterLocationDataDao().queryBuilder().build().list();
       // evaluationMasterLocationData = SplashActivity.getInstance().getDaoSession().getEvaluationMasterLocationDataDao().queryBuilder().build().list();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.evaluation_member_nextBtn)
    public void onNext() {
        if(evaluationMasterLocationData.size()>0){
            AppUtility.getInstance().replaceFragment(getFragmentManager(), EvaluationFragment.newInstance(), EvaluationFragment.class.getSimpleName(), true, R.id.fragmentContainer);
        }else {
            Toast.makeText(getContext(),getContext().getString(R.string.toast_nodata_found),Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.evaluation_member_backBtn)
    public void onBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);

    }
    @Override
    public void doBack() {
        AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
