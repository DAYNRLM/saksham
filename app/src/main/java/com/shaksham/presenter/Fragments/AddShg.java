package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.AddShgPojo;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.AddShgAdeptor;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddShg extends Fragment implements HomeActivity.OnBackPressedListener {

    List<AddShgPojo> listShg;
    @BindView(R.id.add_Shg_rcv)
    RecyclerView rvAddShg;
    @BindView(R.id.add_shg_next)
    Button addShgNext;
    @BindView(R.id.add_shg_back)
    Button addShgBack;
    @BindView(R.id.noshgTV)
    TextView noshgTV;
    private Unbinder unbinder;


    public static AddShg getInstance() {
        AddShg addShg = new AddShg();
        return addShg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
         AppUtility.getInstance().clearDaoSession(SplashActivity.getInstance().getDaoSession());
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),AddShg.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_shg, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void addShgList() {
        String villageCode = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyVillagecodeForAddTraining(), getContext());
        AppUtility.getInstance().showLog("value of village" + villageCode, AddShg.class);

        QueryBuilder<ShgData> shgDataQueryBuilder = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder();
        List<ShgData> shgDataList = shgDataQueryBuilder.where(ShgDataDao.Properties.VillageCode.eq(villageCode)
                , shgDataQueryBuilder.and(ShgDataDao.Properties.VillageCode.eq(villageCode)
                        , ShgDataDao.Properties.BaselineStatus.eq("1"))).build().list();

        if (shgDataList.size() != 0) {
            rvAddShg.setVisibility(View.VISIBLE);

            listShg = new ArrayList<>();
            AddShgPojo modelShg;
            for (int i = 0; i < shgDataList.size(); i++) {
                ShgData shgDatadetails = shgDataList.get(i);
                modelShg = new AddShgPojo();
                modelShg.setShgCode(shgDatadetails.getShgCode());
                modelShg.setShgName(shgDatadetails.getShgName());
                //modelShg.setNoOfShg(AppConstant.shg_member_Detail[i]);
                modelShg.setPosition("" + i);
                listShg.add(modelShg);
            }
            for (AddShgPojo addShgPojo : listShg) {
                Log.d("TAG", "names" + addShgPojo.getShgName());
            }
            AddShgAdeptor addShgAdeptor = new AddShgAdeptor(listShg, getContext(), addShgNext);
            rvAddShg.setLayoutManager(new LinearLayoutManager(getContext()));
            rvAddShg.setAdapter(addShgAdeptor);
        } else {

            noshgTV.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addShgList();
    }

    @OnClick(R.id.add_shg_back)
    public void addLocationFragment() {

        SelectModuleFragment selectModuleFragment = SelectModuleFragment.getInstance();

        AppUtility.getInstance().replaceFragment(getFragmentManager(), selectModuleFragment, AddLocation.class.getSimpleName(), false, R.id.fragmentContainer);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void doBack() {
        SelectModuleFragment selectModuleFragment = SelectModuleFragment.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), selectModuleFragment, SelectModuleFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
