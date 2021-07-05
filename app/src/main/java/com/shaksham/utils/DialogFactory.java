package com.shaksham.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.shaksham.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DialogFactory {

    public static DialogFactory dialogFactory;
    private androidx.appcompat.app.AlertDialog alertDialog;
    private androidx.appcompat.app.AlertDialog serverAlertDialog;

    public static DialogFactory getInstance() {
        if (dialogFactory == null)
            dialogFactory = new DialogFactory();
        return dialogFactory;
    }


    /**
     * @param context                     Activity/Fragment context
     * @param titleIcon                   Title Icon
     * @param dialogMessage               Message Body of Dialog
     * @param positiveButtonText          Positive Button Text
     * @param dialogPositiveClickListener Positive Button click listener
     * @param negativeButtonText          Negative button text
     * @param dialogNegativeClickListener Negative Button click listner
     * @param isCancellable               Can dialog be canceled by clicking on outside
     * @return Created dialog instance/ object
     */
    public AlertDialog showAlertDialog(Context context, @Nullable int titleIcon, String title, String dialogMessage, String positiveButtonText, DialogInterface.OnClickListener dialogPositiveClickListener, @Nullable String negativeButtonText, @Nullable DialogInterface.OnClickListener dialogNegativeClickListener, boolean isCancellable) {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
        builder.setCancelable(isCancellable);
        builder.setInverseBackgroundForced(true);
        if (title.equals(context.getString(R.string.SERVER_ERROR_TITLE))) {
            builder.setCustomTitle(View.inflate(context, R.layout.server_error_dialog, null));
        } else if (title.equals(context.getString(R.string.NO_INTERNET_TITLE))) {
            builder.setCustomTitle(View.inflate(context, R.layout.no_internet_dialog, null));
        } else if (title.equalsIgnoreCase(context.getString(R.string.app_name))) {
            builder.setTitle(title);
        }/*else if (title.equalsIgnoreCase(context.getString(R.string.)))*/
        builder.setMessage(dialogMessage);
        if (titleIcon != 0)
            builder.setIcon(R.drawable.nrlm);

        builder.setPositiveButton(positiveButtonText, dialogPositiveClickListener);

        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText, dialogNegativeClickListener);
        }

        alert = builder.create();
        alert.show();
        return alert;
    }

    public AlertDialog showAlertDialog(Context context, int titleIcon, String title, String dialogMessage, String positiveButtonText, DialogInterface.OnClickListener dialogPositiveClickListener, String negativeButtonText, boolean isCancellable) {

        return showAlertDialog(context, titleIcon, title, dialogMessage, positiveButtonText, dialogPositiveClickListener, negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }, isCancellable);

    }

    public AlertDialog showAlertDialog(Context ctx, int titleIcon, String title, String dialogMessage, String positiveButtonText, boolean isCancellable) {
        return showAlertDialog(ctx, titleIcon, title, dialogMessage, positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }, null, null, isCancellable);
    }


    public AlertDialog showAlertDialog(Context ctx, int titleIcon, String title, String dialogMessage, String positiveButtonText, DialogInterface.OnClickListener onPositiveClickListener, boolean isCancellable) {
        return showAlertDialog(ctx, 0, title, dialogMessage, positiveButtonText, onPositiveClickListener, null, null, isCancellable);
    }

    /* public void showNoInternetDialog(Context context){
         alertDialog= DialogFactory.getInstance().showAlertDialog(context,0,"No Internet!","Please open your internet connection.", "OK", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
             }
         },true);
         alertDialog.show();

     }*/
    public ProgressDialog showProgressDialog(Context context, boolean setCancelable) {
        ProgressDialog progressDialogss = new ProgressDialog(context);
        progressDialogss.setMessage("Loading...");
        progressDialogss.setCancelable(setCancelable);
        return progressDialogss;
    }

    public void showErrorAlertDialog(Context context, String title, String message, String positiveButtonText) {
        serverAlertDialog = DialogFactory.getInstance().showAlertDialog(context, R.drawable.nrlm, title, message, positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, true);
        serverAlertDialog.show();

    }

    public Dialog showCustomDialog(Context context, int layoutId) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layoutId);
        return dialog;
    }

    public void showServerCridentialDialog(Context context, String title, String massege, String positiveButtonText, String negativeButtonText, @Nullable DialogInterface.OnClickListener dialogNegativeClickListener, boolean isCancleable, boolean finishActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.DialogTheme);
        builder.setTitle(title);
        builder.setMessage(massege);
        builder.setCancelable(isCancleable);
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (finishActivity) {
                    System.exit(0);
                } else dialog.dismiss();
            }
        });
        if (dialogNegativeClickListener != null) {
            builder.setNegativeButton(negativeButtonText, dialogNegativeClickListener);
        }
        builder.show();
    }

}
