package com.dev.mcp.matthew.bigtictactoe.Modules;

import com.dev.mcp.matthew.bigtictactoe.Core.Logger;
import com.dev.mcp.matthew.bigtictactoe.Helpers.ComputerPlayerHelper;
import com.dev.mcp.matthew.bigtictactoe.Helpers.MessagesHelper;
import com.dev.mcp.matthew.bigtictactoe.Helpers.PreferenceNames;
import com.dev.mcp.matthew.bigtictactoe.Helpers.SharedPreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GameHelperModule {

    Logger logger;

    @Provides
    @Singleton
    Logger provideLogger() {

        if (logger != null) {
            return logger;
        }

        return new Logger();
    }

    @Provides
    @Singleton
    ComputerPlayerHelper provideComputerPlayerHelperr() {
        return new ComputerPlayerHelper();
    }

    @Provides
    @Singleton
    MessagesHelper provideMessagesHelper() {
        return new MessagesHelper();
    }

    @Provides
    @Singleton
    PreferenceNames providePreferenceNames() {
        return new PreferenceNames();
    }

    @Provides
    @Singleton
    SharedPreferencesHelper provideSharedPreferencesHelper() {
        return new SharedPreferencesHelper();
    }
}