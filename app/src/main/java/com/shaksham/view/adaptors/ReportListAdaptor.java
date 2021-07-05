package com.shaksham.view.adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.BlockData;
import com.shaksham.model.database.BlockDataDao;
import com.shaksham.model.database.GpData;
import com.shaksham.model.database.GpDataDao;
import com.shaksham.model.database.ModuleData;
import com.shaksham.model.database.ModuleDataDao;
import com.shaksham.model.database.ViewReportModuleData;
import com.shaksham.model.database.ViewReportModuleDataDao;
import com.shaksham.model.database.ViewReportTrainingData;
import com.shaksham.model.database.VillageData;
import com.shaksham.model.database.VillageDataDao;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;

import java.util.List;
import java.util.Random;

public class ReportListAdaptor extends RecyclerView.Adapter<ReportListAdaptor.MyReportListView> {
    private Context context;
    List<ViewReportTrainingData> viewReportTrainingData;
    String moduleName="";

    public ReportListAdaptor(Context context, List<ViewReportTrainingData> viewReportTrainingData) {
        this.context = context;
        this.viewReportTrainingData = viewReportTrainingData;
    }

    @NonNull
    @Override
    public MyReportListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myReportListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_reportlist_view, parent, false);
        return new MyReportListView(myReportListView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReportListView holder, int position) {
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.parentRL.setBackgroundColor(currentColor);
        holder.dateView.setText(viewReportTrainingData.get(position).getDateOfTraining());
        String blockCode =viewReportTrainingData.get(position).getBlockCode();
        String gpCode =viewReportTrainingData.get(position).getGpCode();
        String villageCode =viewReportTrainingData.get(position).getVillageCode();
        String locationName = getName(villageCode,gpCode,blockCode);
        holder.locationView.setText(locationName);
        String shgs=viewReportTrainingData.get(position).getShgParticipant();
        holder.shgsParticipatedView.setText(shgs);
        String members=viewReportTrainingData.get(position).getMemberParticipant();
        holder.shgMembersParticipatedView.setText(members);
        String otherMembers=viewReportTrainingData.get(position).getOtherParticipant();
        holder.otherMemberParticipatedView.setText(otherMembers);
        String trainingId =viewReportTrainingData.get(position).getTrainingCode();
        String monthCode = viewReportTrainingData.get(position).getMonthCode();
        holder.moduleView.setText(getModuleName(trainingId,monthCode));

    }



    private String getModuleName(String trainingId, String monthCode) {

        String name[];
        String s="";

        SplashActivity.getInstance().getDaoSession().getViewReportModuleDataDao().detachAll();
        List<ViewReportModuleData> viewReportModuleData = SplashActivity.getInstance()
                .getDaoSession().getViewReportModuleDataDao()
                .queryBuilder()
                .where(ViewReportModuleDataDao.Properties.TringngCode.eq(trainingId)
                        ,ViewReportModuleDataDao.Properties.MonthCode.eq(monthCode))
                .build().list();


        name =new String[viewReportModuleData.size()];

        for(int i=0;i<viewReportModuleData.size();i++){
            AppUtility.getInstance().showLog("viewReportModuleData"+viewReportModuleData.size(),ReportListAdaptor.class);
            String moduleId  =getName(viewReportModuleData.get(i).getModuleId());
           // name [i]=moduleId;
            s +=moduleId+",";
        }
        /*for(String str:name){
            s +=str+",";
        }*/
        s=AppUtility.getInstance().removeCommaFromLast(s);
        return s;
    }

    private String getName(String moduleId) {
        String name="";
        ModuleData moduleData = SplashActivity.getInstance()
                .getDaoSession().getModuleDataDao()
                .queryBuilder().where(ModuleDataDao.Properties.ModuleCode.eq(moduleId))
                .limit(1).unique();
        if(moduleData!=null){
            name = moduleData.getModuleName();
        }

        return name;
    }

    private String getName(String vCode,String gpCode,String blockCode) {
        String villageName ="";
        String blockname ="";
        String gpName ="";

        VillageData villageData = SplashActivity.getInstance().getDaoSession()
                .getVillageDataDao().queryBuilder()
                .where(VillageDataDao.Properties.VillageCode.eq(vCode)).limit(1).unique();

        BlockData blockData = SplashActivity.getInstance().getDaoSession()
                .getBlockDataDao().queryBuilder()
                .where(BlockDataDao.Properties.BlockCode.eq(blockCode)).limit(1).unique();

        GpData gpData = SplashActivity.getInstance().getDaoSession()
                .getGpDataDao().queryBuilder()
                .where(GpDataDao.Properties.GpCode.eq(gpCode)).limit(1).unique();

        if(villageData!=null){
            villageName = villageData.getGetVillageName();
        }

        if(blockData!=null){
            blockname = blockData.getBlockName();
        }

        if(gpData!=null){
            gpName = gpData.getGpName();
        }

      /*  return blockname+">>"+gpName+">>"+villageName;*/
        return villageName;
    }

    @Override
    public int getItemCount() {
        return viewReportTrainingData.size();
    }

    public class MyReportListView extends RecyclerView.ViewHolder {
        private TextView dateView, blockView, locationView, shgsParticipatedView, shgMembersParticipatedView, otherMemberParticipatedView, moduleView;
        public RelativeLayout parentRL;
        public MyReportListView(@NonNull View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.date_viewTV);
            locationView = (TextView) itemView.findViewById(R.id.loc_of_training_viewTV);
            shgsParticipatedView = (TextView) itemView.findViewById(R.id.shgParticipated_viewTV);
            shgMembersParticipatedView = (TextView) itemView.findViewById(R.id.no_of_shg_member_participated_viewTV);
            otherMemberParticipatedView = (TextView) itemView.findViewById(R.id.other_member_participated_viewTV);
            moduleView = (TextView) itemView.findViewById(R.id.modules_viewTV);
            parentRL=(RelativeLayout)itemView.findViewById(R.id.parentRL);
        }
    }
}
