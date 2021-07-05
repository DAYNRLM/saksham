package com.shaksham.interfaces;

import java.util.ArrayList;

public interface PermissionListener {

    void onPermissionsGranted(int requestCode, ArrayList<String> acceptedPermissionList);

    void onPermissionsDenied(int requestCode, ArrayList<String> deniedPermissionList);
}
