package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.BaslineSelectShgAdaptor;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaselineSelectShg extends Fragment implements HomeActivity.OnBackPressedListener {

    @BindView(R.id.basline_shgListRV)
    RecyclerView shgListRv;

    private Unbinder unbinder;

    List<ShgData> baselineSelectShgs;
    BaslineSelectShgAdaptor baslineSelectShgAdaptor;

    String blockCode, villageCode, gpCode;

    public static BaselineSelectShg getInstance() {
        BaselineSelectShg baselineSelectShg = new BaselineSelectShg();
        return baselineSelectShg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        initializer();
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),BaselineSelectShg.class);

        AppUtility.getInstance().clearDaoSession(SplashActivity.getInstance().daoSession);
        getDataFromPreference();
        bindData();
    }

    private void getDataFromPreference() {

        blockCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedBlockCodeForBasline(), getContext());
        villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedVillageCodeForBasline(), getContext());
        gpCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedGp_codeForBasline(), getContext());
    }

    private void bindData() {
        QueryBuilder<ShgData> shgDataQueryBuilder = SplashActivity.getInstance().getDaoSession().getShgDataDao()
                .queryBuilder();
        baselineSelectShgs = shgDataQueryBuilder.where(ShgDataDao.Properties.VillageCode.eq(villageCode)
                , shgDataQueryBuilder.and(ShgDataDao.Properties.VillageCode.eq(villageCode)
                        , ShgDataDao.Properties.BaselineStatus.eq("0"))).build().list();
        AppUtility.getInstance().showLog("size of selected shg list" + baselineSelectShgs.size(), BaselineSelectShg.class);
    }

    private void initializer() {
        baselineSelectShgs = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basline_select_shg_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        showData();
        return view;
    }

    private void showData() {
        String status = "";
        for (int i = 0; i < baselineSelectShgs.size(); i++) {
            status = baselineSelectShgs.get(i).getVillageStatus();
        }
        if (status.equalsIgnoreCase("Shgs not Available in this village !!!")) {
            Toast.makeText(getContext(), getString(R.string.toast_basline_shg_not_found), Toast.LENGTH_SHORT).show();
        } else {
            baslineSelectShgAdaptor = new BaslineSelectShgAdaptor(getContext(), baselineSelectShgs, this);
            shgListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            shgListRv.setAdapter(baslineSelectShgAdaptor);
            baslineSelectShgAdaptor.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public void doBack() {

        AppUtility.getInstance().replaceFragment(getFragmentManager(), BaseLineFilterShgFragment.getInstance(), BaseLineFilterShgFragment.class.getSimpleName(), false, R.id.fragmentContainer);

    }
}
