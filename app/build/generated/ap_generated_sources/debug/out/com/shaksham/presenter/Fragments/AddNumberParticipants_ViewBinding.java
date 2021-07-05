// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddNumberParticipants_ViewBinding implements Unbinder {
  private AddNumberParticipants target;

  private View view7f080045;

  private View view7f080044;

  @UiThread
  public AddNumberParticipants_ViewBinding(final AddNumberParticipants target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.add_number_participent_next, "field 'add_numberparticipantnext' and method 'addContactAndStatusScreen'");
    target.add_numberparticipantnext = Utils.castView(view, R.id.add_number_participent_next, "field 'add_numberparticipantnext'", Button.class);
    view7f080045 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addContactAndStatusScreen();
      }
    });
    view = Utils.findRequiredView(source, R.id.add_number_participant_back, "field 'addNumberPartiback' and method 'addShgScreen'");
    target.addNumberPartiback = Utils.castView(view, R.id.add_number_participant_back, "field 'addNumberPartiback'", Button.class);
    view7f080044 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addShgScreen();
      }
    });
    target.tvSubTotal = Utils.findRequiredViewAsType(source, R.id.text_sub_total, "field 'tvSubTotal'", TextView.class);
    target.etOtherParticipent = Utils.findRequiredViewAsType(source, R.id.edittext_other_participent, "field 'etOtherParticipent'", EditText.class);
    target.tvTotalParticipent = Utils.findRequiredViewAsType(source, R.id.text_total_participant, "field 'tvTotalParticipent'", TextView.class);
    target.recyclerview_selected_shg = Utils.findRequiredViewAsType(source, R.id.recyclerview_selected_shg, "field 'recyclerview_selected_shg'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddNumberParticipants target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.add_numberparticipantnext = null;
    target.addNumberPartiback = null;
    target.tvSubTotal = null;
    target.etOtherParticipent = null;
    target.tvTotalParticipent = null;
    target.recyclerview_selected_shg = null;

    view7f080045.setOnClickListener(null);
    view7f080045 = null;
    view7f080044.setOnClickListener(null);
    view7f080044 = null;
  }
}
