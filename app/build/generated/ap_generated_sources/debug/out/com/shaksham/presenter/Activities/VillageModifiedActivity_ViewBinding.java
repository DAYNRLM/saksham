// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Activities;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VillageModifiedActivity_ViewBinding implements Unbinder {
  private VillageModifiedActivity target;

  private View view7f080240;

  @UiThread
  public VillageModifiedActivity_ViewBinding(VillageModifiedActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VillageModifiedActivity_ViewBinding(final VillageModifiedActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.villageModifiedSync_BTN, "field 'syncDataConfirmation' and method 'test'");
    target.syncDataConfirmation = Utils.castView(view, R.id.villageModifiedSync_BTN, "field 'syncDataConfirmation'", Button.class);
    view7f080240 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.test();
      }
    });
    target.tollbarTitle = Utils.findRequiredViewAsType(source, R.id.tbTitle, "field 'tollbarTitle'", TextView.class);
    target.unsyncedBaselineTV = Utils.findRequiredViewAsType(source, R.id.unsynced_baselineTVW, "field 'unsyncedBaselineTV'", TextView.class);
    target.unsyncedTrainingTV = Utils.findRequiredViewAsType(source, R.id.unsynced_trainingTVW, "field 'unsyncedTrainingTV'", TextView.class);
    target.unsyncedEvaluationTV = Utils.findRequiredViewAsType(source, R.id.unsynced_evaluationTVW, "field 'unsyncedEvaluationTV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VillageModifiedActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.syncDataConfirmation = null;
    target.tollbarTitle = null;
    target.unsyncedBaselineTV = null;
    target.unsyncedTrainingTV = null;
    target.unsyncedEvaluationTV = null;

    view7f080240.setOnClickListener(null);
    view7f080240 = null;
  }
}
