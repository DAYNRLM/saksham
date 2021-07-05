package com.shaksham.view.adaptors;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.interfaces.TextCallBackListener;
import com.shaksham.model.PojoData.GetQuestionValue;
import com.shaksham.model.PojoData.GetTextValue;
import com.shaksham.model.database.BlockData;
import com.shaksham.model.database.QuestionInfoDetail;
import com.shaksham.model.database.TitleInfoDetail;
import com.shaksham.utils.AppUtility;

import java.util.ArrayList;
import java.util.List;

public class ChildQuestionAdapter extends RecyclerView.Adapter<ChildQuestionAdapter.MyViewHolder> {
    List<QuestionInfoDetail> questionInfoDetails;
    List<TitleInfoDetail> titleInfoDetails;
    Context context;
    private String[] mDataset;
    List<String> strings;
    List<GetTextValue> getTextValues;
    List<GetQuestionValue> getQuestionValues;


    public ChildQuestionAdapter(List<QuestionInfoDetail> questionInfoDetails, Context context, List<TitleInfoDetail> titleInfoDetails) {
        this.questionInfoDetails = questionInfoDetails;
        this.context = context;
        this.titleInfoDetails=titleInfoDetails;
        this.mDataset = new String[questionInfoDetails.size()];
        strings = new ArrayList<>(questionInfoDetails.size());
        getTextValues = new ArrayList<>(titleInfoDetails.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myChildView= LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_child_custom_layout,parent,false);
        return new ChildQuestionAdapter.MyViewHolder(myChildView,new CustomETlistner()) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.questionTv.setText(questionInfoDetails.get(position).getQuestionName());
        holder.myCustomListner.updatePosition(position,holder);
        holder.answerET.setText(mDataset[position]);
        holder.answerET.setText(strings.get(position));
        getQuestionValues = new ArrayList<>(questionInfoDetails.size());
        AppUtility.getInstance().showLog("question detail size"+questionInfoDetails.size(),ChildQuestionAdapter.class);
    }

    @Override
    public int getItemCount() {
        return questionInfoDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionTv;
        EditText  answerET;
       public CustomETlistner myCustomListner;

        public MyViewHolder(@NonNull View itemView,CustomETlistner myListner) {
            super(itemView);
            questionTv = (TextView)itemView.findViewById(R.id.evaluation_ChildQuestionTV);
            answerET = (EditText)itemView.findViewById(R.id.evaluation_ChildEt);
            myCustomListner =myListner;
            answerET.addTextChangedListener(myCustomListner);

        }
    }

    private  class CustomETlistner implements TextWatcher {
        private int position;
        TextCallBackListener textCallBackListener;
        MyViewHolder myViewHolder;

        public void updatePosition(int position,@NonNull MyViewHolder holder) {
            this.position = position;
            this.myViewHolder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mDataset[position] = s.toString();
            strings.add(position,s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public String[] getmDataset() {
        return mDataset;
    }
}
