package com.shaksham.view.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.presenter.Fragments.EvaluationFragment;
import com.shaksham.utils.DateFactory;

import java.util.Date;
import java.util.List;

public class SelectShgListAdapter extends RecyclerView.Adapter<SelectShgListAdapter.MyViewHolder> {
    Context context;
    EvaluationFragment evaluationFragment;
    List<EvaluationMasterShgData> evaluationMasterShgData;


    public SelectShgListAdapter(Context context, EvaluationFragment evaluationFragment,List<EvaluationMasterShgData> evaluationMasterShgData) {
        this.context = context;
        this.evaluationFragment = evaluationFragment;
        this.evaluationMasterShgData =evaluationMasterShgData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View selectShgListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_select_training_custom_lauout, parent, false);
        return new SelectShgListAdapter.MyViewHolder(selectShgListView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //trainingId = trainingEvaluationDataItemList.get(position).getTrainingId();
        ChildEvaluationAdatpter childEvaluationAdatpter = new ChildEvaluationAdatpter();

        /**worked on this line by abdul and chang by lincon on 6 aug-2020**/
        holder.ShgName.setText(childEvaluationAdatpter.getShgName(evaluationMasterShgData.get(position).getShgCode()));
        holder.evaluationDate.setText(DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.get(position).getEvaluationDate()));

        Date ev = DateFactory.getInstance().getDateFormate(DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.get(position).getEvaluationDate()));
        Date td = DateFactory.getInstance().getDateFormate(DateFactory.getInstance().getTodayDate());
        Date evMDate = DateFactory.getInstance().getDateFormate(DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.get(position).getMaximunEvaluationdate()));
        int evaluationDaysLefy = Integer.parseInt(DateFactory.getInstance().getCountOfDays(ev,td));
        int evaluationTypeStatus = Integer.parseInt(evaluationMasterShgData.get(position).getEvaluationStatus())+1;
       /* if (evaluationDaysLefy<16) {
            holder.ShgName.setText(childEvaluationAdatpter.getShgName(evaluationMasterShgData.get(position).getShgCode()));
            holder.evaluationDate.setText(DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.get(position).getEvaluationDate()));
        }*/
            //0,1,2,3 for four different evaluation
            holder.evaluationType.setText(" " + evaluationTypeStatus +"  "+ context.getString(R.string.module_Evaluation));


            if (evaluationDaysLefy <= 0) {
                if (evaluationMasterShgData.get(position).getEvaluationdonestatus().equalsIgnoreCase("1")) {
                    holder.noOfdaysLeft.setText(context.getString(R.string.evaluation_done_msg));
                } else {
                    EvaluationFragment.evaluationStartStatus = true;
                    if (evMDate.compareTo(td) <= 0) {
                        holder.ShgName.setTextColor(context.getResources().getColor(R.color.color_red));
                        holder.noOfdaysLeft.setText(context.getString((R.string.pending_evaluation)));
                    } else {
                        holder.ShgName.setTextColor(context.getResources().getColor(R.color.color_green));
                        holder.noOfdaysLeft.setText(context.getString(R.string.evaluation_start_msg));
                    }
                }
            } else {
                holder.noOfdaysLeft.setText(""+evaluationDaysLefy+" "+context.getString(R.string.evaluation_days_msg));
            }
            holder.trainingDate.setText(DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.get(position).getFirstTrainingdate()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

    }


    /*private void getShgMemberListdata() {
        trainingShgMemberDataList = SplashActivity.getInstance().getDaoSession()
                .getAddedTrainingShgMemberDataDao()
                .queryBuilder()
                .where(AddedTrainingShgMemberDataDao.Properties.ShgCode.eq(shgCode)
                        , AddedTrainingShgMemberDataDao.Properties.TrainingId.eq(trainingId))
                .build().list();
    }*/

    /*private void getShgListData() {
        trainingShgDataList = SplashActivity.getInstance().getDaoSession()
                .getAddedTrainingShgDataDao().queryBuilder()
                .where(AddedTrainingShgDataDao.Properties.TrainingId.eq(trainingId))
                .build().list();
        shgDataListSize = trainingShgDataList.size();
        for (AddedTrainingShgData addedTrainingShgData : trainingShgDataList) {
            shgCode = addedTrainingShgData.getShgCode();

        }
    }*/

   /* private void getModuleListData() {
        trainingModuleDataList = SplashActivity.getInstance().getDaoSession()
                .getAddedTrainingShgModuleDataDao()
                .queryBuilder()
                .where(AddedTrainingShgModuleDataDao.Properties.ShgCode.eq(shgCode)
                        , AddedTrainingShgModuleDataDao.Properties.TrainingId.eq(trainingId))
                .build().list();
    }*/

    @Override
    public int getItemCount() {
        return evaluationMasterShgData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ShgName, evaluationDate,noOfdaysLeft,trainingDate,evaluationType;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ShgName = (TextView) itemView.findViewById(R.id.evaluation_ShgNametv);
            noOfdaysLeft = (TextView) itemView.findViewById(R.id.evaluation_NoOfDaysLeftTv);
            evaluationDate = (TextView) itemView.findViewById(R.id.evealuation_StartDateTv);
            trainingDate = (TextView)itemView.findViewById(R.id.trainingDate_Tv);
            evaluationType = (TextView)itemView.findViewById(R.id.evaluation_TypeTv);
        }
    }
}
