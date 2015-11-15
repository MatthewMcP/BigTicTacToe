package com.dev.mcp.matthew.bigtictactoe.Modules;

import com.dev.mcp.matthew.bigtictactoe.Game.Board;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

import dagger.Module;
import dagger.Provides;

@Module
public class IBoardModule {

    @Provides
    IBoard provideIboard() {
        return new Board();
    }

}