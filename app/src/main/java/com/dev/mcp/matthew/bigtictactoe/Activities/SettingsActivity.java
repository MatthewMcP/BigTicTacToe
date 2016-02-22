package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dev.mcp.matthew.bigtictactoe.Core.Logger;
import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Helpers.App;
import com.dev.mcp.matthew.bigtictactoe.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends MyFullScreenActivity {

    @Inject
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        ((App) getApplication()).getMyComponent().inject(this);

        logger.i("SettingsActivity", "Loaded Successfully");
    }

    @OnClick(value = R.id.settings_home_btn)
    public void settingsHomeBtnListener() {
        logger.i("HomeActivity", "Settings Button clicked, loading HomeActivity...");
        final Context context = this;
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }
}


