package com.shaksham.view.adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.QuestionInfoDetail;
import com.shaksham.model.database.TitleInfoDetail;
import com.shaksham.presenter.Fragments.BaselineFragment;
import com.shaksham.utils.AppUtility;

import java.util.List;

public class ParentQuestionAdapter extends RecyclerView.Adapter<ParentQuestionAdapter.MyViewHolder> {
    List<TitleInfoDetail> titleInfoDetails;
    List<QuestionInfoDetail> questionInfoDetails;
    Context context;
    ChildQuestionAdapter childAdapter;

    private String[] mDataset;

    public ParentQuestionAdapter(List<TitleInfoDetail> titleInfoDetails, Context context ) {
        this.titleInfoDetails = titleInfoDetails;
        this.context = context;

    }

    @NonNull
    @Override
    public ParentQuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myParentQuestionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_question_custom_layout, parent, false);
        return new ParentQuestionAdapter.MyViewHolder(myParentQuestionView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentQuestionAdapter.MyViewHolder holder, int position) {

        holder.titelTv.setText(titleInfoDetails.get(position).getTitleName());
        questionInfoDetails = titleInfoDetails.get(position).getQuestionDataList();
        AppUtility.getInstance().showLog("child pojo size" + questionInfoDetails.size(), ParentQuestionAdapter.class);
        childAdapter = new ChildQuestionAdapter(questionInfoDetails, context,titleInfoDetails);
        holder.questionRv.setLayoutManager(new LinearLayoutManager(context));
        holder.questionRv.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return titleInfoDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titelTv;
        RecyclerView questionRv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titelTv = (TextView) itemView.findViewById(R.id.titleTV);
            questionRv = (RecyclerView) itemView.findViewById(R.id.childQuestionRV);
        }
    }

    public String[] getmDataset() {
        String str[] = childAdapter.getmDataset();
        for(int i=0;i<str.length;i++){
            AppUtility.getInstance().showLog("edit text value--"+str[i], BaselineFragment.class);
        }
        return str;
    }
}
