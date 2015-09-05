package com.k_anz__.alarmapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.k_anz__.alarmapp.activity.AlarmActivity;

/**
 * レシーバークラス
 * Created by Kyoko1 on 2015/08/08.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "AlarmBroadCastReceiver#onReceive called", Toast.LENGTH_SHORT).show();

//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setTitle("Alarm");
//        dialog.setMessage("おじかんです");
//
//        dialog.setPositiveButton("了解", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
    }
}
