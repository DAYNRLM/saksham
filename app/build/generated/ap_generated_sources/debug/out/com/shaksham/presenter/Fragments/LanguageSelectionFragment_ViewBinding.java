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

public class LanguageSelectionFragment_ViewBinding implements Unbinder {
  private LanguageSelectionFragment target;

  @UiThread
  public LanguageSelectionFragment_ViewBinding(LanguageSelectionFragment target, View source) {
    this.target = target;

    target.changeLanguageRv = Utils.findRequiredViewAsType(source, R.id.changLanguage_Rv, "field 'changeLanguageRv'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LanguageSelectionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.changeLanguageRv = null;
  }
}
