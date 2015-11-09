package com.dev.mcp.matthew.bigtictactoe.Modules;

import com.dev.mcp.matthew.bigtictactoe.Core.Logger;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by matthew on 06/11/15.
 */
@Module
public class ILoggerModule {

    @Provides
    ILogger provideILogger() {
        return new Logger();
    }
}
