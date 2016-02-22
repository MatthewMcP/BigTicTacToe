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

public class HomeActivity extends MyFullScreenActivity {

    @Inject
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getMyComponent().inject(this);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        logger.i("HomeActivity", "Loaded Successfully");
    }

    @OnClick(value = R.id.home_play_btn)
    public void playBtnClick() {
        logger.i("HomeActivity", "Play Button clicked, loading ChooseDifficultyActivity...");
        final Context context = this;
        Intent intent = new Intent(context, ChooseDifficultyActivity.class);
        startActivity(intent);
    }

    @OnClick(value = R.id.home_settings_btn)
    public void settingsBtnClick() {
        logger.i("HomeActivity", "Settings Button clicked, loading SettingsActivity...");
        final Context context = this;
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(value = R.id.home_about_btn)
    public void aboutBtnClick() {
        logger.i("HomeActivity", "About Button clicked, loading AboutActivity...");
        final Context context = this;
        Intent intent = new Intent(context, AboutActivity.class);
        startActivity(intent);
    }
}
