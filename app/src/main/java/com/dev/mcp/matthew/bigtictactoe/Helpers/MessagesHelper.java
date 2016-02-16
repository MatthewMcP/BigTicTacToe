package com.dev.mcp.matthew.bigtictactoe.Helpers;

import com.dev.mcp.matthew.bigtictactoe.Enums.ComputerPlayerType;
import com.dev.mcp.matthew.bigtictactoe.R;

import java.util.Random;

public class MessagesHelper {

    public static String GetComputerWinsMessage(String Name, ComputerPlayerType computerPlayerType) {
        switch (computerPlayerType) {
            case Easy: {
                String selectedMessage = GetEasyComputerMessage();
                return FormatedString(Name, selectedMessage);
            }
            case Medium: {
                String selectedMessage = GetMediumComputerMessage();
                return FormatedString(Name, selectedMessage);
            }
            case Hard: {
                String selectedMessage = GetHardComputerMessage();
                return FormatedString(Name, selectedMessage);
            }
            default:
                String selectedMessage = GetRandomComputerMessage();
                return FormatedString(Name, selectedMessage);
        }
    }

    private static String GetEasyComputerMessage() {
        String[] responseArray = App.getContext().getResources().getStringArray(R.array.message_computerwins_easy_array);
        String selectedStr = responseArray[new Random().nextInt(responseArray.length)];
        return selectedStr;
    }

    private static String GetMediumComputerMessage() {
        String[] responseArray = App.getContext().getResources().getStringArray(R.array.message_computerwins_easy_array);
        String selectedStr = responseArray[new Random().nextInt(responseArray.length)];
        return selectedStr;
    }

    private static String GetHardComputerMessage() {
        String[] responseArray = App.getContext().getResources().getStringArray(R.array.message_computerwins_easy_array);
        String selectedStr = responseArray[new Random().nextInt(responseArray.length)];
        return selectedStr;
    }

    private static String GetRandomComputerMessage() {
        String[] responseArray = App.getContext().getResources().getStringArray(R.array.message_computerwins_easy_array);
        String selectedStr = responseArray[new Random().nextInt(responseArray.length)];
        return selectedStr;
    }

    private static String FormatedString(String Name, String MessageToFormat) {
        String message = String.format(MessageToFormat, Name);
        return message;
    }
}
