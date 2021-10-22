package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shaksham.R;
import com.shaksham.model.PojoData.BaselineDonePojo;
import com.shaksham.model.database.BlockData;
import com.shaksham.model.database.GpData;
import com.shaksham.model.database.GpDataDao;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.model.database.VillageData;
import com.shaksham.model.database.VillageDataDao;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class BaselineDoneFragment extends Fragment {
    List<BaselineDonePojo.Block> blockInfo = new ArrayList<>();
    List<BaselineDonePojo.Block.Gp> gpInfo = new ArrayList<>();
    List<BaselineDonePojo.Block.Gp.Village> villages = new ArrayList<>();
    List<BaselineDonePojo.Block.Gp.Village.Shgs> shgs = new ArrayList<>();
    BaselineDonePojo.Block.Gp gpVillage;
    BaselineDonePojo.Block.Gp.Village villageShg;
    BaselineDonePojo.Block block1;
    BaselineDonePojo.Block.Gp gp;
    BaselineDonePojo.Block.Gp.Village village;
    List<BaselineDonePojo.Block> blockss = new ArrayList<>();

    public static BaselineDoneFragment getInstance() {
        BaselineDoneFragment baselineDoneFragment = new BaselineDoneFragment();

        return baselineDoneFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),BaselineDoneFragment.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.base_line_done_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SplashActivity.getInstance().getDaoSession().getGpDataDao().detachAll();
        List<BlockData> block = SplashActivity.getInstance().getDaoSession().getBlockDataDao().queryBuilder().build().list();
        for (int i = 0; i < block.size(); i++) {
            block1 = new BaselineDonePojo.Block();
            block1.setBlockName(block.get(i).getBlockName());
            block1.setBlockCode(block.get(i).getBlockCode());
            blockInfo.add(block1);
            BaselineDonePojo.baselineDonePojo.setBlocks(blockInfo);
            SplashActivity.getInstance().getDaoSession().getGpDataDao().detachAll();
            List<GpData> gpData = SplashActivity.getInstance().getDaoSession().getGpDataDao().queryBuilder().where(GpDataDao.Properties.BlockCode.eq(block.get(i).getBlockCode())).build().list();
            for (int j = 0; j < gpData.size(); j++) {
                gp = new BaselineDonePojo.Block.Gp();
                gp.setGpName(gpData.get(j).getGpName());
                gp.setGpCode(gpData.get(j).getGpCode());
                gpInfo.add(gp);
                block1.setGps(gpInfo);
                SplashActivity.getInstance().getDaoSession().getVillageDataDao().detachAll();
                List<VillageData> villageData = SplashActivity.getInstance().getDaoSession().getVillageDataDao().queryBuilder().where(VillageDataDao.Properties.GpCode.eq(gpData.get(j).getGpCode())).build().list();
                villages.clear();
                for (int k = 0; k < villageData.size(); k++) {
                    village = new BaselineDonePojo.Block.Gp.Village();
                    village.setVillageName(villageData.get(k).getGetVillageName());
                    village.setVillageCode(villageData.get(k).getVillageCode());
                    villages.add(village);
                    gp.setVillages(villages);
                    if (k == (villageData.size()) - 1)
                        blockss.add(block1);
                    //villages=new ArrayList<>();
                    SplashActivity.getInstance().getDaoSession().getShgDataDao().detachAll();
                    QueryBuilder<ShgData> shgDataQueryBuilder = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder();
                    List<ShgData> baselineDoneshg = shgDataQueryBuilder.where(ShgDataDao.Properties.VillageCode.eq(villageData.get(k).getVillageCode()), shgDataQueryBuilder.and(ShgDataDao.Properties.VillageCode.eq(villageData.get(k).getVillageCode()), ShgDataDao.Properties.BaselineStatus.eq("1"))).build().list();
                    for (int l = 0; l < baselineDoneshg.size(); l++) {
                        BaselineDonePojo.Block.Gp.Village.Shgs shg = new BaselineDonePojo.Block.Gp.Village.Shgs();
                        shg.setShgName(baselineDoneshg.get(l).getShgName());
                        shg.setShgName(baselineDoneshg.get(l).getShgCode());
                        shgs.add(shg);
                    }
                }
            }
        }
        //   villageShg.setShgs(shgs);
        BaselineDonePojo data = BaselineDonePojo.baselineDonePojo;
        AppUtility.getInstance().showLog("" + data, BaselineDoneFragment.class);
    }
}
