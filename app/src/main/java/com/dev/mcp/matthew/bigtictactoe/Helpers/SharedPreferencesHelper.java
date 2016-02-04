package com.dev.mcp.matthew.bigtictactoe.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper {

    public static void IncreaseWins(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int currentWins = Integer.parseInt(preferences.getString("pref_gameswon_key", "0"));
        SharedPreferences.Editor editor = preferences.edit();
        int newWins = currentWins + 1;
        editor.putString("pref_gameswon_key", Integer.toString(newWins));
        editor.commit();
    }
}
