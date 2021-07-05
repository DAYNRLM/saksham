// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseLineFilterShgFragment_ViewBinding implements Unbinder {
  private BaseLineFilterShgFragment target;

  private View view7f080050;

  private View view7f080048;

  private View view7f0801f5;

  @UiThread
  public BaseLineFilterShgFragment_ViewBinding(final BaseLineFilterShgFragment target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.back_of_addlocation, "field 'btnBack' and method 'dashboardFragment'");
    target.btnBack = Utils.castView(view, R.id.back_of_addlocation, "field 'btnBack'", Button.class);
    view7f080050 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.dashboardFragment();
      }
    });
    view = Utils.findRequiredView(source, R.id.addlocfr_next_btn, "field 'btnNext' and method 'selectShgFrg'");
    target.btnNext = Utils.castView(view, R.id.addlocfr_next_btn, "field 'btnNext'", Button.class);
    view7f080048 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectShgFrg();
      }
    });
    target.selectBlockSpinner = Utils.findRequiredViewAsType(source, R.id.spinner_selectblock, "field 'selectBlockSpinner'", MaterialBetterSpinner.class);
    target.selectGpSpinner = Utils.findRequiredViewAsType(source, R.id.spinner_select_gp, "field 'selectGpSpinner'", MaterialBetterSpinner.class);
    target.selectVillageSpinner = Utils.findRequiredViewAsType(source, R.id.spinner_select_village, "field 'selectVillageSpinner'", MaterialBetterSpinner.class);
    target.dateSelectionTv = Utils.findRequiredViewAsType(source, R.id.date_TextView, "field 'dateSelectionTv'", TextView.class);
    view = Utils.findRequiredView(source, R.id.text_date_of_training_addlocation, "field 'dateOfBaseline' and method 'getDateFragment'");
    target.dateOfBaseline = Utils.castView(view, R.id.text_date_of_training_addlocation, "field 'dateOfBaseline'", TextView.class);
    view7f0801f5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.getDateFragment();
      }
    });
    target.baslineRelativeLayout = Utils.findRequiredViewAsType(source, R.id.baslineStatusRelativeLayout, "field 'baslineRelativeLayout'", RelativeLayout.class);
    target.baslineErrorTV = Utils.findRequiredViewAsType(source, R.id.baslineShgErrorTv, "field 'baslineErrorTV'", TextView.class);
    target.baslineShgDetailsLinearLayout = Utils.findRequiredViewAsType(source, R.id.baslineShgDetilsLinearLayout, "field 'baslineShgDetailsLinearLayout'", LinearLayout.class);
    target.shgLocationTv = Utils.findRequiredViewAsType(source, R.id.shgLocationTv, "field 'shgLocationTv'", TextView.class);
    target.baslineTotalShg = Utils.findRequiredViewAsType(source, R.id.basline_totalShgTv, "field 'baslineTotalShg'", TextView.class);
    target.baslineCompletedShg = Utils.findRequiredViewAsType(source, R.id.basline_doneShgTv, "field 'baslineCompletedShg'", TextView.class);
    target.baslineUnCompletShg = Utils.findRequiredViewAsType(source, R.id.basline_pendingShgTV, "field 'baslineUnCompletShg'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseLineFilterShgFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnBack = null;
    target.btnNext = null;
    target.selectBlockSpinner = null;
    target.selectGpSpinner = null;
    target.selectVillageSpinner = null;
    target.dateSelectionTv = null;
    target.dateOfBaseline = null;
    target.baslineRelativeLayout = null;
    target.baslineErrorTV = null;
    target.baslineShgDetailsLinearLayout = null;
    target.shgLocationTv = null;
    target.baslineTotalShg = null;
    target.baslineCompletedShg = null;
    target.baslineUnCompletShg = null;

    view7f080050.setOnClickListener(null);
    view7f080050 = null;
    view7f080048.setOnClickListener(null);
    view7f080048 = null;
    view7f0801f5.setOnClickListener(null);
    view7f0801f5 = null;
  }
}
