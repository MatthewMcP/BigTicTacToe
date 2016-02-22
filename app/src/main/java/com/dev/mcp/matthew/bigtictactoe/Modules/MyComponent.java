package com.dev.mcp.matthew.bigtictactoe.Modules;

import com.dev.mcp.matthew.bigtictactoe.Activities.HomeActivity;
import com.dev.mcp.matthew.bigtictactoe.Activities.SettingsActivity;
import com.dev.mcp.matthew.bigtictactoe.Activities.SingleGameActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GameModule.class, GameHelperModule.class})
public interface MyComponent {
    void inject(HomeActivity homeActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(SingleGameActivity singleGameActivity);
}