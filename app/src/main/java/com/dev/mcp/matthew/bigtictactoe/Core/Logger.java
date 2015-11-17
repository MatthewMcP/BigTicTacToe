package com.dev.mcp.matthew.bigtictactoe.Core;

import android.support.v7.appcompat.BuildConfig;
import android.util.Log;

import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;


public class Logger implements ILogger {
    @Override
    public void v(String ActivityName, String Message) {
        if (BuildConfig.DEBUG) {
            Log.v(ActivityName, "VMessage" + Message);
        }
    }

    @Override
    public void d(String ActivityName, String Message) {
        if (BuildConfig.DEBUG) {
            Log.d(ActivityName, "DMessage" + Message);
        }
    }

    @Override
    public void i(String ActivityName, String Message) {
        if (BuildConfig.DEBUG) {
            Log.i(ActivityName, "IMessage" + Message);
        }
    }

    @Override
    public void w(String ActivityName, String Message) {
        if (BuildConfig.DEBUG) {
            Log.w(ActivityName, "WMessage" + Message);
        }
    }

    @Override
    public void e(String ActivityName, String Message) {
        if (BuildConfig.DEBUG) {
            Log.e(ActivityName, "EMessage" + Message);
        }
    }
}