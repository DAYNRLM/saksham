package com.shaksham.view.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.AddedTrainingShgData;
import com.shaksham.model.database.AddedTrainingShgDataDao;
import com.shaksham.model.database.BlockDataDao;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.GpDataDao;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.model.database.VillageDataDao;
import com.shaksham.presenter.Activities.SplashActivity;

import java.util.List;

public class DashBoardEvaluationAdapter extends RecyclerView.Adapter<DashBoardEvaluationAdapter.MyViewHolder> {
    List<EvaluationMasterShgData> selectedTrainingDataList;
    Context context;

    public DashBoardEvaluationAdapter(List<EvaluationMasterShgData> selectedTrainingDataList, Context context) {
        this.selectedTrainingDataList = selectedTrainingDataList;
        this.context = context;
    }

    public DashBoardEvaluationAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLanguageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_evaluation_custom_layout, parent, false);
        return new DashBoardEvaluationAdapter.MyViewHolder(myLanguageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       /* String traingId = selectedTrainingDataList.get(position).getTrainingId();
        String gpCode = selectedTrainingDataList.get(position).getGpCode();
        String villageCode = selectedTrainingDataList.get(position).getVillageCode();
        String blockCode = selectedTrainingDataList.get(position).getBlockcode();
        holder.location.setText( getLocationName(gpCode,villageCode,blockCode));
        holder.shgNumber.setText(getShgCount(traingId));*/
       holder.shgName.setText(getShgName(selectedTrainingDataList.get(position).getShgCode()));
    }

    private String getShgName(String shgCode) {
        String name = SplashActivity.getInstance().getDaoSession().getShgDataDao()
                .queryBuilder().where(ShgDataDao.Properties.ShgCode.eq(shgCode))
                .limit(1).unique()
                .getShgName();
        return name;
    }


    private String getShgCount(String traingId) {
        List<AddedTrainingShgData> addedTrainingShgData = SplashActivity.getInstance().getDaoSession().getAddedTrainingShgDataDao().queryBuilder().where(AddedTrainingShgDataDao.Properties.TrainingId.eq(traingId)).build().list();
        return ""+addedTrainingShgData.size();
    }

    public String getLocationName(String gpCode, String villageCode, String blockCode) {
        String location = "";
        String  blockName = SplashActivity.getInstance().getDaoSession().getBlockDataDao().queryBuilder().where(BlockDataDao.Properties.BlockCode.eq(blockCode)).limit(1).unique().getBlockName();
        String gpName = SplashActivity.getInstance().getDaoSession().getGpDataDao().queryBuilder().where(GpDataDao.Properties.GpCode.eq(gpCode)).limit(1).unique().getGpName();
        String villageName = SplashActivity.getInstance().getDaoSession().getVillageDataDao().queryBuilder().where(VillageDataDao.Properties.VillageCode.eq(villageCode)).limit(1).unique().getGetVillageName();
        location = blockName+", "+gpName+", "+villageName;
        return location;
    }

    @Override
    public int getItemCount() {
        return selectedTrainingDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView location,shgName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shgName = (TextView)itemView.findViewById(R.id.notificationShgName_tv);
        }
    }
}
