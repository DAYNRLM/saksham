package com.shaksham.view.adaptors;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.shaksham.interfaces.TextCallBackListener;
import com.shaksham.model.PojoData.SelectEvaluationShgPojo;
import com.shaksham.model.PojoData.ShgEvaluationMemberPojo;
import com.shaksham.model.database.AddedTrainingShgData;
import com.shaksham.model.database.AddedTrainingShgMemberData;
import com.shaksham.model.database.AddedTrainingShgMemberDataDao;
import com.shaksham.model.database.AddedTrainingShgModuleData;
import com.shaksham.model.database.AddedTrainingShgModuleDataDao;
import com.shaksham.model.database.EvaluationMasterShgData;
import com.shaksham.model.database.EvaluationMasterShgDataDao;
import com.shaksham.model.database.EvaluationMasterTrainingData;
import com.shaksham.model.database.EvaluationMasterTrainingDataDao;
import com.shaksham.model.database.ModuleDataDao;
import com.shaksham.model.database.ShgData;
import com.shaksham.model.database.ShgDataDao;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.presenter.Fragments.EvaluationFormFragment;
import com.shaksham.presenter.Fragments.EvaluationMemberFragment;
import com.shaksham.utils.AppConstant;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShgMemberListAdapter extends RecyclerView.Adapter<ShgMemberListAdapter.MyViewHolder> {
    Context context;
    List<EvaluationMasterShgData> evaluationMasterShgDataList;
    String shgCode;
    EvaluationMemberFragment evaluationMemberFragment;
    Date evaluationDate, todayDate;
    private Dialog dialog;
    EditText enterShg;
    Button ok, cancel;
    int enterShgMemberCount=0;

    public ShgMemberListAdapter(Context context, EvaluationMemberFragment evaluationMemberFragment, List<EvaluationMasterShgData> evaluationMasterShgDataList) {
        this.context = context;
        this.evaluationMemberFragment = evaluationMemberFragment;
        this.evaluationMasterShgDataList = evaluationMasterShgDataList;
    }

    public ShgMemberListAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ShgMemberListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_select_shg_custom_layout, parent, false);
        return new ShgMemberListAdapter.MyViewHolder(ShgMemberListView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        shgCode = evaluationMasterShgDataList.get(position).getShgCode();
        String evdate = evaluationMasterShgDataList.get(position).getEvaluationDate();
        String finalEvaluationdate = DateFactory.getInstance().geteDateFromTimeStamp(evdate);

        AddShgAdeptor addShgAdeptor = new AddShgAdeptor();
        BaslineSelectShgAdaptor baslineSelectShgAdaptor= new BaslineSelectShgAdaptor();
        String moduleNaleList = addShgAdeptor.getModuleList(shgCode,context);
        holder.module.setText(moduleNaleList);

        holder.shgMemberTv.setText(String.valueOf(baslineSelectShgAdaptor.getShgMemberCountFromDb(shgCode)));
        holder.shgName.setText( getShgNameFromLocal(shgCode));
        holder.shgTotalmember.setText(getTrainingCount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shgCode = evaluationMasterShgDataList.get(position).getShgCode();
                String today = DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate().trim());
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    evaluationDate = sdf.parse(finalEvaluationdate);
                    sdf.format(evaluationDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    todayDate = sdf.parse(today);
                    sdf.format(todayDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (todayDate.compareTo(evaluationDate) >= 0) {
                    String totalMember = holder.shgMemberTv.getText().toString().trim();
                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyTotalMemberForEvaluation(),totalMember,context);
                    PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyShgCodeForEvaluation(), shgCode, context);
                    //dialog enter more the 50% of total members
                    int shgMemberCount = Integer.parseInt(totalMember);
                    dialog = DialogFactory.getInstance().showCustomDialog(context, R.layout.enter_shg_member_custom_dialog);
                    ok = (Button) dialog.findViewById(R.id.dialog_nextBtn);
                    cancel = (Button) dialog.findViewById(R.id.dialog_CancelBtn);
                    enterShg = (EditText) dialog.findViewById(R.id.dilog_enterShgEt);
                    dialog.show();
                    dialog.setCancelable(true);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String getCount ="";
                            getCount = (enterShg.getText().toString().trim());
                            if(getCount.equalsIgnoreCase("")||getCount==null){
                                Toast.makeText(context,context.getString(R.string.toast_basline_entervalid_number),Toast.LENGTH_SHORT).show();
                            }else{
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
                                    }else{
                                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeySelectedShgEnterMemberForEvalution(),String.valueOf(enterShgMemberCount),context);
                                        EvaluationFormFragment evaluationFormFragment = new EvaluationFormFragment();
                                        AppUtility.getInstance().replaceFragment(evaluationMemberFragment.getFragmentManager(), evaluationFormFragment, EvaluationFormFragment.class.getSimpleName(), true, R.id.fragmentContainer);
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

                    //*******
                } else {
                    //show dialog for evaluation date
                    DialogFactory.getInstance().showAlertDialog(context, R.drawable.ic_launcher_background, "Evaluation", "Evaluation is done after"+evdate, "ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    },false);
                    Toast.makeText(context, context.getString(R.string.toast_not_elegible), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTrainingCount() {
        List<EvaluationMasterTrainingData> evaluationMasterTrainingData = SplashActivity.getInstance()
                .getDaoSession().getEvaluationMasterTrainingDataDao()
                .queryBuilder().where(EvaluationMasterTrainingDataDao
                        .Properties.ShgCode.eq(shgCode)).build().list();
        int size = evaluationMasterTrainingData.size();
        return String.valueOf(size);
    }

    /*private String getModuleList() {
        String name[];
        String s = "";
        List<AddedTrainingShgModuleData> addedTrainingShgModuleData = SplashActivity.getInstance()
                .getDaoSession()
                .getAddedTrainingShgModuleDataDao()
                .queryBuilder()
                .where(AddedTrainingShgModuleDataDao.Properties.TrainingId.eq(trainingId)
                        , AddedTrainingShgModuleDataDao.Properties.ShgCode.eq(shgCode))
                .build().list();


        name = new String[addedTrainingShgModuleData.size()];

        for (int i = 0; i < addedTrainingShgModuleData.size(); i++) {
            String moduleId = getName(addedTrainingShgModuleData.get(i).getModuleId());
            name[i] = moduleId;
        }
        for (String str : name) {
            s += str + ",";
        }

        return s;

    }*/

   /* private String getName(String moduleId) {

        String moduleName = SplashActivity.getInstance()
                .getDaoSession().getModuleDataDao()
                .queryBuilder()
                .where(ModuleDataDao.Properties.ModuleCode.eq(moduleId))
                .limit(1).unique()
                .getModuleName();


        return moduleName;
    }*/

    public String getShgNameFromLocal(String code) {
        String shgName1="";
        shgName1 = SplashActivity.getInstance().getDaoSession().getShgDataDao().queryBuilder().where(ShgDataDao.Properties.ShgCode.eq(code)).limit(1).unique().getShgName();
        return shgName1;
    }

    /*private int getShgMemberCount() {
        String trainingId = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyTrainingidForEvealuation(), context);
        trainingShgMemberDataList = SplashActivity.getInstance()
                .getDaoSession().getAddedTrainingShgMemberDataDao()
                .queryBuilder().where(AddedTrainingShgMemberDataDao.Properties.ShgCode.eq(shgCode)
                        , AddedTrainingShgMemberDataDao.Properties.TrainingId.eq(trainingId))
                .build().list();

        return trainingShgMemberDataList.size();
    }*/

    @Override
    public int getItemCount() {
        return evaluationMasterShgDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView shgName, shgTotalmember, module,shgMemberTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            shgMemberTv =(TextView)itemView.findViewById(R.id.ev_shgMemberLayout);
            shgName = (TextView) itemView.findViewById(R.id.ev_selectShgTv);
            shgTotalmember = (TextView) itemView.findViewById(R.id.ev_totalMemberTv);
            module = (TextView) itemView.findViewById(R.id.ev_ModuleTv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}