package com.dev.mcp.matthew.bigtictactoe.Components;

import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ILoggerModule.class})
public interface ILoggerComponent {
    ILogger provideILogger();
}
