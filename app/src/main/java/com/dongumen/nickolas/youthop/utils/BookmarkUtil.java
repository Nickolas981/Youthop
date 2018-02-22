package com.dongumen.nickolas.youthop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;


public class BookmarkUtil {
    private SharedPreferences sharedPreferences;
    private final String BOOKMARKS = "bookmarks";
    private SharedPreferences.Editor editor;

    public BookmarkUtil(Activity activity) {
        if (sharedPreferences == null)
            sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public ArrayList<String> getBookmarkList() {
        ArrayList<String> ids;
        String idStr = getBookmarkString();
        if (idStr.equals("empty")) {
            ids = new ArrayList<>();
        } else {
            ids = getList(idStr);
        }
        return ids;
    }

    public void deleteBookmark(String id) {
        ArrayList<String> ids = getBookmarkList();
        ids.remove(id);
        save(ids);
    }

    public void addBookmark(String id) {
        ArrayList<String> ids = getBookmarkList();
        ids.add(id);
        save(ids);
    }

    private String getBookmarkString() {
        return sharedPreferences.getString(BOOKMARKS, "empty");
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
        editor.putString(BOOKMARKS, getString(ids));
        editor.apply();
    }

}
