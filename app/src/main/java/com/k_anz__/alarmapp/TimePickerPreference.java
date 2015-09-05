package com.k_anz__.alarmapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.k_anz__.alarmapp.util.DateUtill;

/**
 * 時間指定を行うプリファレンス
 * Created by Kyoko1 on 2015/08/08.
 */
public class TimePickerPreference extends DialogPreference {

    private int mHourOfDay;
    private int mMinute;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimePickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TimePickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimePickerPreference(Context context) {
        super(context);
    }

    @Override
    protected View onCreateDialogView() {
        TimePicker timePicker = new TimePicker(getContext());
        int[] time = DateUtill.parseTime(this.getPersistedInt(0));
        timePicker.setCurrentHour(time[0]);
        timePicker.setCurrentMinute(time[1]);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                TimePickerPreference.this.mHourOfDay = hourOfDay;
                TimePickerPreference.this.mMinute = minute;
            }
        });
        timePicker.setIs24HourView(true);
        return timePicker;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            int time = mHourOfDay * 60 + mMinute;
            persistInt(time);
            callChangeListener(time);
        }
        super.onDialogClosed(positiveResult);
    }
}
