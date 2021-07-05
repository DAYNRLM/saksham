package com.shaksham.view.adaptors;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.interfaces.TextCallBackListener;
import com.shaksham.model.PojoData.GetQuestionValue;
import com.shaksham.model.database.QuestionInfoDetail;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import java.util.ArrayList;
import java.util.List;

public class EvaluationQuestionAdapter extends RecyclerView.Adapter<EvaluationQuestionAdapter.MyViewHolder> {
    List<QuestionInfoDetail> questionInfoDetails ;
    List<GetQuestionValue> getQuestionValuesDataList;
    Context context;
    String value;

    public EvaluationQuestionAdapter(List<QuestionInfoDetail> questionInfoDetails, Context context) {
        this.questionInfoDetails = questionInfoDetails;
        this.context = context;
        getQuestionValuesDataList = new ArrayList<>();
        for(int i=0;i<questionInfoDetails.size();i++){
            GetQuestionValue getQuestionValue = new GetQuestionValue();
            getQuestionValue.setValue("");
            getQuestionValue.setQuestionId("");
            getQuestionValuesDataList.add(i,getQuestionValue);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLanguageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_child_custom_layout, parent, false);
        return new EvaluationQuestionAdapter.MyViewHolder(myLanguageView,new CustomETlistner());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.questionName.setText(""+(position+1)+":"+questionInfoDetails.get(position).getQuestionName());

        if(questionInfoDetails.get(position).getQuestionTypeId().equalsIgnoreCase("2")){
            holder.answeET.setVisibility(View.GONE);
            holder.radioGroup.setVisibility(View.VISIBLE);
            if (getQuestionValuesDataList.get(position).getValue().equalsIgnoreCase("")) {
                holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        String answerValue = ((RadioButton) holder.itemView.findViewById(checkedId)).getText().toString();
                   /* saveRadioButton(position,holder, String.valueOf(questionInfoDetails.get(position).getQuestionId())
                            ,questionInfoDetails.get(position).getQuestionMainId()
                            ,questionInfoDetails.get(position).getQuestionTypeId());*/
                        AppUtility.getInstance().showLog("RADIO_VALUE" + answerValue, BaslineQuestionAdapter.class);
                        if (answerValue.equalsIgnoreCase("Yes"))
                            answerValue = "111";
                        else answerValue = "000";
                        GetQuestionValue getQuestionValue = new GetQuestionValue();
                        getQuestionValue.setQuestionId(String.valueOf(questionInfoDetails.get(position).getQuestionId()));
                        getQuestionValue.setValue(answerValue);
                        getQuestionValue.setQuestionMainId(questionInfoDetails.get(position).getQuestionMainId());
                        getQuestionValue.setTypeId(questionInfoDetails.get(position).getQuestionTypeId());
                        getQuestionValuesDataList.set(position, getQuestionValue);
                    }
                });

            }
        }else {
            holder.myCustomEditTextListener.updatePosition(position,holder, String.valueOf(questionInfoDetails.get(position).getQuestionId())
                    ,questionInfoDetails.get(position).getQuestionMainId()
                    ,questionInfoDetails.get(position).getQuestionTypeId());
            holder.answeET.setText(getQuestionValuesDataList.get(position).getValue());
        }
      /*  holder.questionName.setText(questionInfoDetails.get(position).getQuestionName());
        holder.myCustomEditTextListener.updatePosition(position,holder, String.valueOf(questionInfoDetails.get(position).getQuestionId())
                ,questionInfoDetails.get(position).getQuestionMainId()
                ,questionInfoDetails.get(position).getQuestionTypeId());
       // holder.myCustomEditTextListener.updatePosition(position,holder, String.valueOf(questionInfoDetails.get(position).getQuestionId()));
        holder.answeET.setText(getQuestionValuesDataList.get(position).getValue());

        if(questionInfoDetails.get(position).getQuestionTypeId().equalsIgnoreCase("2")){
            holder.answeET.setVisibility(View.GONE);
            holder.radioGroup.setVisibility(View.VISIBLE);
            holder.myCustomEditTextListener.saveRadioButton(position,holder, String.valueOf(questionInfoDetails.get(position).getQuestionId())
                    ,questionInfoDetails.get(position).getQuestionMainId()
                    ,questionInfoDetails.get(position).getQuestionTypeId());
        }*/
    }

    @Override
    public int getItemCount() {
        return questionInfoDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionName;
        EditText answeET;
        RadioGroup radioGroup;
        RadioButton answerButton;
        public CustomETlistner myCustomEditTextListener;
        public MyViewHolder(@NonNull View itemView, CustomETlistner myLis) {
            super(itemView);
            questionName = (TextView)itemView.findViewById(R.id.evaluation_ChildQuestionTV);
            answeET = (EditText)itemView.findViewById(R.id.evaluation_ChildEt);
            radioGroup = (RadioGroup)itemView.findViewById(R.id.radioGroup);
            myCustomEditTextListener =myLis;
            answeET.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private  class CustomETlistner implements TextWatcher {
        private int position;
        private String questionId;
        private String mainQuestionId;
        private String questionTypeid;
        TextCallBackListener textCallBackListener;
        MyViewHolder holder;

        public void updatePosition(int position,@NonNull MyViewHolder holder,String questionId,String mainQuestionId,String questionTypeid) {
            this.position = position;
            this.holder = holder;
            this.questionId =questionId;
            this.mainQuestionId = mainQuestionId;
            this.questionTypeid = questionTypeid;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int totalMember = Integer.parseInt(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyTotalMemberForEvaluation(),context));
            AppUtility.getInstance().showLog("string length"+totalMember,EvaluationQuestionAdapter.class);
            int entervedvalue=0;
            value = s.toString().trim();
            AppUtility.getInstance().showLog("string length"+value.length(),EvaluationQuestionAdapter.class);
            if(value.equalsIgnoreCase("")){
                //Toast.makeText(context,"Space Is Not Allowed",Toast.LENGTH_SHORT).show();
                value="";
                GetQuestionValue getQuestionValue = new GetQuestionValue();
                getQuestionValue.setQuestionId(questionId);
                getQuestionValue.setValue(value);
                getQuestionValue.setQuestionMainId(mainQuestionId);
                getQuestionValue.setTypeId(questionTypeid);
                getQuestionValuesDataList.set(position,getQuestionValue);
            }else {
                entervedvalue = Integer.parseInt(value);
                if (entervedvalue > totalMember) {
                    value = "";
                    GetQuestionValue getQuestionValue = new GetQuestionValue();
                    getQuestionValue.setQuestionId(questionId);
                    getQuestionValue.setValue(value);
                    getQuestionValue.setQuestionMainId(mainQuestionId);
                    getQuestionValue.setTypeId(questionTypeid);
                    getQuestionValuesDataList.set(position, getQuestionValue);
                    Toast.makeText(context, context.getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                    holder.answeET.setText("");
                    holder.answeET.clearFocus();
                } else {
                    AppUtility.getInstance().showLog("Quantity is less" + value, BaslineQuestionAdapter.class);
                    GetQuestionValue getQuestionValue = new GetQuestionValue();
                    getQuestionValue.setQuestionId(questionId);
                    getQuestionValue.setValue("" + entervedvalue);
                    getQuestionValue.setQuestionMainId(mainQuestionId);
                    getQuestionValue.setTypeId(questionTypeid);
                    getQuestionValuesDataList.set(position, getQuestionValue);
                }
            }

           /* AppUtility.getInstance().showLog("string length"+value.length(),EvaluationQuestionAdapter.class);
            if(value.equalsIgnoreCase("")){
                //Toast.makeText(context,"Space Is Not Allowed",Toast.LENGTH_SHORT).show();
            }else {
                entervedvalue = Integer.parseInt(value);
                if(entervedvalue>totalMember){
                    Toast.makeText(context,context.getString(R.string.toast_enter_member_msg),Toast.LENGTH_SHORT).show();
                    value="";
                    GetQuestionValue getQuestionValue = new GetQuestionValue();
                    getQuestionValue.setQuestionId(questionId);
                    getQuestionValue.setValue(value);
                    getQuestionValue.setQuestionMainId(mainQuestionId);
                    getQuestionValue.setTypeId(questionTypeid);
                    getQuestionValuesDataList.set(position,getQuestionValue);
                    Toast.makeText(context,context.getString(R.string.toast_error),Toast.LENGTH_SHORT).show();
                    holder.answeET.setText("");
                    holder.answeET.clearFocus();
                    holder.answeET.setText("");
                    holder.answeET.clearFocus();

                }else {
                    GetQuestionValue getQuestionValue = new GetQuestionValue();
                    getQuestionValue.setQuestionId(questionId);
                    AppUtility.getInstance().showLog("questionId"+questionId,EvaluationQuestionAdapter.class);
                    getQuestionValue.setValue(""+entervedvalue);
                    getQuestionValuesDataList.set(position,getQuestionValue);
                }
            }*/
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    }
    public List<GetQuestionValue> getmDataset() {
        return getQuestionValuesDataList;
    }
}
