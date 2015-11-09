package com.dev.mcp.matthew.bigtictactoe.Interfaces;

/**
 * Created by matthew on 06/11/15.
 */
public interface ILogger {

    void v(String ActivityName, String Message);

    void d(String ActivityName, String Message);

    void i(String ActivityName, String Message);

    void w(String ActivityName, String Message);

    void e(String ActivityName, String Message);
}
