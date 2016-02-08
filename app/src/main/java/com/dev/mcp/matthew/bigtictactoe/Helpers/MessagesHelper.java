package com.dev.mcp.matthew.bigtictactoe.Helpers;

import com.dev.mcp.matthew.bigtictactoe.R;

import java.util.Random;

public class MessagesHelper {

    public static String GetRandomComputerWinsMessage(String ComputerName) {
        String[] responseArray = App.getContext().getResources().getStringArray(R.array.message_computerwins_array);
        String selectedStr = responseArray[new Random().nextInt(responseArray.length)];
        String message = String.format(selectedStr, ComputerName);
        return message;
    }

    public static String GetRandomComputerWinsMessage() {
        return GetRandomComputerWinsMessage("Marvin The __ Android");
    }
}
