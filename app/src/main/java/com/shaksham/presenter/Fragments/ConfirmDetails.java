package com.shaksham.presenter.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shaksham.R;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.model.database.TrainingLocationInfo;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DateFactory;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfirmDetails extends Fragment implements HomeActivity.OnBackPressedListener {

    private String subTotal, otherParticipants;
    @BindView(R.id.button_save)
    Button bt_Save;
    @BindView(R.id.text_date_of_training)
    TextView tvDateOfTraining;
    @BindView(R.id.text_block)
    TextView tvBlock;

    @BindView(R.id.text_shg_participating)
    TextView tvShgParticipating;
    @BindView(R.id.text_shg_member_participating)
    TextView tvShgMemberParticipating;
    @BindView(R.id.text_other_member_participating)
    TextView tvOtherMemberParticipating;
    @BindView(R.id.text_content_module)
    TextView tvContentModule;
    @BindView(R.id.text_gp)
    TextView tvGp;
    @BindView(R.id.text_village)
    TextView tvVillage;

    private Unbinder unbinder;

    public static ConfirmDetails getInstance() {
        ConfirmDetails confirmDetails = new ConfirmDetails();
        return confirmDetails;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),ConfirmDetails.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.confirm_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvDateOfTraining.setText(DateFactory.getInstance().getTodayDate());
        getDataFromMainModel();
        return view;


    }

    @OnClick(R.id.button_save)
    void photoGpsFrag() {


        DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info_confirmation), getString(R.string.do_you_want_to_save_detail), getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PhotoGps photoGps = PhotoGps.getInstance();

                AppUtility.getInstance().replaceFragment(getFragmentManager(), photoGps, PhotoGps.class.getSimpleName(), true, R.id.fragmentContainer);

            }
        }, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void doBack() {
        AddNumberParticipants addNumberParticipants = AddNumberParticipants.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), addNumberParticipants, AddNumberParticipants.class.getSimpleName(), false, R.id.fragmentContainer);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();

    }

    void getDataFromMainModel() {
        AddTrainingPojo data = AddTrainingPojo.addTrainingPojo;
        tvBlock.setText(data.getBlockName());
        tvGp.setText(data.getGpName());
        tvVillage.setText(data.getVillageName());
        tvShgParticipating.setText(data.getSelectedShgCount());
        subTotal = data.getSubTotal();
        tvShgMemberParticipating.setText(subTotal);
        otherParticipants = data.getOtherParticipant();
        tvOtherMemberParticipating.setText(otherParticipants);
        String smoduleName = " ";
        List<AddTrainingPojo.SelectedModulesList> moduleName = data.getSelectedModulesList();
        for (int i = 0; i < moduleName.size(); i++) {
            smoduleName = smoduleName +""+(i+1)+"."+ moduleName.get(i).getModuleName()+" ";
            System.getProperty("line.separator");
        }
        AppUtility.getInstance().showLog("smoduleName"+smoduleName,ConfirmDetails.class);
      //  smoduleName = smoduleName.replace("\\\n", System.getProperty("line.separator"));
        tvContentModule.setText(""+AppUtility.getInstance().removeCommaFromLast(smoduleName));
    }

}
