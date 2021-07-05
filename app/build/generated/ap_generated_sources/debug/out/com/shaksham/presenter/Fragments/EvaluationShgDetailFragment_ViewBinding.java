// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EvaluationShgDetailFragment_ViewBinding implements Unbinder {
  private EvaluationShgDetailFragment target;

  private View view7f0800d5;

  private View view7f0800d4;

  @UiThread
  public EvaluationShgDetailFragment_ViewBinding(final EvaluationShgDetailFragment target,
      View source) {
    this.target = target;

    View view;
    target.evaluationShgLocationRv = Utils.findRequiredViewAsType(source, R.id.shg_detailRV, "field 'evaluationShgLocationRv'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.evaluation_member_nextBtn, "field 'evaluationNext' and method 'onNext'");
    target.evaluationNext = Utils.castView(view, R.id.evaluation_member_nextBtn, "field 'evaluationNext'", Button.class);
    view7f0800d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNext();
      }
    });
    view = Utils.findRequiredView(source, R.id.evaluation_member_backBtn, "field 'evaluationBack' and method 'onBack'");
    target.evaluationBack = Utils.castView(view, R.id.evaluation_member_backBtn, "field 'evaluationBack'", Button.class);
    view7f0800d4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBack();
      }
    });
    target.errorLayout = Utils.findRequiredViewAsType(source, R.id.middleLayout, "field 'errorLayout'", LinearLayout.class);
    target.titletv = Utils.findRequiredViewAsType(source, R.id.titleLayout, "field 'titletv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EvaluationShgDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.evaluationShgLocationRv = null;
    target.evaluationNext = null;
    target.evaluationBack = null;
    target.errorLayout = null;
    target.titletv = null;

    view7f0800d5.setOnClickListener(null);
    view7f0800d5 = null;
    view7f0800d4.setOnClickListener(null);
    view7f0800d4 = null;
  }
}
