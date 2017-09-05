package com.example.lixnet.mkopo.helpers;

/**
 * Created by Lixnet on 2017-08-22.
 */


        import android.content.Context;
        import android.content.SharedPreferences;
        import android.util.Log;

        import java.util.HashMap;
        import java.util.Map;

public class GEPreference {

    public static final String USER_EMAIL = "user_email";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private static final String PREFERENCES = "ge_prefs";
    private static final String SPLASH_SHOWN = "splash_shown";
    public static final String USER_ID = "user_id";
    public static final String USER_PHONE= "user_phone";
    public static final String USER_IDNO= "user_idno";
    public static final String USER_GENDER= "user_gender";
    private static final String USER_LOGGED = "user_logged";
    public static final String USER_NAME = "user_name";

    private static final String PAYMENT_OPTION = "payment_option";
    private static final String TOKEN = "token";

    public GEPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
    }

    public void setPaymentOption(String paymentOption) {
        editor.putString(PAYMENT_OPTION, paymentOption);
        editor.apply();
    }

    public String getPaymentOption() {
        return sharedPreferences.getString(PAYMENT_OPTION, "");
    }

    public void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, "");
    }

    // User preference
    public void setUser(String id, String full_name, String phone_number, String email, String id_number, String gender) {
        editor.putString(USER_ID, id);
        editor.putString(USER_NAME, full_name);
        editor.putString(USER_PHONE, phone_number);
        editor.putString(USER_EMAIL, email);
        editor.putString(USER_IDNO, id_number);
        editor.putString(USER_GENDER, gender);
        editor.putBoolean(USER_LOGGED, true);
        Log.d(USER_ID, id);
        Log.d("ASS", "ASS");
        editor.apply();
    }

    public void unsetUser() {
        editor.clear();
        editor.apply();
    }

    public Map<String, String> getUser() {
        Map<String, String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, ""));
        user.put(USER_NAME, sharedPreferences.getString(USER_NAME, ""));
        user.put(USER_PHONE, sharedPreferences.getString(USER_PHONE, ""));
        user.put(USER_EMAIL, sharedPreferences.getString(USER_EMAIL, ""));
        user.put(USER_IDNO, sharedPreferences.getString(USER_IDNO, ""));
        user.put(USER_GENDER, sharedPreferences.getString(USER_GENDER, ""));
        return user;
    }



    public boolean isUserLogged() {
        return sharedPreferences.getBoolean(USER_LOGGED, false);
    }
}
