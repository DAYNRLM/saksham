// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EvaluationFragment_ViewBinding implements Unbinder {
  private EvaluationFragment target;

  private View view7f0801b6;

  @UiThread
  public EvaluationFragment_ViewBinding(final EvaluationFragment target, View source) {
    this.target = target;

    View view;
    target.evaluationShgRv = Utils.findRequiredViewAsType(source, R.id.evealuation_shgSelectRecyclerView, "field 'evaluationShgRv'", RecyclerView.class);
    target.dateSelectedtv = Utils.findRequiredViewAsType(source, R.id.evaluation_dateSelectedTv, "field 'dateSelectedtv'", TextView.class);
    target.selectBlockSpinner = Utils.findRequiredViewAsType(source, R.id.evaluation_selectBlockSpinner, "field 'selectBlockSpinner'", MaterialBetterSpinner.class);
    target.selectGpSpinner = Utils.findRequiredViewAsType(source, R.id.evaluation_selectGpSpinner, "field 'selectGpSpinner'", MaterialBetterSpinner.class);
    target.selectVillageSpinner = Utils.findRequiredViewAsType(source, R.id.evaluation_SelectVillageSpinner, "field 'selectVillageSpinner'", MaterialBetterSpinner.class);
    target.trainingErrorRelativelayout = Utils.findRequiredViewAsType(source, R.id.evaluationTrainingRelativeLayout, "field 'trainingErrorRelativelayout'", RelativeLayout.class);
    target.trainingListLinearLayout = Utils.findRequiredViewAsType(source, R.id.evaluation_trainingListLL, "field 'trainingListLinearLayout'", LinearLayout.class);
    target.shgErrorviewTv = Utils.findRequiredViewAsType(source, R.id.shgEvLocationTv, "field 'shgErrorviewTv'", TextView.class);
    target.listTrainingTv = Utils.findRequiredViewAsType(source, R.id.listTrainingTv, "field 'listTrainingTv'", TextView.class);
    view = Utils.findRequiredView(source, R.id.shgEvaluation_nextBtn, "field 'nextBtn' and method 'nextBtn'");
    target.nextBtn = Utils.castView(view, R.id.shgEvaluation_nextBtn, "field 'nextBtn'", Button.class);
    view7f0801b6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.nextBtn();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EvaluationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.evaluationShgRv = null;
    target.dateSelectedtv = null;
    target.selectBlockSpinner = null;
    target.selectGpSpinner = null;
    target.selectVillageSpinner = null;
    target.trainingErrorRelativelayout = null;
    target.trainingListLinearLayout = null;
    target.shgErrorviewTv = null;
    target.listTrainingTv = null;
    target.nextBtn = null;

    view7f0801b6.setOnClickListener(null);
    view7f0801b6 = null;
  }
}
