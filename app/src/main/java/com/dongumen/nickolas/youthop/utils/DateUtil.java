package com.dongumen.nickolas.youthop.utils;


import android.text.format.DateFormat;

import java.util.Calendar;

public class DateUtil {

    public static String getDeadlineDays(long date1){
        String res;
        Calendar curr = Calendar.getInstance();
        long millis2 = curr.getTimeInMillis();
        long diff = date1 - millis2;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        res = String.valueOf(diffDays);
        if (diffDays > 1)
            res += " days";
        else if(diffDays < 1)
            res = "ended";
        else
            res += " day";
        return res;
    }

    public static String getDateFrormated(long date1){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date1);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
