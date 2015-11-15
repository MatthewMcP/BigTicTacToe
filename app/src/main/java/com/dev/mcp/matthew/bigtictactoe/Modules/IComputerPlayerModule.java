package com.dev.mcp.matthew.bigtictactoe.Modules;

import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Game.Board;
import com.dev.mcp.matthew.bigtictactoe.Game.ComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

import dagger.Module;
import dagger.Provides;

@Module
public class IComputerPlayerModule {

    @Provides
    IComputerPlayer provideComputerPlayer() {
        return new ComputerPlayer();
    }
}
