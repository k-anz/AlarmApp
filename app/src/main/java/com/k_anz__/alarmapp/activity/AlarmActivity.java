package com.k_anz__.alarmapp.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.k_anz__.alarmapp.R;
import com.k_anz__.alarmapp.service.BellService;

/**
 * アラーム起動時に表示される画面
 * Created by Kyoko1 on 2015/08/08.
 */
public class AlarmActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AlarmActivity.this.getApplicationContext();
                    Intent intent = new Intent(context, BellService.class);
                    stopService(intent);
                    finish();
            }
        });
    }
}
