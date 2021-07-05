// Generated code from Butter Knife. Do not modify!
package com.shaksham.presenter.Fragments;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.shaksham.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotoGps_ViewBinding implements Unbinder {
  private PhotoGps target;

  private View view7f08006f;

  private View view7f08006d;

  @UiThread
  public PhotoGps_ViewBinding(final PhotoGps target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.button_take_photo, "field 'btTakePhoto' and method 'cameraOn'");
    target.btTakePhoto = Utils.castView(view, R.id.button_take_photo, "field 'btTakePhoto'", Button.class);
    view7f08006f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cameraOn();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_capture_gps, "field 'btCaptureGps' and method 'captureLatLong'");
    target.btCaptureGps = Utils.castView(view, R.id.button_capture_gps, "field 'btCaptureGps'", Button.class);
    view7f08006d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.captureLatLong();
      }
    });
    target.btUploadData = Utils.findRequiredViewAsType(source, R.id.button_upload_data, "field 'btUploadData'", Button.class);
    target.imgPhoto = Utils.findRequiredViewAsType(source, R.id.image_on_take_photo, "field 'imgPhoto'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotoGps target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btTakePhoto = null;
    target.btCaptureGps = null;
    target.btUploadData = null;
    target.imgPhoto = null;

    view7f08006f.setOnClickListener(null);
    view7f08006f = null;
    view7f08006d.setOnClickListener(null);
    view7f08006d = null;
  }
}
