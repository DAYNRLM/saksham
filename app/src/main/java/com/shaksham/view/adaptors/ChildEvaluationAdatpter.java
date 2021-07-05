package com.shaksham.view.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.DateFactory;

import java.util.Date;
import java.util.List;

public class ChildEvaluationAdatpter extends RecyclerView.Adapter<ChildEvaluationAdatpter.MyViewHolder> {
    Context context;
    List<EvaluationMasterShgData> evaluationMasterShgData;

    public ChildEvaluationAdatpter(Context context, List<EvaluationMasterShgData> evaluationMasterShgData) {
        this.context = context;
        this.evaluationMasterShgData = evaluationMasterShgData;
    }

    public ChildEvaluationAdatpter() {
    }

    @NonNull
    @Override
    public ChildEvaluationAdatpter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLanguageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_child_shg_detail_layout, parent, false);
        return new ChildEvaluationAdatpter.MyViewHolder(myLanguageView);
    }


    @Override
    public void onBindViewHolder(@NonNull ChildEvaluationAdatpter.MyViewHolder holder, int position) {
        String shgCode = evaluationMasterShgData.get(position).getShgCode();

            String evalutionDate = DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.get(position).getEvaluationDate());
            Date evaluatiom = DateFactory.getInstance().getDateFormate(evalutionDate);
            Date todayDate = DateFactory.getInstance().getDateFormate(DateFactory.getInstance().getTodayDate());
            Date evMax = DateFactory.getInstance().getDateFormate(DateFactory.getInstance().geteDateFromTimeStamp(evaluationMasterShgData.get(position).getMaximunEvaluationdate()));
            int evaluationDaysLefy = Integer.parseInt(DateFactory.getInstance().getCountOfDays(evaluatiom, todayDate));
                holder.shgName.setText(getShgName(shgCode));
                if (evaluationMasterShgData.get(position).getEvaluationStatus().equalsIgnoreCase("4")) {
                    holder.evaluationType.setText("");

                } else {
                    int evaluationTypeStatus = Integer.parseInt(evaluationMasterShgData.get(position).getEvaluationStatus()) + 1;//0,1,2,3 for four different evaluation
                    holder.evaluationType.setText("" + evaluationTypeStatus);
                }

                String ststus = evaluationMasterShgData.get(position).getEvaluationStatus();
                switch (ststus) {
                    case "0":
                        break;
                    case "1":
                        holder.mainDateLayout.setVisibility(View.VISIBLE);
                        holder.firstLayout.setVisibility(View.VISIBLE);
                        holder.firstDate.setVisibility(View.VISIBLE);
                        holder.firstDate.setText(evaluationMasterShgData.get(position).getFirstEvaluationDate());
                        break;
                    case "2":
                        holder.mainDateLayout.setVisibility(View.VISIBLE);
                        holder.firstLayout.setVisibility(View.VISIBLE);
                        holder.firstDate.setVisibility(View.VISIBLE);
                        holder.secondLayout.setVisibility(View.VISIBLE);
                        holder.secondDate.setVisibility(View.VISIBLE);
                        holder.firstDate.setText(evaluationMasterShgData.get(position).getFirstEvaluationDate());
                        holder.secondDate.setText(evaluationMasterShgData.get(position).getSecondEvaluationDate());

                        break;
                    case "3":
                        holder.mainDateLayout.setVisibility(View.VISIBLE);
                        holder.firstLayout.setVisibility(View.VISIBLE);
                        holder.firstDate.setVisibility(View.VISIBLE);
                        holder.secondLayout.setVisibility(View.VISIBLE);
                        holder.secondDate.setVisibility(View.VISIBLE);
                        holder.thirdLayout.setVisibility(View.VISIBLE);
                        holder.thirdDate.setVisibility(View.VISIBLE);
                        holder.firstDate.setText(evaluationMasterShgData.get(position).getFirstEvaluationDate());
                        holder.secondDate.setText(evaluationMasterShgData.get(position).getSecondEvaluationDate());
                        holder.thirdDate.setText(evaluationMasterShgData.get(position).getThirdEvaluationDate());
                        break;
                    case "4":
                        holder.mainDateLayout.setVisibility(View.VISIBLE);
                        holder.firstLayout.setVisibility(View.VISIBLE);
                        holder.firstDate.setVisibility(View.VISIBLE);
                        holder.secondLayout.setVisibility(View.VISIBLE);
                        holder.secondDate.setVisibility(View.VISIBLE);
                        holder.thirdLayout.setVisibility(View.VISIBLE);
                        holder.thirdDate.setVisibility(View.VISIBLE);
                        holder.fourthLayout.setVisibility(View.VISIBLE);
                        holder.fourthdate.setVisibility(View.VISIBLE);
                        holder.firstDate.setText(evaluationMasterShgData.get(position).getFirstEvaluationDate());
                        holder.secondDate.setText(evaluationMasterShgData.get(position).getSecondEvaluationDate());
                        holder.thirdDate.setText(evaluationMasterShgData.get(position).getThirdEvaluationDate());
                        holder.fourthdate.setText(evaluationMasterShgData.get(position).getFourthEvaluationDate());
                        break;
                }


                if (evaluationDaysLefy <= 0) {
                    if (evaluationMasterShgData.get(position).getEvaluationdonestatus().equalsIgnoreCase("1")) {//by default this status is 0 not get from server its heandle only locally
                        holder.daysLeft.setText(context.getString(R.string.evaluation_done_msg));
                    } else {
                        if (evMax.compareTo(todayDate) <= 0) {
                            holder.daysLeft.setText(context.getString(R.string.evaluation_ro_evaluation_msg));
                            holder.shgName.setTextColor(context.getResources().getColor(R.color.color_red));
                        } else {
                            holder.shgName.setTextColor(context.getResources().getColor(R.color.color_green));
                            holder.daysLeft.setText(context.getString(R.string.evaluation_start_msg));
                        }
                    }
                } else {
                    holder.daysLeft.setText("" + evaluationDaysLefy + context.getString(R.string.evaluation_days_msg));
                }


    }

        public void setPreviousDate ( int evaluationType){


        }

        public String getShgName (String shgCode){
            String shgName = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder().where(ShgDataDao.Properties.ShgCode.eq(shgCode)).limit(1).unique().getShgName();
            return shgName;
        }

        @Override
        public int getItemCount () {
            return evaluationMasterShgData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView shgName, daysLeft, evaluationType;
            LinearLayout mainDateLayout, firstLayout, secondLayout, thirdLayout, fourthLayout,parentLL;
            TextView firstDate, secondDate, thirdDate, fourthdate,numericDetailsTV;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                shgName = (TextView) itemView.findViewById(R.id.childShgTv);
                daysLeft = (TextView) itemView.findViewById(R.id.childNoofDaysLeft);
                evaluationType = (TextView) itemView.findViewById(R.id.childevaluationTypeTv);

                firstDate = (TextView) itemView.findViewById(R.id.firstDate_Tv);
                secondDate = (TextView) itemView.findViewById(R.id.secondDate_Tv);
                thirdDate = (TextView) itemView.findViewById(R.id.thirdDate_Tv);
                fourthdate = (TextView) itemView.findViewById(R.id.fourthDate_Tv);

                mainDateLayout = (LinearLayout) itemView.findViewById(R.id.evaluationStatusLayout);
                firstLayout = (LinearLayout) itemView.findViewById(R.id.firstEvaluationLayout);
                secondLayout = (LinearLayout) itemView.findViewById(R.id.secodEvaluationLayout);
                thirdLayout = (LinearLayout) itemView.findViewById(R.id.thirdEvaluationLayout);
                fourthLayout = (LinearLayout) itemView.findViewById(R.id.fourthEvaluationLayout);

            }
        }

}
