package com.shaksham.view.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.EvaluationMasterLocationData;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterShgDataDao;
import com.shaksham.presenter.Activities.SplashActivity;

import java.util.List;

public class EvaluationCompletShgAdapter extends RecyclerView.Adapter<EvaluationCompletShgAdapter.MyViewHolder> {
    Context context;
    List<EvaluationMasterLocationData> evaluationMasterLocationData;
    List<EvaluationMasterShgData> evaluationMasterShgData;
    ChildEvaluationAdatpter childEvaluationAdatpter;

    public EvaluationCompletShgAdapter(Context context, List<EvaluationMasterLocationData> evaluationMasterLocationData) {
        this.context = context;
        this.evaluationMasterLocationData = evaluationMasterLocationData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myEvaluationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_shg_detail_layout, parent, false);
        return new EvaluationCompletShgAdapter.MyViewHolder(myEvaluationView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String blockCode = evaluationMasterLocationData.get(position).getBlockCode();
        String villageCode = evaluationMasterLocationData.get(position).getVillageCode();
        String gpCode = evaluationMasterLocationData.get(position).getGpCode();
        DashBoardEvaluationAdapter dashBoardEvaluationAdapter = new DashBoardEvaluationAdapter();
        holder.locationTv.setText(dashBoardEvaluationAdapter.getLocationName(gpCode,villageCode,blockCode));
        getshgList(villageCode);
        //call adapter for shg details
        if(evaluationMasterShgData.size()>1){
            /*show text*/
            holder.showPendingEvaluationDataNum.setText(context.getString(R.string.pending_evaluation1)+"     "+evaluationMasterShgData.size());
            holder.showPendingEvaluationDataNum.setVisibility(View.VISIBLE);
        }else {
            holder.showPendingEvaluationDataLL.setVisibility(View.VISIBLE);
            childEvaluationAdatpter = new ChildEvaluationAdatpter(context,evaluationMasterShgData);
            holder.shgDetailRV.setLayoutManager(new LinearLayoutManager(context));
            holder.shgDetailRV.setAdapter(childEvaluationAdatpter);
            childEvaluationAdatpter.notifyDataSetChanged();
        }
    }

    private void getshgList(String villageCode) {
        SplashActivity.getInstance().getDaoSession().getEvaluationMasterShgDataDao().detachAll();
        evaluationMasterShgData = SplashActivity.getInstance()
                .getDaoSession()
                .getEvaluationMasterShgDataDao()
                .queryBuilder().where(EvaluationMasterShgDataDao.Properties.VillageCode.eq(villageCode))
                .build().list();

        //************query for get shg whos status is 0************************
        /*evaluationMasterShgData = SplashActivity.getInstance()
                .getDaoSession()
                .getEvaluationMasterShgDataDao()
                .queryBuilder().where(EvaluationMasterShgDataDao.Properties.VillageCode.eq(villageCode),EvaluationMasterShgDataDao.Properties.EvaluationStatus.eq("0"))
                .build().list();*/
    }

    @Override
    public int getItemCount() {
        return evaluationMasterLocationData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView locationTv,showPendingEvaluationDataNum;
        RecyclerView shgDetailRV;
        LinearLayout showPendingEvaluationDataLL;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTv = (TextView)itemView.findViewById(R.id.shgLocation_Tv);
            showPendingEvaluationDataNum = (TextView)itemView.findViewById(R.id.showPendingEvaluationDataNum);
            shgDetailRV = (RecyclerView)itemView.findViewById(R.id.location_ShgDetailRv);
            showPendingEvaluationDataLL=(LinearLayout)itemView.findViewById(R.id.showPendingEvaluationDataLL);
        }
    }
}
