package com.dev.mcp.matthew.bigtictactoe.Helpers;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context myContext;

    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
    }

    public static Context getContext() {
        return myContext;
    }
}
