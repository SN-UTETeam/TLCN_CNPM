package com.spkt.nguyenducnguu.jobstore.SharedPreferencesManager;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String KEY_ISEXIST = "isExist";
    private static final String KEY_ID = "id_selected";
    private static final String KEY_NAME = "name_selected";

    // Constructor
    public CacheManager(Context context, String Pref_Name) {
        this._context = context;
        pref = _context.getSharedPreferences(Pref_Name, PRIVATE_MODE);
        editor = pref.edit();
    }
    //Create
    public void create(int id, String name) {
        editor.putBoolean(KEY_ISEXIST, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        // commit changes
        editor.commit();
    }
    //Get id
    public int getId() {
        return pref.getInt(KEY_ID, -1);
    }
    //Get name
    public String getName() {
        return pref.getString(KEY_NAME, null);
    }
    //Clear SharedPreferences
    public void clear() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }
    //Check xem SharedPreferences đã tồn tại hay chưa
    public Boolean isExist() {
        return pref.getBoolean(KEY_ISEXIST, false);
    }
}
