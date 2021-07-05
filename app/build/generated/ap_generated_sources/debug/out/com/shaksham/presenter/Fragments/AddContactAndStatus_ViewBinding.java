// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddContactAndStatus_ViewBinding implements Unbinder {
  private AddContactAndStatus target;

  private View view7f080043;

  private View view7f080042;

  @UiThread
  public AddContactAndStatus_ViewBinding(final AddContactAndStatus target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.add_contact_status_next, "field 'addContactStatusNext' and method 'confirmDetailScreen'");
    target.addContactStatusNext = Utils.castView(view, R.id.add_contact_status_next, "field 'addContactStatusNext'", Button.class);
    view7f080043 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.confirmDetailScreen();
      }
    });
    view = Utils.findRequiredView(source, R.id.add_contact_status_back, "field 'addContactStatusback' and method 'addNumberParticipantFragment'");
    target.addContactStatusback = Utils.castView(view, R.id.add_contact_status_back, "field 'addContactStatusback'", Button.class);
    view7f080042 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addNumberParticipantFragment();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddContactAndStatus target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.addContactStatusNext = null;
    target.addContactStatusback = null;

    view7f080043.setOnClickListener(null);
    view7f080043 = null;
    view7f080042.setOnClickListener(null);
    view7f080042 = null;
  }
}
