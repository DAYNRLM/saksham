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

public class EvaluationFormFragment_ViewBinding implements Unbinder {
  private EvaluationFormFragment target;

  private View view7f08006e;

  @UiThread
  public EvaluationFormFragment_ViewBinding(final EvaluationFormFragment target, View source) {
    this.target = target;

    View view;
    target.enteredQuestionRV = Utils.findRequiredViewAsType(source, R.id.evaluation_QuestionRv, "field 'enteredQuestionRV'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.button_save, "field 'saveBtn' and method 'SaveDone'");
    target.saveBtn = Utils.castView(view, R.id.button_save, "field 'saveBtn'", Button.class);
    view7f08006e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SaveDone();
      }
    });
    target.memberdetailLayout = Utils.findRequiredViewAsType(source, R.id.header2_linearlayout, "field 'memberdetailLayout'", LinearLayout.class);
    target.shgNameTv = Utils.findRequiredViewAsType(source, R.id.evForm_ShgNameTv, "field 'shgNameTv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EvaluationFormFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.enteredQuestionRV = null;
    target.saveBtn = null;
    target.memberdetailLayout = null;
    target.shgNameTv = null;

    view7f08006e.setOnClickListener(null);
    view7f08006e = null;
  }
}
