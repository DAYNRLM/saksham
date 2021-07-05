package com.shaksham.presenter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shaksham.R;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddContactAndStatus extends Fragment {


    @BindView(R.id.add_contact_status_next)
    Button addContactStatusNext;

    @BindView(R.id.add_contact_status_back)
    Button addContactStatusback;

    private Unbinder unbinder;


    public static AddContactAndStatus getInstance() {
        AddContactAndStatus addContactAndStatus = new AddContactAndStatus();

        return addContactAndStatus;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),AddContactAndStatus.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_content_and_status, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.add_contact_status_next)
    void confirmDetailScreen() {
        ConfirmDetails confirmDetails = ConfirmDetails.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), confirmDetails, ConfirmDetails.class.getSimpleName(), false, R.id.fragmentContainer);

    }

    @OnClick(R.id.add_contact_status_back)
    public void addNumberParticipantFragment() {
        AddNumberParticipants addNumberParticipants = AddNumberParticipants.getInstance();
        AppUtility.getInstance().replaceFragment(getFragmentManager(), addNumberParticipants, AddNumberParticipants.class.getSimpleName(), false, R.id.fragmentContainer);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();

    }
}
