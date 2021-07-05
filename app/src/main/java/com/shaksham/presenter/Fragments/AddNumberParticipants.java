package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shaksham.R;
import com.shaksham.model.PojoData.AddShgPojo;
import com.shaksham.model.PojoData.AddTrainingPojo;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.SelectedShgAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddNumberParticipants extends Fragment implements HomeActivity.OnBackPressedListener {

    private Unbinder unbinder;
    private  int subTotal=0;
    TextView textView;

    @BindView(R.id.add_number_participent_next)
    Button add_numberparticipantnext;

    @BindView(R.id.add_number_participant_back)
    Button addNumberPartiback;

    @BindView(R.id.text_sub_total)
    TextView tvSubTotal;

    @BindView(R.id.edittext_other_participent)
    EditText etOtherParticipent;

    @BindView(R.id.text_total_participant)
    TextView tvTotalParticipent;

    @BindView(R.id.recyclerview_selected_shg)
    RecyclerView recyclerview_selected_shg;
    String otherParticipant="";
    public static AddNumberParticipants getInstance() {
        AddNumberParticipants addNumberParticipants = new AddNumberParticipants();
        return addNumberParticipants;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),AddNumberParticipants.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_number_participants, container, false);
        unbinder = ButterKnife.bind(this, view);
        showData();
        clickListner();
        return view;
    }

    private void clickListner() {
        etOtherParticipent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                otherParticipant = etOtherParticipent.getText().toString();
                String totalSHGMember = tvSubTotal.getText().toString();

                if ((otherParticipant != null) && !(otherParticipant.equalsIgnoreCase("")))
                    AddTrainingPojo.addTrainingPojo.setOtherParticipant(otherParticipant);


                if (totalSHGMember == null && totalSHGMember.equalsIgnoreCase("")) {
                    totalSHGMember = "0";
                } else {
                    if (otherParticipant.equalsIgnoreCase("")) {
                      String   otherParticipantl = "0";
                        int sum = 0;

                        sum = sum + Integer.parseInt(otherParticipantl) + Integer.parseInt(totalSHGMember);
                        tvTotalParticipent.setText("" + sum);
                        Toast.makeText(getContext(),getString(R.string.please_enter_other_participant),Toast.LENGTH_SHORT).show();
                        return;


                    } else {
                        int sum = 0;
                      /*  int otherValue = 99-subTotal;
                        if(otherValue<0){
                            Toast.makeText(getContext(),"Yor can  not enter member more then 99.",Toast.LENGTH_SHORT).show();
                        }else if(Integer.parseInt(otherParticipant)>otherValue) {
                            etOtherParticipent.setError("Can not enter more than"+otherValue);
                        }else {*/
                            sum = sum + Integer.parseInt(otherParticipant) + Integer.parseInt(totalSHGMember);
                            tvTotalParticipent.setText("" + sum);
                            String totalParticipant = tvTotalParticipent.getText().toString().trim();

                            if ((totalParticipant != null) && !(totalParticipant.equalsIgnoreCase("")))
                                AddTrainingPojo.addTrainingPojo.setTotalMembers(totalParticipant);
                      //  }
                    }
                }
            }
        });
    }

    private void showData() {
        List<AddShgPojo> slectedShg = new ArrayList<>();
        Gson gson = new Gson();
        String stringOfSelectedShg = PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeySelectedShgAddtraining(), getContext());
        Type type = new TypeToken<List<AddShgPojo>>() {
        }.getType();
        slectedShg = gson.fromJson(stringOfSelectedShg, type);
        SelectedShgAdapter selectedShgAdapter = new SelectedShgAdapter(slectedShg, getContext(), tvSubTotal, etOtherParticipent, tvTotalParticipent);
        recyclerview_selected_shg.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_selected_shg.setAdapter(selectedShgAdapter);
        for (int i=0;i<slectedShg.size();i++){
            subTotal(slectedShg.get(i).getNoOfShg());
        }
        tvSubTotal.setText(""+subTotal);
        AddTrainingPojo.addTrainingPojo.setSubTotal(String.valueOf(subTotal));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.add_number_participent_next)
    void addContactAndStatusScreen() {


        if (otherParticipant.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), getString(R.string.please_enter_other_participant), Toast.LENGTH_SHORT).show();

            return;
        }
        ConfirmDetails confirmDetails = ConfirmDetails.getInstance();

        AppUtility.getInstance().replaceFragment(getFragmentManager(), confirmDetails, ConfirmDetails.class.getSimpleName(), true, R.id.fragmentContainer);
    }

    @OnClick(R.id.add_number_participant_back)
    void addShgScreen() {

        AddShg addShg = AddShg.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), addShg, AddShg.class.getSimpleName(), false, R.id.fragmentContainer);

    }

    @Override
    public void doBack() {
        AddShg addShg = AddShg.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), addShg, AddShg.class.getSimpleName(), false, R.id.fragmentContainer);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    private void subTotal(String noOfSelectedMembers){
        int members=Integer.parseInt(noOfSelectedMembers);
        subTotal +=members;
    }
}