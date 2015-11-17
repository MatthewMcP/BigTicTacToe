package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dev.mcp.matthew.bigtictactoe.Components.DaggerILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.ILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;
import com.dev.mcp.matthew.bigtictactoe.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends MyFullScreenActivity {

    ILogger logger;

    @Bind(R.id.home_play_btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        ILoggerComponent logComponent;
        logComponent = DaggerILoggerComponent.builder().iLoggerModule(new ILoggerModule()).build();
        logger = logComponent.provideILogger();
    }

    @OnClick(value = R.id.home_play_btn)
    public void addListenerBtn() {
        final Context context = this;
        Intent intent = new Intent(context, SingleGameActivity.class);
        startActivity(intent);
    }
}
