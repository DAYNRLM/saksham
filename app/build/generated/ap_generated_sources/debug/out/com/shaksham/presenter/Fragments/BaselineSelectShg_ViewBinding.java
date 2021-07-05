// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaselineSelectShg_ViewBinding implements Unbinder {
  private BaselineSelectShg target;

  @UiThread
  public BaselineSelectShg_ViewBinding(BaselineSelectShg target, View source) {
    this.target = target;

    target.shgListRv = Utils.findRequiredViewAsType(source, R.id.basline_shgListRV, "field 'shgListRv'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaselineSelectShg target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.shgListRv = null;
  }
}
