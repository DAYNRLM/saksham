package com.shaksham.view.adaptors;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgMemberData;
import com.shaksham.model.database.ShgMemberDataDao;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.presenter.Fragments.BaselineFragment;
import com.shaksham.presenter.Fragments.BaselineSelectShg;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import java.util.List;

public class BaslineSelectShgAdaptor extends RecyclerView.Adapter<BaslineSelectShgAdaptor.MyViewHolder> {
    Context context;
    List<ShgData> baselineSelectShgDataList;
    List<ShgMemberData> shgMemberDataList;
    BaselineSelectShg baselineSelectShg;
    private Dialog dialog;
    EditText enterShg;
    Button ok, cancel;
    int enterShgMemberCount=0;

    public BaslineSelectShgAdaptor(Context context,List<ShgData> baselineSelectShgDataList, BaselineSelectShg baselineSelectShg) {
        this.context = context;
        this.baselineSelectShgDataList = baselineSelectShgDataList;
        this.baselineSelectShg = baselineSelectShg;
    }

    public BaslineSelectShgAdaptor() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLanguageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.basline_select_shg_custom_layout, parent, false);
        return new BaslineSelectShgAdaptor.MyViewHolder(myLanguageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.shgName.setText(baselineSelectShgDataList.get(position).getShgName());
        String shgCode = baselineSelectShgDataList.get(position).getShgCode();
        int shgMemberCount = getShgMemberCountFromDb(shgCode);
        holder.shgTotalMember.setText(""+shgMemberCount);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shgMemberCount = getShgMemberCountFromDb(shgCode);
                dialog = DialogFactory.getInstance().showCustomDialog(context, R.layout.enter_shg_member_custom_dialog);
                ok = (Button) dialog.findViewById(R.id.dialog_nextBtn);
                cancel = (Button) dialog.findViewById(R.id.dialog_CancelBtn);
                enterShg = (EditText) dialog.findViewById(R.id.dilog_enterShgEt);
                dialog.show();
                dialog.setCancelable(true);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // enterShgMemberCount = (enterShg.getText().toString().trim());
                        String getCount ="";
                        getCount = (enterShg.getText().toString().trim());
                        if(getCount.equalsIgnoreCase("")||getCount==null){
                            Toast.makeText(context,context.getString(R.string.toast_basline_entervalid_number),Toast.LENGTH_SHORT).show();
                            AppUtility.getInstance().showLog("enter sahi number",BaslineSelectShgAdaptor.class);
                        }else {
                            enterShgMemberCount = Integer.parseInt(getCount);
                            if(enterShgMemberCount==0){
                                Toast.makeText(context,context.getString(R.string.toast_basline_entervalid_number),Toast.LENGTH_SHORT).show();
                                enterShg.setText("");
                                enterShg.clearFocus();
                            }else {
                                double shgMembersize=shgMemberCount;
                                double halfMeamber=shgMembersize/2;

                                int celiHalfMember= (int)Math.ceil(halfMeamber);
                                if(enterShgMemberCount>shgMemberCount || enterShgMemberCount<celiHalfMember ){
                                    Toast.makeText(context,context.getString(R.string.minimum_half_members_required_for_baseline),Toast.LENGTH_LONG).show();
                                     enterShg.setText("");
                                    enterShg.clearFocus();
                                }else {
                                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedShgEnterMemberForBasline(),String.valueOf(enterShgMemberCount),context);
                                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedShgTotalMembers(),String.valueOf(shgMemberCount),context);
                                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedShgCodeForBasline(),shgCode,context);
                                    BaselineFragment baselineFragment = BaselineFragment.getInstance();
                                    AppUtility.getInstance().replaceFragment(baselineSelectShg.getFragmentManager(), baselineFragment, BaselineFragment.class.getSimpleName(), true, R.id.fragmentContainer);
                                    dialog.dismiss();
                                }
                            }
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public int getShgMemberCountFromDb(String shgCode) {
        shgMemberDataList = SplashActivity.getInstance().getDaoSession()
                .getShgMemberDataDao().queryBuilder()
                .where(ShgMemberDataDao.Properties.ShgCode.eq(shgCode))
                .build()
                .list();
        return shgMemberDataList.size();
    }

    @Override
    public int getItemCount() {
        return baselineSelectShgDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView shgName, shgTotalMember;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shgName = (TextView) itemView.findViewById(R.id.basline_customShgNameTv);
            shgTotalMember = (TextView) itemView.findViewById(R.id.basline_customShgMemberTv);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.childRv);
        }
    }
}
