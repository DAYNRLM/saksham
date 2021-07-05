package com.shaksham.view.adaptors;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shaksham.R;
import com.shaksham.model.PojoData.AddShgPojo;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.model.database.ShgMemberData;
import com.shaksham.model.database.ShgMemberDataDao;
import com.shaksham.model.database.ShgModuleData;
import com.shaksham.model.database.ShgModuleDataDao;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.presenter.Fragments.AddNumberParticipants;
import com.shaksham.presenter.Fragments.AddShg;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddShgAdeptor extends RecyclerView.Adapter<AddShgAdeptor.ViewHoalder> {
    int count = 0;
    int k;
    List<AddShgPojo> addShgPojo;
     List<AddShgPojo> selectionShg= new ArrayList<>();
    Context context;
    private List<AddTrainingPojo.SelectedShgList> selectedShgListATP= new ArrayList<>();
    private final SparseBooleanArray array = new SparseBooleanArray();
    Button nextBTN;

    public AddShgAdeptor(List<AddShgPojo> addShgPojo, Context context, Button nextBTN) {
        this.addShgPojo = addShgPojo;
        this.context = context;
        this.nextBTN=nextBTN;

    }

    public AddShgAdeptor() {
    }

    @NonNull
    @Override
    public AddShgAdeptor.ViewHoalder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_shg_custom_layout, parent, false);

        return new ViewHoalder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddShgAdeptor.ViewHoalder holder, int position) {


        AddShgPojo addShgPojo1 = addShgPojo.get(position);

       /* holder.cbAddShg.setOnCheckedChangeListener(null);
        holder.cbAddShg.setChecked(addShgPojo1.isSelected());

*/

       String shgCode=addShgPojo.get(position).getShgCode();
       String modudle = getModuleList(shgCode,context);
        holder.tvNameOfShg.setText(addShgPojo.get(position).getShgName());
        holder.module.setText(modudle);
        holder.cbAddShg.setChecked(addShgPojo.get(position).isSelected());
        holder.cbAddShg.setTag(addShgPojo.get(position));
        holder.cbAddShg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                AppUtility.getInstance().showLog("check count" + count, AddShgAdeptor.class);
                AddShgPojo contact = (AddShgPojo) cb.getTag();
                contact.setSelected(cb.isChecked());
                addShgPojo.get(position).setSelected(cb.isChecked());
                if (cb.isChecked()) {
                    addShgPojo1.setShgName(addShgPojo.get(position).getShgName());
                    AppUtility.getInstance().showLog("Name of SHg-----" + addShgPojo.get(position).getShgName(), AddShgAdeptor.class);
                    addShgPojo1.setPosition(addShgPojo.get(position).getPosition());
                    AppUtility.getInstance().showLog("Position is-----" + addShgPojo.get(position).getPosition(), AddShgAdeptor.class);
                    /*selectionShg.add(addShgPojo1);*/

                    AddTrainingPojo.SelectedShgList selectedShgObjectATP=new AddTrainingPojo.SelectedShgList();
                    String shgCode=addShgPojo.get(position).getShgCode();
                    selectedShgObjectATP.setShgCode(shgCode);
                    selectedShgObjectATP.setShgName(addShgPojo.get(position).getShgName());

                    Dialog dialog = DialogFactory.getInstance().showCustomDialog(context, R.layout.select_language_dialog);
                    dialog.setCancelable(false);
                    TextView submitBTN=dialog.findViewById(R.id.selected_languageTV);
                    TextView cancelTV=dialog.findViewById(R.id.cancleTV);
                    RecyclerView recyclerView = dialog.findViewById(R.id.select_languageRV);
                    MemberListAdapter memberListAdapter = new MemberListAdapter(getMembersFromLocal(addShgPojo.get(position).getShgCode()), context,submitBTN,selectedShgListATP,shgCode,selectedShgObjectATP,dialog,selectionShg,addShgPojo1);

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(memberListAdapter);
                    memberListAdapter.notifyDataSetChanged();
                    dialog.show();
                    cancelTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //remove(addShgPojo.get(position).getPosition());
                            if((holder.cbAddShg).isChecked()){
                                holder.cbAddShg.setChecked(false);
                            }
                            dialog.dismiss();
                        }
                    });

                } else {
                    remove(addShgPojo.get(position).getPosition());
                }
            }
        });

        Log.d("TAG", "Adapter name" + addShgPojo1.getShgName());

        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTrainingPojo.addTrainingPojo.setSelectedShgCount(String.valueOf(selectionShg.size()));
                if (selectionShg.size()==0){
                    Toast.makeText(context,context.getString(R.string.atlest_one_shg_should_selected),Toast.LENGTH_SHORT).show();
                }else {
                    if (selectionShg.size() > 6) {
                        Toast.makeText(context, context.getString(R.string.enter_number_of_participants), Toast.LENGTH_LONG).show();
                    } else {
                        Gson gson = new Gson();
                        String finalShgSelectionList = gson.toJson(selectionShg);
                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedShgAddtraining(), finalShgSelectionList, context);
                        AppUtility.getInstance().replaceFragment(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), AddNumberParticipants.getInstance(), AddNumberParticipants.class.getSimpleName(), true, R.id.fragmentContainer);
                    }
                }

            }
        });

    }
    public String getModuleList(String shgCode,Context context) {
        String moduleList = "";
        String langCode =PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLanguageId(),context);
        if(langCode.equalsIgnoreCase("")){
            langCode="0";
        }

        QueryBuilder<ShgModuleData> shgDataQueryBuilder = SplashActivity.getInstance().getDaoSession().getShgModuleDataDao().queryBuilder();
        List<ShgModuleData> shgModuleData = shgDataQueryBuilder.where(ShgModuleDataDao.Properties.ShgCodeforModule.eq(shgCode)
                , shgDataQueryBuilder.and(ShgModuleDataDao.Properties.ShgCodeforModule.eq(shgCode)
                        , ShgModuleDataDao.Properties.LanguageId.eq(langCode))).build().list();
     /*   List<ShgModuleData> shgModuleData = SplashActivity.getInstance()
                .getDaoSession().getShgModuleDataDao()
                .queryBuilder().where(ShgModuleDataDao.Properties.ShgCodeforModule.eq(shgCode)).build().list();*/
        String module[] = new String[shgModuleData.size()];

        for (int i = 0; i < shgModuleData.size(); i++) {
            String moduleName = shgModuleData.get(i).getModuleName();
            String moduleCount = shgModuleData.get(i).getModuleCount();
            module[i] = moduleName + "(" + moduleCount + ")";
        }
        for (int i = 0; i < module.length; i++) {
            moduleList += module[i]+"\n";
        }
        return moduleList;
    }

    private void remove(String position) {


        int index = 0;
        for (int i = 0; i < selectionShg.size(); i++) {
            String removingPosition = selectionShg.get(i).getPosition();
            if (removingPosition.equalsIgnoreCase(position)) {
                index = i;
            }
        }
//        AppUtility.getInstance().showLog("name" + selectionShg.get(index), AddShgAdeptor.class);
        if (selectionShg.size()>0){
            selectionShg.remove(index);
        }
        count = 0;


    }

    @Override
    public int getItemCount() {
        return addShgPojo.size();
    }

    private List<ShgMemberData> getMembersFromLocal(String shgCode){

        SplashActivity.getInstance().getDaoSession().getShgMemberDataDao().detachAll();
        return SplashActivity.getInstance().getDaoSession().getShgMemberDataDao().queryBuilder()
                .where(ShgMemberDataDao.Properties.ShgCode.eq(shgCode)).build().list();
    }


    class ViewHoalder extends RecyclerView.ViewHolder {

        CheckBox cbAddShg;
        TextView tvNameOfShg;
         TextView module;
        public ViewHoalder(@NonNull View itemView) {
            super(itemView);

            cbAddShg = (CheckBox) itemView.findViewById(R.id.checkbox_select_shg_add);
            tvNameOfShg = (TextView) itemView.findViewById(R.id.text_add_shg);
            module=(TextView) itemView.findViewById(R.id.fp);


/*cbAddShg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            cbAddShg.setChecked(true);
            Toast.makeText(context,"info"+tvNameOfShg.getText().toString(),Toast.LENGTH_SHORT).show();
        }else{


        }
    }
});*/
        }
    }
}
