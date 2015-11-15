package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.mcp.matthew.bigtictactoe.Components.*;
import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.IBoardModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.IComputerPlayerModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;
import com.dev.mcp.matthew.bigtictactoe.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SingleGameActivity extends MyFullScreenActivity {

    ILogger logger;
    IBoard gameBoard;
    IComputerPlayer computerPlayer;

    private int moveCount = 0, xloc = 0, yloc = 0;
    private String mark = getResources().getString(R.string.XConst), aiMark = getResources().getString(R.string.OConst);
    private boolean isOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);

        ILoggerComponent logComponent;
        logComponent = DaggerILoggerComponent.builder().iLoggerModule(new ILoggerModule()).build();
        logger = logComponent.provideILogger();

        IBoardComponent boardComponent;
        boardComponent = DaggerIBoardComponent.builder().iBoardModule(new IBoardModule()).build();
        gameBoard = boardComponent.provideBoard();

        IComputerPlayerComponent computerPlayerComponentComponent;
        computerPlayerComponentComponent = DaggerIComputerPlayerComponent.builder().iComputerPlayerModule(new IComputerPlayerModule()).build();
        computerPlayer = computerPlayerComponentComponent.provideComputerPlayer();
    }

    //Action when reset is clicked which clears the screen and the virtual game board
    public void resetClick(View v) {
        clear();
        if (aiMark == "X") getAIMove(gameBoard);
    }

    //Action for when a cell is clicked. Determines which cell has been clicked and passed that
    //	information on the the virtual game board.
    public void cellClick(View v) {
        //Get the id of the clicked object and assign it to a Textview variable
        TextView cell = (TextView) findViewById(v.getId());
        //Check the content and make sure the cell is empty and that the game isn't over
        String content = (String) cell.getText();
        if (content == "" && !isOver) {
            //Find the X Y location values of the particular cell that was clicked
            switch (cell.getId()) {
                case R.id.cell11:
                    xloc = 0;
                    yloc = 0;
                    break;
                case R.id.cell12:
                    xloc = 0;
                    yloc = 1;
                    break;
                case R.id.cell13:
                    xloc = 0;
                    yloc = 2;
                    break;
                case R.id.cell21:
                    xloc = 1;
                    yloc = 0;
                    break;
                case R.id.cell22:
                    xloc = 1;
                    yloc = 1;
                    break;
                case R.id.cell23:
                    xloc = 1;
                    yloc = 2;
                    break;
                case R.id.cell31:
                    xloc = 2;
                    yloc = 0;
                    break;
                case R.id.cell32:
                    xloc = 2;
                    yloc = 1;
                    break;
                case R.id.cell33:
                    xloc = 2;
                    yloc = 2;
                    break;
            }

            //Place the player's mark on the specific X Y location on both the virtual and displayed board
            gameBoard.placeMark(xloc, yloc, mark);
            cell.setText(mark);

            //Increment move Count because a move was just made
            moveCount++;

            //Check to see if the game is over
            isOver = checkEnd(mark);

            //if the game game is over get the AI's move
            if (!isOver)
                getAIMove(gameBoard);

        }
    }

    //Even for when the user changes between going first and going second
    public void onRadioButtonClicked(View view) {
        //Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button was clicked
        switch (view.getId()) {
            //If the user wants to be X just clear the board and wait for his move
            case R.id.radio_X:
                if (checked)
                    mark = "X";
                aiMark = "O";
                clear();
                break;
            //If the user wants to be O's then clear the board and get the AI's opening move
            case R.id.radio_O:
                if (checked)
                    mark = "O";
                aiMark = "X";
                clear();
                getAIMove(gameBoard);
                break;
        }
    }

    //Checks to see if the game has ended provided with the last player to make a move
    private boolean checkEnd(String player) {
        //Checks the virtual board for a winner if there's a winner announce it with the provided player
        if (gameBoard.isWinner()) {
            announce(true, player);
            return true;
        }
        //Check to see if we've reached our move total meaning it's a draw
        else if (moveCount >= 9) {
            announce(false, player);
            return true;
        }
        //If neither win or draw then the game is still on
        return false;
    }

    //Announce the winner, given a boolean for whether it was a win or a draw
    // and given the last player to make a mark
    private void announce(boolean endState, String player) {
        //Check for if it's a win or a draw. if it's a win amend player with wins!
        //	if it's a lose replace player with it's a draw! I did this just because why
        //  declare another String when I can just reuse the one I have?
        if (endState == true)
            player = player + " wins!";
        else
            player = "It's a draw!";

        //Get the application Context and setup the Toast notification with the end state info
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_LONG);
        toast.show();
    }

    //Clears the game Board
    private void clear() {
        //Get the id list of all the Textview cells
        int[] idList = {R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21,
                R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33};
        TextView cell;
        //For each cell clear the text with an empty string
        for (int item : idList) {
            cell = (TextView) findViewById(item);
            cell.setText("");
        }
        //Reset the game state and clear the virtual board
        isOver = false;
        moveCount = 0;
        gameBoard.clear();
    }

    //Gets the AI's next move giving the current state of the board
    private void getAIMove(IBoard board) {
        //Send the board to the AI for it to determine and return the move in an array {x,y}
        int[] move = computerPlayer.move(board, aiMark);
        TextView cell = null;
        //Determine the right cell to use by id first go to the right row then the right column
        switch (move[0]) {
            case 0:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell11);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell12);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell13);
                        break;
                }
                break;
            case 1:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell21);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell22);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell23);
                        break;
                }
                break;
            case 2:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell31);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell32);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell33);
                        break;
                }
                break;
        }

        //Make sure there's nothing already in the cell
        //	then place the mark with the ai's Mark, increment move count
        //	and check to see if the game's over
        if (cell != null && cell.getText() == "") {
            board.placeMark(move[0], move[1], aiMark);
            cell.setText(aiMark);
            moveCount++;
            isOver = checkEnd(aiMark);
        }
    }

}
