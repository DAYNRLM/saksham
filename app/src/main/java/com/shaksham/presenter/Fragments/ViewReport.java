package com.shaksham.presenter.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.shaksham.R;
import com.shaksham.model.database.ViewReportData;
import com.shaksham.model.database.ViewReportDataDao;
import com.shaksham.model.database.ViewReportMonthDataDao;
import com.shaksham.model.database.ViewReportTrainingData;
import com.shaksham.model.database.ViewReportTrainingDataDao;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.presenter.Activities.SplashActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;
import com.shaksham.view.adaptors.ReportListAdaptor;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ViewReport extends Fragment implements HomeActivity.OnBackPressedListener {

    private MaterialBetterSpinner selectMonthMBS;
    private RecyclerView reportListRV;
    public TextView shgsParticipated, shgsMembersParticipated, otherMembersParticipated, noOfDays, noOfShg, baseLineDoneTV, assesmentDoneTV, noDatafound;
    private Button goToHomeBTN;
    List<ViewReportTrainingData> viewReportTrainingData;

    public static ViewReport newInstance() {
        ViewReport fragment = new ViewReport();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.title_viewreport));
        AppUtility.getInstance().showLog("loginStatus"+ PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginSessionStatus(), getContext()),ViewReport.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_report, container, false);
        selectMonthMBS = (MaterialBetterSpinner) view.findViewById(R.id.select_monthMBS);
        reportListRV = (RecyclerView) view.findViewById(R.id.report_listRV);
        shgsParticipated = (TextView) view.findViewById(R.id.no_of_shg_participatedTV);
        shgsMembersParticipated = (TextView) view.findViewById(R.id.no_of_shg_member_participatedTV);
        otherMembersParticipated = (TextView) view.findViewById(R.id.other_member_participatedTV);

        noOfDays = (TextView) view.findViewById(R.id.no_of_daysTV);
        noOfShg = (TextView) view.findViewById(R.id.no_of_shgsTV);
        baseLineDoneTV = (TextView) view.findViewById(R.id.baseline_doneTV);
        assesmentDoneTV = (TextView) view.findViewById(R.id.assesment_doneTV);

        goToHomeBTN = (Button) view.findViewById(R.id.goto_homeBTN);
        noDatafound = view.findViewById(R.id.nodata_found);
        noDatafound.setVisibility(View.VISIBLE);
        reportListRV.setVisibility(View.GONE);
        setSpinnerView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goToHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setSpinnerView(View view) {
        ArrayAdapter<String> monthListAdaptor = new ArrayAdapter<String>(getActivity(), R.layout.monthlist_spinnerview, AppUtility.getInstance().getMonthList());
        selectMonthMBS.setAdapter(monthListAdaptor);
        monthListAdaptor.notifyDataSetChanged();

        selectMonthMBS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemFromMSB = selectMonthMBS.getText().toString().trim();
                int selectedMonth = position + 1;
                AppUtility.getInstance().showLog("selectedItemFromMSB=" + selectedItemFromMSB + "position=" + position + "selectedMonth" + selectedMonth, ViewReport.class);
                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);

                AppUtility.getInstance().showLog("year" + year+selectedMonth, ViewReport.class);
                bindReportdata(String.valueOf(selectedMonth), String.valueOf(year));
                if (viewReportTrainingData.size() == 0) {
                    reportListRV.setVisibility(View.GONE);
                    noDatafound.setText(getString(R.string.toast_nodata_found));
                    noDatafound.setVisibility(View.VISIBLE);
                    noOfDays.setText("");
                    noOfShg.setText("");
                    baseLineDoneTV.setText("");
                    assesmentDoneTV.setText("");
                    shgsParticipated.setText("");
                    shgsMembersParticipated.setText("");
                    otherMembersParticipated.setText("");
                    Toast.makeText(getContext(), "Data Not Found!!!", Toast.LENGTH_SHORT).show();
                } else {
                    noDatafound.setVisibility(View.GONE);
                    reportListRV.setVisibility(View.VISIBLE);
                    showData();
                    setTotal(selectedMonth);
                }

            }
        });
    }

    private void showData() {
        ReportListAdaptor reportListAdaptor = new ReportListAdaptor(getContext(), viewReportTrainingData);
        reportListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        reportListRV.setAdapter(reportListAdaptor);
        reportListAdaptor.notifyDataSetChanged();
    }

    private void bindReportdata(String selectedMonth, String year) {
        SplashActivity.getInstance()
                .getDaoSession().getViewReportTrainingDataDao().detachAll();
        QueryBuilder<ViewReportTrainingData> viewReportTrainingDataQueryBuilder = SplashActivity.getInstance()
                .getDaoSession().getViewReportTrainingDataDao().queryBuilder();
        viewReportTrainingData = viewReportTrainingDataQueryBuilder.where(ViewReportTrainingDataDao.Properties.MonthCode.eq(selectedMonth)
                , viewReportTrainingDataQueryBuilder.and(ViewReportTrainingDataDao.Properties.MonthCode.eq(selectedMonth)
                        , ViewReportTrainingDataDao.Properties.Year.eq(year)))
                .build().list();
    }

    private void setTotal(int month) {
        int totalSHGs = 0;
        int totalMembers = 0;
        int totalOthermembers = 0;
        int shgUniquelyDone = 0;
        int baselineDone = 0;
        int evaluationDone = 0;
        for (ViewReportTrainingData viewReportTrainingData : viewReportTrainingData) {
            totalSHGs += Integer.parseInt(viewReportTrainingData.getShgParticipant());
            totalMembers += Integer.parseInt(viewReportTrainingData.getMemberParticipant());
            totalOthermembers += Integer.parseInt(viewReportTrainingData.getOtherParticipant());
        }
        shgsParticipated.setText("SHGs:" + totalSHGs);
        shgsMembersParticipated.setText("Members:" + totalMembers);
        otherMembersParticipated.setText("Other Members:" + totalOthermembers);

        noOfDays.setText("" + SplashActivity.getInstance().getDaoSession()
                .getViewReportMonthDataDao().queryBuilder().where(ViewReportMonthDataDao.Properties.MonthCode.eq(month))
                .limit(1).build().unique().getWorkingDays());

          
        SplashActivity.getInstance().getDaoSession().getViewReportDataDao().detachAll();
        List<ViewReportData> viewReportDataList = SplashActivity.getInstance().getDaoSession().getViewReportDataDao().queryBuilder().where(ViewReportDataDao.Properties.MonthCode.eq(String.valueOf(month))).build().list();

        if (!viewReportDataList.get(0).getShgUniquelyDone().equalsIgnoreCase(""))
            shgUniquelyDone = Integer.parseInt(viewReportDataList.get(0).getShgUniquelyDone());
        if (!viewReportDataList.get(0).getBaselineDone().equalsIgnoreCase(""))
            baselineDone = Integer.parseInt(viewReportDataList.get(0).getBaselineDone());
        if (!viewReportDataList.get(0).getEvaluationDone().equalsIgnoreCase(""))
            evaluationDone = Integer.parseInt(viewReportDataList.get(0).getEvaluationDone());

        noOfShg.setText("" + shgUniquelyDone);
        baseLineDoneTV.setText("" + baselineDone);
        assesmentDoneTV.setText("" + evaluationDone);
    }


    @Override
    public void doBack() {

        AppUtility.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);

    }
}
