package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.dev.mcp.matthew.bigtictactoe.Components.*;
import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.IBoardModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;
import com.dev.mcp.matthew.bigtictactoe.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SingleGameActivity extends MyFullScreenActivity {

    ILogger logger;
    IBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);

        ILoggerComponent logComponent;
        logComponent = DaggerILoggerComponent.builder().iLoggerModule(new ILoggerModule()).build();
        logger = logComponent.provideILogger();

        IBoardComponent boardComponent;
        boardComponent = DaggerIBoardComponent.builder().iBoardModule(new IBoardModule()).build();
        gameBoard = boardComponent.provideBoard();
    }


    public void cellClick(View v) {

    }

    //Even for when the user changes between going first and going second
    public void onRadioButtonClicked(View view) {
    }

}
