package com.firdadev.runsheetv2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.firdadev.runsheetv2.MainActivity;
import com.firdadev.runsheetv2.R;
import com.firdadev.runsheetv2.activity.LoginActivity;

public class AlertDialogManager {


    public void showAlertDialog(Context context, String tittle, String messages, Boolean status) {

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(tittle)
                .setMessage(messages)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                    })
                .create();
        dialog.show();
    }
}
