package com.dev.mcp.matthew.bigtictactoe.Helpers;

import com.dev.mcp.matthew.bigtictactoe.R;

public class PreferenceNames {

    public static String NamePreference() {
        return App.getContext().getString(R.string.preference_playername_Key);
    }

    public static String WinsPreference() {
        return App.getContext().getString(R.string.preference_gameswon_key);
    }

    public static String LosesPreference() {
        return App.getContext().getString(R.string.preference_gameslost_key);

    }

    public static String DrawsPreference() {
        return App.getContext().getString(R.string.preference_gamesdrawn_key);
    }
}
