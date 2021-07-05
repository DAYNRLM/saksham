// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SelectModuleFragment_ViewBinding implements Unbinder {
  private SelectModuleFragment target;

  private View view7f0801b1;

  @UiThread
  public SelectModuleFragment_ViewBinding(final SelectModuleFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.selectmd_back, "field 'btBack' and method 'addLocationFr'");
    target.btBack = Utils.castView(view, R.id.selectmd_back, "field 'btBack'", Button.class);
    view7f0801b1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addLocationFr();
      }
    });
    target.btNext = Utils.findRequiredViewAsType(source, R.id.selectmd_next_btn, "field 'btNext'", Button.class);
    target.rvSelectModule = Utils.findRequiredViewAsType(source, R.id.select_module_rcv, "field 'rvSelectModule'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectModuleFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btBack = null;
    target.btNext = null;
    target.rvSelectModule = null;

    view7f0801b1.setOnClickListener(null);
    view7f0801b1 = null;
  }
}
