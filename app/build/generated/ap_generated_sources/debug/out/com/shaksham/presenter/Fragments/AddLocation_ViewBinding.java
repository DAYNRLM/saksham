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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddLocation_ViewBinding implements Unbinder {
  private AddLocation target;

  private View view7f080048;

  private View view7f080050;

  @UiThread
  public AddLocation_ViewBinding(final AddLocation target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.addlocfr_next_btn, "field 'Loc_next' and method 'addSHGFragmentOnNextClick'");
    target.Loc_next = Utils.castView(view, R.id.addlocfr_next_btn, "field 'Loc_next'", Button.class);
    view7f080048 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addSHGFragmentOnNextClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.back_of_addlocation, "field 'loc_back' and method 'openDashboard'");
    target.loc_back = Utils.castView(view, R.id.back_of_addlocation, "field 'loc_back'", Button.class);
    view7f080050 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openDashboard();
      }
    });
    target.spSelectBlock = Utils.findRequiredViewAsType(source, R.id.spinner_selectblock, "field 'spSelectBlock'", MaterialBetterSpinner.class);
    target.spSelectGp = Utils.findRequiredViewAsType(source, R.id.spinner_select_gp, "field 'spSelectGp'", MaterialBetterSpinner.class);
    target.spSelectVillage = Utils.findRequiredViewAsType(source, R.id.spinner_select_village, "field 'spSelectVillage'", MaterialBetterSpinner.class);
    target.tvDateTraining = Utils.findRequiredViewAsType(source, R.id.text_date_of_training_addlocation, "field 'tvDateTraining'", TextView.class);
    target.noDataFoundLL = Utils.findRequiredViewAsType(source, R.id.noDataFoundLL, "field 'noDataFoundLL'", LinearLayout.class);
    target.baselineshgRv = Utils.findRequiredViewAsType(source, R.id.baseline_doneShg, "field 'baselineshgRv'", RecyclerView.class);
    target.baselineLiner = Utils.findRequiredViewAsType(source, R.id.baseline_doneShglinr, "field 'baselineLiner'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddLocation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Loc_next = null;
    target.loc_back = null;
    target.spSelectBlock = null;
    target.spSelectGp = null;
    target.spSelectVillage = null;
    target.tvDateTraining = null;
    target.noDataFoundLL = null;
    target.baselineshgRv = null;
    target.baselineLiner = null;

    view7f080048.setOnClickListener(null);
    view7f080048 = null;
    view7f080050.setOnClickListener(null);
    view7f080050 = null;
  }
}
