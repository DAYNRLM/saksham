// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

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

public class ConfirmDetails_ViewBinding implements Unbinder {
  private ConfirmDetails target;

  private View view7f08006e;

  @UiThread
  public ConfirmDetails_ViewBinding(final ConfirmDetails target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.button_save, "field 'bt_Save' and method 'photoGpsFrag'");
    target.bt_Save = Utils.castView(view, R.id.button_save, "field 'bt_Save'", Button.class);
    view7f08006e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.photoGpsFrag();
      }
    });
    target.tvDateOfTraining = Utils.findRequiredViewAsType(source, R.id.text_date_of_training, "field 'tvDateOfTraining'", TextView.class);
    target.tvBlock = Utils.findRequiredViewAsType(source, R.id.text_block, "field 'tvBlock'", TextView.class);
    target.tvShgParticipating = Utils.findRequiredViewAsType(source, R.id.text_shg_participating, "field 'tvShgParticipating'", TextView.class);
    target.tvShgMemberParticipating = Utils.findRequiredViewAsType(source, R.id.text_shg_member_participating, "field 'tvShgMemberParticipating'", TextView.class);
    target.tvOtherMemberParticipating = Utils.findRequiredViewAsType(source, R.id.text_other_member_participating, "field 'tvOtherMemberParticipating'", TextView.class);
    target.tvContentModule = Utils.findRequiredViewAsType(source, R.id.text_content_module, "field 'tvContentModule'", TextView.class);
    target.tvGp = Utils.findRequiredViewAsType(source, R.id.text_gp, "field 'tvGp'", TextView.class);
    target.tvVillage = Utils.findRequiredViewAsType(source, R.id.text_village, "field 'tvVillage'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ConfirmDetails target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.bt_Save = null;
    target.tvDateOfTraining = null;
    target.tvBlock = null;
    target.tvShgParticipating = null;
    target.tvShgMemberParticipating = null;
    target.tvOtherMemberParticipating = null;
    target.tvContentModule = null;
    target.tvGp = null;
    target.tvVillage = null;

    view7f08006e.setOnClickListener(null);
    view7f08006e = null;
  }
}
