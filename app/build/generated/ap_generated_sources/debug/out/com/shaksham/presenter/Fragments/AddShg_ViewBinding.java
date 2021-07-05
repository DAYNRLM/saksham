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

public class AddShg_ViewBinding implements Unbinder {
  private AddShg target;

  private View view7f080046;

  @UiThread
  public AddShg_ViewBinding(final AddShg target, View source) {
    this.target = target;

    View view;
    target.rvAddShg = Utils.findRequiredViewAsType(source, R.id.add_Shg_rcv, "field 'rvAddShg'", RecyclerView.class);
    target.addShgNext = Utils.findRequiredViewAsType(source, R.id.add_shg_next, "field 'addShgNext'", Button.class);
    view = Utils.findRequiredView(source, R.id.add_shg_back, "field 'addShgBack' and method 'addLocationFragment'");
    target.addShgBack = Utils.castView(view, R.id.add_shg_back, "field 'addShgBack'", Button.class);
    view7f080046 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addLocationFragment();
      }
    });
    target.noshgTV = Utils.findRequiredViewAsType(source, R.id.noshgTV, "field 'noshgTV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddShg target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvAddShg = null;
    target.addShgNext = null;
    target.addShgBack = null;
    target.noshgTV = null;

    view7f080046.setOnClickListener(null);
    view7f080046 = null;
  }
}
