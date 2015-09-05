package com.k_anz__.alarmapp.util;

/**
 * Created by Kyoko1 on 2015/08/08.
 */
public final class DateUtill {

    public static int[] parseTime(int time) {
        int hour = time / 60;
        int minute = time % 60;
        return new int[] {hour, minute};
    }
}
