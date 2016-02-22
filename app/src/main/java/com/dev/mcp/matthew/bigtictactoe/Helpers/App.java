package com.dev.mcp.matthew.bigtictactoe.Helpers;

import android.app.Application;
import android.content.Context;

import com.dev.mcp.matthew.bigtictactoe.Modules.DaggerMyComponent;
import com.dev.mcp.matthew.bigtictactoe.Modules.GameHelperModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.GameModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.MyComponent;

public class App extends Application {

    private static Context myContext;
    private MyComponent myComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;

        myComponent = DaggerMyComponent.builder()
                // list of modules that are part of this component need to be created here too
                .gameModule(new GameModule()) // This also corresponds to the name of your module: %component_name%Module
                .gameHelperModule(new GameHelperModule())
                .build();
    }

    public static Context getContext() {
        return myContext;
    }

    public MyComponent getMyComponent() {
        return myComponent;
    }
}
