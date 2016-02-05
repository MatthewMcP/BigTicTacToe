package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.view.View;

import com.dev.mcp.matthew.bigtictactoe.Components.DaggerILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.ILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Helpers.PreferenceNames;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;
import com.dev.mcp.matthew.bigtictactoe.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends MyFullScreenActivity {

    ILogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        ILoggerComponent logComponent;
        logComponent = DaggerILoggerComponent.builder().iLoggerModule(new ILoggerModule()).build();
        logger = logComponent.provideILogger();

       // getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferencesFragment()).commit();


        logger.i("SettingsActivity", "Loaded Successfully");
    }

    @OnClick(value = R.id.settings_home_btn)
    public void settingsBtnListener() {
        logger.i("HomeActivity", "Settings Button clicked, loading HomeActivity...");
        final Context context = this;
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }

    public static class MyPreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
                Preference preference = getPreferenceScreen().getPreference(i);
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
                        Preference singlePref = preferenceGroup.getPreference(j);
                        updatePreference(singlePref, singlePref.getKey());
                    }
                } else {
                    updatePreference(preference, preference.getKey());
                }
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updatePreference(findPreference(PreferenceNames.NamePreference()), key);
        }

        private void updatePreference(Preference preference, String key) {
            if (preference == null) return;
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setSummary(listPreference.getEntry());
                return;
            }

            SharedPreferences sharedPrefs = getPreferenceManager().getSharedPreferences();

            preference.setSummary(sharedPrefs.getString(key, "Default"));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.settings_home_btn:
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}


