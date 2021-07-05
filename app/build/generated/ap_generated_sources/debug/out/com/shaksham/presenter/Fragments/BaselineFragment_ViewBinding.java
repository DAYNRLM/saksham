// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
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

public class BaselineFragment_ViewBinding implements Unbinder {
  private BaselineFragment target;

  private View view7f08006e;

  @UiThread
  public BaselineFragment_ViewBinding(final BaselineFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.button_save, "field 'btSave' and method 'dashboardFragment'");
    target.btSave = Utils.castView(view, R.id.button_save, "field 'btSave'", Button.class);
    view7f08006e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.dashboardFragment();
      }
    });
    target.titleTv = Utils.findRequiredViewAsType(source, R.id.evaluation_formTitleTv, "field 'titleTv'", TextView.class);
    target.enteredQuestionRV = Utils.findRequiredViewAsType(source, R.id.evaluation_QuestionRv, "field 'enteredQuestionRV'", RecyclerView.class);
    target.totalMemberTv = Utils.findRequiredViewAsType(source, R.id.evform_totalMemberTv, "field 'totalMemberTv'", TextView.class);
    target.entredMemberTv = Utils.findRequiredViewAsType(source, R.id.evForm_enteredMemberTv, "field 'entredMemberTv'", TextView.class);
    target.shgnameTv = Utils.findRequiredViewAsType(source, R.id.evForm_ShgNameTv, "field 'shgnameTv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaselineFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btSave = null;
    target.titleTv = null;
    target.enteredQuestionRV = null;
    target.totalMemberTv = null;
    target.entredMemberTv = null;
    target.shgnameTv = null;

    view7f08006e.setOnClickListener(null);
    view7f08006e = null;
  }
}
