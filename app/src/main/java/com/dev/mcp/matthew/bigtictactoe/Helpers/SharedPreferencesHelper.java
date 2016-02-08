package com.dev.mcp.matthew.bigtictactoe.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dev.mcp.matthew.bigtictactoe.R;

public class SharedPreferencesHelper {

    public static void IncreaseWins(Context context) {
        IncreasePreferenceByOne(PreferenceNames.WinsPreference(), context);
    }

    public static void IncreaseLosses(Context context) {
        IncreasePreferenceByOne(PreferenceNames.LosesPreference(), context);
    }

    public static void IncreaseDraws(Context context) {
        IncreasePreferenceByOne(PreferenceNames.DrawsPreference(), context);
    }

    private static void IncreasePreferenceByOne(String preferenceName, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        int currentValue = Integer.parseInt(preferences.getString(preferenceName, "0"));
        int newValue = currentValue + 1;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preferenceName, Integer.toString(newValue));
        editor.commit();
    }

    public static void ResetStatistics(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
        PreferenceManager.setDefaultValues(context, R.xml.preferences, true);
    }
}
