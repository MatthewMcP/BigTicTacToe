package com.dev.mcp.matthew.bigtictactoe.Components;

import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Modules.IComputerPlayerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {IComputerPlayerModule.class})
public interface IComputerPlayerComponent {
    IComputerPlayer provideComputerPlayer();
}
