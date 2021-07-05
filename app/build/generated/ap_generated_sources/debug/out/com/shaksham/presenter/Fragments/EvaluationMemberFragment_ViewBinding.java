// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EvaluationMemberFragment_ViewBinding implements Unbinder {
  private EvaluationMemberFragment target;

  @UiThread
  public EvaluationMemberFragment_ViewBinding(EvaluationMemberFragment target, View source) {
    this.target = target;

    target.enteredMemberDetailRv = Utils.findRequiredViewAsType(source, R.id.evealuation_enterdMemberRv, "field 'enteredMemberDetailRv'", RecyclerView.class);
    target.msgTv = Utils.findRequiredViewAsType(source, R.id.evaluationDoneMessg_tv, "field 'msgTv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EvaluationMemberFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.enteredMemberDetailRv = null;
    target.msgTv = null;
  }
}
