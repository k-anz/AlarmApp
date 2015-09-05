package com.k_anz__.alarmapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.k_anz__.alarmapp.R;
import com.k_anz__.alarmapp.activity.AlarmActivity;

import java.io.IOException;

/**
 * 音を鳴らす・通知を送る機能のサービス
 * Created by Kyoko1 on 2015/08/08.
 */
public class BellService extends Service {
    private NotificationManager mManager;
    private MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sp.getString(getString(R.string.pref_bell_key), "");

        if (url == null) {
            return;
        }

        Uri uri = Uri.parse(url);

        mPlayer = new MediaPlayer();

        try {
            mPlayer.setDataSource(this, uri);
            mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mPlayer.setLooping(true);
            mPlayer.prepare();

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace(); // TODO ちゃんとログはかせる
        }
        Toast.makeText(this, "Set Alarm ", Toast.LENGTH_SHORT).show();//TODO delete

    }

    @Override
    public void onDestroy() {
        mManager.cancel(R.string.app_name);
        mPlayer.pause();
        mPlayer.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent bellIntent = new Intent(this, AlarmActivity.class);
        bellIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(bellIntent);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, bellIntent, 0);


        Notification notification = new Notification.Builder(this)
                .setContentTitle("hogehoge")
                .setContentText("fugafuga")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.abc_btn_default_mtrl_shape)
                .setAutoCancel(true)
                .build();

        // TODO Notificationのコンストラクタの代わりにBuilder使えって
//        Notification notification = new Notification(R.drawable.abc_btn_default_mtrl_shape, "", System.currentTimeMillis());
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, bellIntent, 0);
//        notification.setLatestEventInfo(this, getText(R.string.app_name), "", contentIntent);
//        notification.flags = Notification.FLAG_ONGOING_EVENT;

        mManager.notify(R.string.app_name, notification);
        mPlayer.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
