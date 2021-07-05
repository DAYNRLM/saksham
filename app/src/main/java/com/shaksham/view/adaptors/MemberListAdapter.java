package com.shaksham.view.adaptors;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.AddShgPojo;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.PojoData.SelectedShgMemberData;
import com.shaksham.model.database.ShgMemberData;
import com.shaksham.presenter.Fragments.AddLocation;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DialogFactory;

import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.ViewHoalder> {

    List<ShgMemberData> shgMemberDataList;
    Context context;
    private List<SelectedShgMemberData> selectyedMembers=new ArrayList<SelectedShgMemberData>();
    private TextView submitBTN;
    List<AddTrainingPojo.SelectedShgList> selectedShgListATP;
    public List<AddTrainingPojo.SelectedShgList.ShgMemberList> shgMemberListsATP=new ArrayList<>();
    private String shgCode;
    private  AddTrainingPojo.SelectedShgList selectedShgObjectATP;
    private Dialog dialog;
    List<AddShgPojo> addShgPojos;
    AddShgPojo addShgPojoObject;

    public MemberListAdapter(List<ShgMemberData> shgMemberDataList, Context context, TextView submitBTN, List<AddTrainingPojo.SelectedShgList> selectedShgListATP, String shgCode, AddTrainingPojo.SelectedShgList selectedShgObjectATP, Dialog dialog, List<AddShgPojo> addShgPojos, AddShgPojo addShgPojoObject )
    {
        this.shgMemberDataList=shgMemberDataList;
        this.context=context;
        this.submitBTN=submitBTN;
        this.selectedShgListATP=selectedShgListATP;
        this.shgCode=shgCode;
        this.selectedShgObjectATP=selectedShgObjectATP;
        this.dialog=dialog;
        this.addShgPojos=addShgPojos;
        this.addShgPojoObject=addShgPojoObject;
    }

    @NonNull
    @Override
    public MemberListAdapter.ViewHoalder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_language_view_item,parent,false);
        return new ViewHoalder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListAdapter.ViewHoalder holder, int position) {

        holder.nameOfMember.setText(shgMemberDataList.get(position).getShgMemberName());
        holder.checkB.setTag(shgMemberDataList.get(position));
        holder.checkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox=(CheckBox) v;
                if (checkBox.isChecked()){

                    AddTrainingPojo.SelectedShgList.ShgMemberList shgMemberListATP=new AddTrainingPojo.SelectedShgList.ShgMemberList();
                    shgMemberListATP.setMemberCode(shgMemberDataList.get(position).getShgMemberCode());
                    shgMemberListATP.setMemberName(shgMemberDataList.get(position).getShgMemberName());
                    shgMemberListsATP.add(shgMemberListATP);

                    SelectedShgMemberData selectedShgMemberData=new SelectedShgMemberData();
                    selectedShgMemberData.setSelectedPosition(String.valueOf(position));
                    selectedShgMemberData.setSelectedMemberCode(shgMemberDataList.get(position).getShgMemberCode());
                    selectedShgMemberData.setSelectedMemberName(shgMemberDataList.get(position).getShgMemberName());
                    selectyedMembers.add(selectedShgMemberData);
                    AppUtility.getInstance().showLog("membercode"+shgMemberDataList.get(position).getShgMemberCode()+shgMemberDataList.get(position).getShgMemberName(),MemberListAdapter.class);
                    AppUtility.getInstance().showLog("size"+selectyedMembers.size(),MemberListAdapter.class);
                }else {
                    remove(selectyedMembers,position,shgMemberListsATP);
                }
            }
        });

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double shgMembersize=shgMemberDataList.size();
                double halfMeamber=shgMembersize/2;

                int celiHalfMember= (int)Math.ceil(halfMeamber);
                if (shgMemberListsATP.size()<celiHalfMember){
                    //Toast.makeText(context,context.getString(R.string.please_select_atleast_half_member),Toast.LENGTH_SHORT).show();
                    DialogFactory.getInstance().showErrorAlertDialog(context,"", context.getString(R.string.please_select_atleast_half_member), "OK");

                }else {
                    addShgPojoObject.setNoOfShg(String.valueOf(shgMemberListsATP.size()));
                    addShgPojos.add(addShgPojoObject);
                    selectedShgObjectATP.setShgMemberLists(shgMemberListsATP);
                    selectedShgListATP.add(selectedShgObjectATP);
                    AddTrainingPojo.addTrainingPojo.setSelectedShgList(selectedShgListATP);
                    AddTrainingPojo data=AddTrainingPojo.addTrainingPojo;
                    AppUtility.getInstance().showLog("AddTrainingPojodata"+data,MemberListAdapter.class);
                    dialog.dismiss();
                }
       /*  AddTrainingPojo.SelectedShgList selectedShgListATP=new AddTrainingPojo.SelectedShgList();
         selectedShgListATP.setShgMemberLists(shgMemberListsATP);

            getAddTrainingPojo();*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return shgMemberDataList.size();
    }


    public class ViewHoalder extends  RecyclerView.ViewHolder{
        TextView nameOfMember;
        CheckBox checkB;
        public ViewHoalder(@NonNull View itemView) {
            super(itemView);

            nameOfMember=itemView.findViewById(R.id.select_language_item_viewTV);
            checkB=itemView.findViewById(R.id.selected_language_item_viewCB);

        }
    }
    private void remove(List<SelectedShgMemberData> selectedShgMemberDataList,int  uncheckedPosition,List<  AddTrainingPojo.SelectedShgList.ShgMemberList>shgMemberListsATP) {
        this.shgMemberListsATP=shgMemberListsATP;
       // this.shgMemberListsATP.remove(shgMemberObj);
        int k=0;
        for (int i = 0; i < selectedShgMemberDataList.size(); i++) {
            SelectedShgMemberData  uncheckedMember = selectedShgMemberDataList.get(i);
            if (String.valueOf(uncheckedPosition).equalsIgnoreCase(uncheckedMember.getSelectedPosition())) {
                AppUtility.getInstance().showLog("removedmember"+uncheckedMember.getSelectedMemberName(),MemberListAdapter.class);
                k=selectedShgMemberDataList.indexOf(uncheckedMember);
                this.shgMemberListsATP.remove(k);
                selectedShgMemberDataList.remove(uncheckedMember);

//                this.shgMemberListsATP.remove(shgMemberListsATP.get(Integer.parseInt(uncheckedMember.getSelectedPosition())));
                AppUtility.getInstance().showLog("removedsize"+selectedShgMemberDataList.size(),MemberListAdapter.class);
            }
        }
       List<  AddTrainingPojo.SelectedShgList.ShgMemberList> shgMemberListsATP1=this.shgMemberListsATP;
      AppUtility.getInstance().showLog("obj"+shgMemberListsATP1,MemberListAdapter.class);

    }

}
