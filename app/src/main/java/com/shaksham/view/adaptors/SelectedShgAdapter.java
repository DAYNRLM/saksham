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
import com.shaksham.model.PojoData.AddShgPojo;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.utils.AppUtility;

import java.util.List;
import java.util.zip.Inflater;

public class SelectedShgAdapter extends RecyclerView.Adapter<SelectedShgAdapter.ViewHoalder> {


    private List<AddShgPojo> selecteModals;
    private Context context;
    private String[] selectedShgData;
    TextView tvSubTotal,tvTotalParticipant;
    EditText getOtherParticipant;



    public SelectedShgAdapter(List<AddShgPojo> selecteModals, Context context,TextView tvSubTotal,EditText getOtherParticipant,TextView tvTotalParticipant  ) {

        this.selecteModals = selecteModals;
        this.context = context;
        this.selectedShgData = new String[selecteModals.size()];
        this.tvSubTotal = tvSubTotal;
        this.getOtherParticipant = getOtherParticipant;
        this.tvTotalParticipant = tvTotalParticipant;

    }
    @NonNull
    @Override
    public SelectedShgAdapter.ViewHoalder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_shg_custom_layout,parent,false);

        return new ViewHoalder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedShgAdapter.ViewHoalder holder, int position) {
        AddShgPojo addShgPojo=selecteModals.get(position);
        holder.tvSelectedShgName.setText(addShgPojo.getShgName());
/*
        holder.myCustomEditTextListener.updatePosition(position,holder);
*/
        holder.etEnterNoOfShg.setText(addShgPojo.getNoOfShg());


    }

    @Override
    public int getItemCount() {
        return selecteModals.size();
    }


    public class ViewHoalder extends RecyclerView.ViewHolder {

     /*   public CustomEtlistner myCustomEditTextListener;*/
        TextView tvSelectedShgName;
        TextView etEnterNoOfShg;

        public ViewHoalder(@NonNull View itemView) {
            super(itemView);

            tvSelectedShgName = itemView.findViewById(R.id.text_selected_shg_name);
            etEnterNoOfShg = itemView.findViewById(R.id.edit_no_of_shg);
         /*   myCustomEditTextListener = mylis;
            etEnterNoOfShg.addTextChangedListener(myCustomEditTextListener);*/
        }
    }

  /*  private class CustomEtlistner implements TextWatcher
    {

private int position;
ViewHoalder hoalder;


        public void updatePosition(int position, @NonNull ViewHoalder hoalder) {
            this.position = position;
            this.hoalder = hoalder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            int totalNumber = Integer.parseInt(selecteModals.get(position).getNoOfShg());
            String str = s.toString();
            if (str.equalsIgnoreCase("")) {
                str = "0";
            } else {
                int enterdNumber = Integer.parseInt(str);
                selectedShgData[position] = str;
                if (enterdNumber > totalNumber) {
                    Toast.makeText(context, "Enter number is Grteater then total Member Number", Toast.LENGTH_SHORT).show();
                    hoalder.etEnterNoOfShg.setText("");
                }else {
                    for(int i=0;i<selectedShgData.length;i++){
                        AppUtility.getInstance().showLog("list:--"+selectedShgData[i],SelectedShgAdapter.class);
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            int sum =0;
            String str = hoalder.etEnterNoOfShg.getText().toString();
            String getParticipant  = getOtherParticipant.getText().toString();
            if(getParticipant.equalsIgnoreCase(""))
                getParticipant="0";
            if (str.equalsIgnoreCase("0")) {
                Toast.makeText(context, "Enter number should be more then zero", Toast.LENGTH_SHORT).show();
                hoalder.etEnterNoOfShg.setText("");
            }else {
                selectedShgData[position] = str;
                AppUtility.getInstance().showLog("edit text get :--"+getParticipant,SelectedShgAdapter.class);
                for(int i=0;i<selectedShgData.length;i++){
                    str = selectedShgData[i];
                    if(str==null||str.equalsIgnoreCase("")){
                        str="0";
                    }
                    sum = sum + Integer.parseInt(str);
                    AppUtility.getInstance().showLog("list in after text changed:--"+selectedShgData[i],SelectedShgAdapter.class);
                    AppUtility.getInstance().showLog("total sum is:--"+sum,SelectedShgAdapter.class);
                }
                tvSubTotal.setText(""+sum);
                sum = sum+Integer.parseInt(getParticipant);
                tvTotalParticipant.setText(""+sum);
            }
        }
    }
public String[] getNoOfShgInfo()
{
    return selectedShgData;
}
*/





}
