package com.dev.mcp.matthew.bigtictactoe.Interfaces;

public interface ILogger {

    void v(String ActivityName, String Message);

    void d(String ActivityName, String Message);

    void i(String ActivityName, String Message);

    void w(String ActivityName, String Message);

    void e(String ActivityName, String Message);
}
