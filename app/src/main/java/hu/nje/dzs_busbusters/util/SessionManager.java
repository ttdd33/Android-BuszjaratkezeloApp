package hu.nje.dzs_busbusters.util;

import android.content.Context;
import android.content.SharedPreferences;

import hu.nje.dzs_busbusters.data.entities.User;

// A bejelentkezett felhasználó megjegyzése SharedPreferences-ben,
// így az alkalmazás újraindítása után sem kell újra belépni.
public class SessionManager {
    private static final String PREFS_NAME = "bus_session";
    private static final String KEY_USER_ID = "user_key";
    private static final String KEY_NAME = "user_name";

    private final SharedPreferences preferences;

    public SessionManager(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void login(User user) {
        preferences.edit()
                .putString(KEY_USER_ID, user.getId())
                .putString(KEY_NAME, user.getName())
                .apply();
    }

    public void logout() {
        preferences.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return getUserId() != null;
    }

    public String getUserId() {
        return preferences.getString(KEY_USER_ID, null);
    }

    public String getUserName() {
        return preferences.getString(KEY_NAME, "");
    }
}
