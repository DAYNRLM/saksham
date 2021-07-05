// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DashBoardFragment_ViewBinding implements Unbinder {
  private DashBoardFragment target;

  private View view7f080065;

  private View view7f080069;

  private View view7f080067;

  private View view7f080066;

  private View view7f0801dc;

  private View view7f0801db;

  @UiThread
  public DashBoardFragment_ViewBinding(final DashBoardFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnAddTraining, "field 'addModuleTraining' and method 'addLocationFr'");
    target.addModuleTraining = Utils.castView(view, R.id.btnAddTraining, "field 'addModuleTraining'", CardView.class);
    view7f080065 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addLocationFr();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnReports, "field 'moduleReports' and method 'onClickReport'");
    target.moduleReports = Utils.castView(view, R.id.btnReports, "field 'moduleReports'", CardView.class);
    view7f080069 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickReport();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnEvaluation, "field 'moduleEvaluation' and method 'saveClicked'");
    target.moduleEvaluation = Utils.castView(view, R.id.btnEvaluation, "field 'moduleEvaluation'", CardView.class);
    view7f080067 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.saveClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnBaseline, "field 'btBaseline' and method 'addlocationFr'");
    target.btBaseline = Utils.castView(view, R.id.btnBaseline, "field 'btBaseline'", CardView.class);
    view7f080066 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addlocationFr();
      }
    });
    target.unsyncedBaselineTV = Utils.findRequiredViewAsType(source, R.id.unsynced_baselineTV, "field 'unsyncedBaselineTV'", TextView.class);
    target.unsyncedTrainingTV = Utils.findRequiredViewAsType(source, R.id.unsynced_trainingTV, "field 'unsyncedTrainingTV'", TextView.class);
    target.unsyncedEvaluationTV = Utils.findRequiredViewAsType(source, R.id.unsynced_evaluationTV, "field 'unsyncedEvaluationTV'", TextView.class);
    view = Utils.findRequiredView(source, R.id.sync_dataTV, "field 'syncDataBTN' and method 'syncData'");
    target.syncDataBTN = Utils.castView(view, R.id.sync_dataTV, "field 'syncDataBTN'", TextView.class);
    view7f0801dc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.syncData();
      }
    });
    target.pendingSHGsTv = Utils.findRequiredViewAsType(source, R.id.dash_pendingEvaluatingShgTv, "field 'pendingSHGsTv'", TextView.class);
    target.showDetailTv = Utils.findRequiredViewAsType(source, R.id.dashboard_showDetailtv, "field 'showDetailTv'", TextView.class);
    target.pendingTrainingLayout = Utils.findRequiredViewAsType(source, R.id.trainingPendingLayout, "field 'pendingTrainingLayout'", LinearLayout.class);
    target.notificationRecyclerview = Utils.findRequiredViewAsType(source, R.id.notification_rv, "field 'notificationRecyclerview'", RecyclerView.class);
    target.notificationLL = Utils.findRequiredViewAsType(source, R.id.notificationLL, "field 'notificationLL'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.syncTest, "field 'test' and method 'test'");
    target.test = Utils.castView(view, R.id.syncTest, "field 'test'", Button.class);
    view7f0801db = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.test();
      }
    });
    target.baslineDoneTv = Utils.findRequiredViewAsType(source, R.id.baslineDone_tv, "field 'baslineDoneTv'", TextView.class);
    target.trainingDoneTv = Utils.findRequiredViewAsType(source, R.id.trainingDoneTv, "field 'trainingDoneTv'", TextView.class);
    target.evaluationDoneTV = Utils.findRequiredViewAsType(source, R.id.evaluationDone_tv, "field 'evaluationDoneTV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DashBoardFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.addModuleTraining = null;
    target.moduleReports = null;
    target.moduleEvaluation = null;
    target.btBaseline = null;
    target.unsyncedBaselineTV = null;
    target.unsyncedTrainingTV = null;
    target.unsyncedEvaluationTV = null;
    target.syncDataBTN = null;
    target.pendingSHGsTv = null;
    target.showDetailTv = null;
    target.pendingTrainingLayout = null;
    target.notificationRecyclerview = null;
    target.notificationLL = null;
    target.test = null;
    target.baslineDoneTv = null;
    target.trainingDoneTv = null;
    target.evaluationDoneTV = null;

    view7f080065.setOnClickListener(null);
    view7f080065 = null;
    view7f080069.setOnClickListener(null);
    view7f080069 = null;
    view7f080067.setOnClickListener(null);
    view7f080067 = null;
    view7f080066.setOnClickListener(null);
    view7f080066 = null;
    view7f0801dc.setOnClickListener(null);
    view7f0801dc = null;
    view7f0801db.setOnClickListener(null);
    view7f0801db = null;
  }
}
