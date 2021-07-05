package com.shaksham.view.adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.AddShgPojo;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.PojoData.SelectModulePojo;
import com.shaksham.model.database.ModuleData;
import com.shaksham.presenter.Fragments.AddShg;
import com.shaksham.utils.AppUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class SelectModuleAdapter extends RecyclerView.Adapter<SelectModuleAdapter.ViewHoalder> {

    List<ModuleData> listOfModules;
    List<AddTrainingPojo.SelectedModulesList > selectedModules=new ArrayList<>();
    Context context;
    Button nextBTN;
    public SelectModuleAdapter(List<ModuleData> listOfModule, Context context, Button nextBTN)
    {
        this.listOfModules=listOfModule;
        this.context=context;
        this.nextBTN=nextBTN;
    }
    @NonNull
    @Override
    public SelectModuleAdapter.ViewHoalder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_module_custom_layout,parent,false);
        return new ViewHoalder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull SelectModuleAdapter.ViewHoalder holder, int position) {
      holder.tvModuleName.setText( listOfModules.get(position).getModuleName());
      holder.cbModuleMark.setTag(listOfModules.get(position));
        if((holder.cbModuleMark).isChecked()){
            holder.cbModuleMark.setChecked(false);
        }
      holder.cbModuleMark.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              if (((CheckBox) v).isChecked()){
                  if (selectedModules.size()<3){
                      AddTrainingPojo.SelectedModulesList selectModulePojo=new AddTrainingPojo.SelectedModulesList();
                      selectModulePojo.setModuleName(listOfModules.get(position).getModuleName());
                      selectModulePojo.setModulecode(listOfModules.get(position).getModuleCode());
                      selectModulePojo.setPosition(String.valueOf(position));
                      selectedModules.add(selectModulePojo);
                  }else {
                      Toast.makeText(context,context.getString(R.string.cant_select_more_than3),Toast.LENGTH_SHORT).show();
                      selectedModules.clear();
                      refreshAdapter(listOfModules);

                  }
              }else {
                  remove(selectedModules,String.valueOf(position));
              }

        /*      CheckBox cb=(CheckBox) v;
              if(count==0)
              {
                  k=position;
              }
              if(k!=position)
              {
                  holder.cbModuleMark.setChecked(listOfModule.get(position).isSelected());
                  return;
              }
              count++;
              if(k==position) {
                  SelectModulePojo contact = (SelectModulePojo) cb.getTag();
                  contact.setSelected(cb.isChecked());
                  listOfModule.get(position).setSelected(cb.isChecked());
                  if (cb.isChecked()) {
                      // AddShgPojo addShgPojo2=new AddShgPojo();
                      selectModulePojo1.setModuleName(listOfModule.get(position).getModuleName());
                     AppUtility.getInstance().showLog("Name of SHg"+listOfModule.get(position).getModuleName(),SelectModuleAdapter.class);
                      selectModulePojo1.setPosition(listOfModule.get(position).getPosition());
                     AppUtility.getInstance().showLog("Position is"+listOfModule.get(position).getPosition(),SelectModuleAdapter.class);
                      selectedModule.add(selectModulePojo1);

                  } else {
                      remove(listOfModule.get(position).getPosition());
                  }


              }*/

          }
      });

        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedModules.size()>0){
                    AppUtility.getInstance().showLog("selectedModules"+selectedModules, SelectModuleAdapter.class);
                    AddTrainingPojo.addTrainingPojo.setSelectedModulesList(selectedModules);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    AddShg addShg= AddShg.getInstance();
                    AppUtility.getInstance().replaceFragment(activity.getSupportFragmentManager(),addShg,AddShg.class.getSimpleName(),true,R.id.fragmentContainer);
                }else {
                    Toast.makeText(context,context.getString(R.string.please_select_atleast_one_module),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void remove(List<AddTrainingPojo.SelectedModulesList > selectedShgMemberDataList,String uncheckedPosition) {

        for (int i = 0; i < selectedShgMemberDataList.size(); i++) {
            AddTrainingPojo.SelectedModulesList   uncheckedModule = selectedShgMemberDataList.get(i);
            if (uncheckedPosition.equalsIgnoreCase(uncheckedModule.getPosition())) {
                AppUtility.getInstance().showLog("removedmember"+uncheckedModule.getModulecode(),MemberListAdapter.class);
                selectedShgMemberDataList.remove(uncheckedModule);
                AppUtility.getInstance().showLog("removedsize"+selectedShgMemberDataList.size(),MemberListAdapter.class);
            }
        }
    }
    private void refreshAdapter(List< ModuleData > listOfModules){
        this.listOfModules = listOfModules;
        notifyDataSetChanged();
    }

/*
    private void remove(String position) {


        int index = 0;
        for(int i=0;i<selectedModule.size();i++){
            String removingPosition = selectedModule.get(i).getPosition();
            if(removingPosition.equalsIgnoreCase(position)){
                index = i;
            }
        }
        SelectModulePojo s=selectedModule.get(index);
        AppUtility.getInstance().showLog("name"+s.getModuleName(),SelectModuleAdapter.class);
        selectedModule.remove(index);
        count=0;


    }
*/

    @Override
    public int getItemCount() {
        return listOfModules.size();
    }

     public  class ViewHoalder extends RecyclerView.ViewHolder{
     public CheckBox cbModuleMark;
     public   TextView tvModuleName;
     public ViewHoalder(@NonNull View itemView) {
     super(itemView);

     cbModuleMark=itemView.findViewById(R.id.checkbox_select_module);
     tvModuleName=itemView.findViewById(R.id.text_module_name);
     }
     }
}
