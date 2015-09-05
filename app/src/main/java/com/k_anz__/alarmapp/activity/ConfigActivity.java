package com.k_anz__.alarmapp.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.k_anz__.alarmapp.R;
import com.k_anz__.alarmapp.service.BellService;
import com.k_anz__.alarmapp.util.DateUtill;

import java.util.Calendar;

/**
 * 設定画面のActivity
 * Created by Kyoko1 on 2015/08/08.
 */
public class ConfigActivity extends Activity {

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ConfigFragment()).commit();
    }

    /**
     * 設定画面のFragment
     */
    public static class ConfigFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            Preference prefEnabled = findPreference(getString(R.string.pref_enabled_key));
            prefEnabled.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final Context context = getActivity().getApplicationContext();

                    final Boolean enabled = (Boolean) newValue;
                    if (enabled) {
                        setAlarm(context, null);
                    } else {
                        stopAlarm(context);
                    }
                    return true;
                }
            });

            Preference prefTime = findPreference(getString(R.string.pref_time_key));
            prefTime.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final Context context = getActivity().getApplicationContext();

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    boolean enabled = pref.getBoolean(context.getString(R.string.pref_enabled_key), false);

                    if (enabled) {
                        stopAlarm(context);
                        setAlarm(context, DateUtill.parseTime((int) newValue));
                    }
                    return true;
                }
            });
        }
    }

    /**
     * アラームをセットする
     */
    private static void setAlarm(Context context, int[] time) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        time = DateUtill.parseTime(pref.getInt(context.getString(R.string.pref_time_key), 0));

        // 時間をセットする
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, time[0]);
        alarmTime.set(Calendar.MINUTE, time[1]);

        Calendar now = Calendar.getInstance();
        if (alarmTime.compareTo(now) < 0) {
            Toast.makeText(context, "過去の時間が設定されています。", Toast.LENGTH_LONG);
            return;
        }

        Intent intent = new Intent(context, BellService.class);
        PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);

        // アラームをセットする
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pending);

        Toast.makeText(context, "Set Alarm ", Toast.LENGTH_SHORT).show();//TODO delete

    }

    /**
     * アラームを解除する
     */
    private static void stopAlarm(Context context) {
        Intent intent = new Intent(context, BellService.class);
        PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pending);
        Toast.makeText(context, "Stop Alarm ", Toast.LENGTH_SHORT).show();//TODO delete
    }
}
