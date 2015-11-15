package com.dev.mcp.matthew.bigtictactoe.Components;

import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;
import com.dev.mcp.matthew.bigtictactoe.Modules.IBoardModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {IBoardModule.class})
public interface IBoardComponent {
    IBoard provideBoard();
}
