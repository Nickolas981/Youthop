package com.dongumen.nickolas.youthop.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.dongumen.nickolas.youthop.ApplicationConstants;

import java.util.ArrayList;
import java.util.Arrays;

public class NotifUtil {
    private SharedPreferences sharedPreferences;
    private final String NOTIF = "notif";
    private SharedPreferences.Editor editor;

    public NotifUtil(Context activity) {
        if (sharedPreferences == null)
            sharedPreferences = activity
                    .getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
    }

    public ArrayList<String> getNotifList() {
        ArrayList<String> ids;
        String idStr = getNotifString();
        if (idStr.equals("empty") || idStr.equals("")) {
            ids = new ArrayList<>();
        } else {
            ids = getList(idStr);
        }
        return ids;
    }

    public void deleteNotif() {
        save(new ArrayList<>());
    }

    public void addNotif(String id) {
        ArrayList<String> ids = getNotifList();
        ids.add(id);
        save(ids);
    }

    public int getCount(){
        return getNotifList().size();
    }

    private String getNotifString() {
        return sharedPreferences.getString(NOTIF, "empty");
    }

    private ArrayList<String> getList(String ids) {
        return new ArrayList<>(Arrays.asList(ids.split("%")));
    }

    private String getString(ArrayList<String> ids) {
        StringBuilder result = new StringBuilder();
        for (String id : ids) {
            result.append(id).append("%");
        }
        return result.toString();
    }

    private void save(ArrayList<String> ids) {
        editor = sharedPreferences.edit();
        editor.putString(NOTIF, getString(ids));
        editor.apply();
    }

}
