package com.programmingtechniques.uas.Users;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Session {
    SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    Context context;
    public static final String SESSION_USER_LOGIN = "userLogin";
    public static final String SESSION_REMEMBER_ME = "rememberMe";
    //    User Login Session
    private static final String IS_LOGIN = "isLogin";
    public static final String KEY_NAMAPENGGUNA = "namaPengguna";
    public static final String KEY_SUREL = "surel";
    public static final String KEY_KATASANDI = "kataSandi";
    public static final String KEY_NOMORHANDPHONE = "nomorHandphone";
    //    Remember Me Session
    private static final String IS_REMEMBERME = "isRememberMe";
    public static final String KEY_SESSION_NAMAPENGGUNA = "namaPengguna";
    public static final String KEY_SESSION_KATASANDI = "kataSandi";

    public Session(Context _context, String sessionName) {
        context = _context;
        sharedPreferences = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void createUserLoginSession(String namaPengguna, String surel, String kataSandi, String nomorHandphone) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAMAPENGGUNA, namaPengguna);
        editor.putString(KEY_SUREL, surel);
        editor.putString(KEY_KATASANDI, kataSandi);
        editor.putString(KEY_NOMORHANDPHONE, nomorHandphone);
        editor.commit();
    }

    public HashMap<String, String> getUserLoginFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_NAMAPENGGUNA, sharedPreferences.getString(KEY_NAMAPENGGUNA, null));
        userData.put(KEY_SUREL, sharedPreferences.getString(KEY_SUREL, null));
        userData.put(KEY_KATASANDI, sharedPreferences.getString(KEY_KATASANDI, null));
        userData.put(KEY_NOMORHANDPHONE, sharedPreferences.getString(KEY_NOMORHANDPHONE, null));
        return userData;
    }

    public boolean checkUserLogin() {
        return sharedPreferences.getBoolean(IS_REMEMBERME, false);
    }

    public void logoutUserSession() {
        editor.commit();
        editor.clear();
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