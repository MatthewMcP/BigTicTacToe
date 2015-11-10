package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.dev.mcp.matthew.bigtictactoe.Components.DaggerILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.ILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;
import com.dev.mcp.matthew.bigtictactoe.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SingleGameActivity extends AppCompatActivity {

    ILogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_game);
        ILoggerComponent logComponent;
        logComponent = DaggerILoggerComponent.builder().iLoggerModule(new ILoggerModule()).build();

        logger = logComponent.provideILogger();
    }
}
