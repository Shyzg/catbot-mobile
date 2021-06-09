package com.programmingtechniques.uas;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Session {
    SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    Context context;
    //    Remember Me Variable & Session Name
    public static final String SESSION_REMEMBER_ME = "rememberMe";
    private static final String IS_REMEMBERME = "isRememberMe";
    public static final String KEY_SESSION_NAMAPENGGUNA = "namaPengguna";
    public static final String KEY_SESSION_KATASANDI = "kataSandi";

    public Session(Context _context, String sessionName) {
        context = _context;
        sharedPreferences = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //    Remember Me
    public static void createRememberMeSession(String namaPengguna, String kataSandi) {
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSION_NAMAPENGGUNA, namaPengguna);
        editor.putString(KEY_SESSION_KATASANDI, kataSandi);
        editor.commit();
    }

    public HashMap<String, String> getRememberMeFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_SESSION_NAMAPENGGUNA, sharedPreferences.getString(KEY_SESSION_NAMAPENGGUNA, null));
        userData.put(KEY_SESSION_KATASANDI, sharedPreferences.getString(KEY_SESSION_KATASANDI, null));
        return userData;
    }

    public boolean checkRememberMe() {
        return sharedPreferences.getBoolean(IS_REMEMBERME, false);
    }
}