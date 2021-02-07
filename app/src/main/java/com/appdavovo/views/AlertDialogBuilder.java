package com.appdavovo.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.appdavovo.R;

public abstract class AlertDialogBuilder {

    public static AlertDialog buildOkDialog(String title, String message, Context context) {
        String buttonTitle = context.getResources().getString(R.string.ok_button);
        AlertDialog dialog = buildCustomOk(title, message, buttonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, context);
        return dialog;
    }

    public static AlertDialog buildCustomOk(String title,
                                            String message,
                                            String buttonTitle,
                                            DialogInterface.OnClickListener listener,
                                            Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton(buttonTitle, listener);
        AlertDialog dialog = builder.create();

        return dialog;
    }

}
