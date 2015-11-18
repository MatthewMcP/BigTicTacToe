package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.mcp.matthew.bigtictactoe.Components.DaggerIBoardComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.DaggerIComputerPlayerComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.DaggerILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.IBoardComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.IComputerPlayerComponent;
import com.dev.mcp.matthew.bigtictactoe.Components.ILoggerComponent;
import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.IBoardModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.IComputerPlayerModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;
import com.dev.mcp.matthew.bigtictactoe.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleGameActivity extends MyFullScreenActivity {

    ILogger logger;
    IBoard gameBoard;
    IComputerPlayer computerPlayer;

    private int moveCount = 0, xloc = 0, yloc = 0;
    private CellState mark = CellState.XMark, aiMark = CellState.OMark;
    //private String mark = getResources().getString(R.string.XConst), aiMark = getResources().getString(R.string.OConst);
    private boolean isOver = false;


    @Bind(R.id.activity_singlegame_reset)
    TextView button;

    @Bind(R.id.activity_singlegame_radioBtns)
    RadioGroup radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);
        ButterKnife.bind(this);

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

    @OnClick(value = R.id.activity_singlegame_reset)
    public void resetClick() {
        clear();
        if (aiMark == CellState.XMark) {
            getAIMove(gameBoard);
        }
    }

    @OnClick(value = R.id.activity_singlegame_radioBtns)
    public void onRadioButtonClicked(View view) {
        View viewChild = ((ViewGroup) view).getChildAt(0);

        switch (viewChild.getId()) {
            case R.id.radio_X:
                mark = CellState.XMark;
                aiMark = CellState.OMark;
                clear();
                break;
            case R.id.radio_O:
                mark = CellState.OMark;
                aiMark = CellState.XMark;
                clear();
                getAIMove(gameBoard);
                break;
        }
    }

    public void cellClick(View v) {
        //Get the id of the clicked object and assign it to a Textview variable
        TextView cell = (TextView) findViewById(v.getId());
        //Check the content and make sure the cell is empty and that the game isn't over
        String content = (String) cell.getText();
        CellState current;
        if (content == "X") {
            current = CellState.XMark;
        } else if (content == "") {
            current = CellState.Empty;
        } else {
            current = CellState.OMark;
        }
        if (current == CellState.Empty && !isOver) {
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
            gameBoard.placeMark(new Point(xloc, yloc), mark);

            cell.setText(mark.toString());

            //Increment move Count because a move was just made
            moveCount++;

            //Check to see if the game is over
            isOver = checkEnd(mark);

            //if the game game is over get the AI's move
            if (!isOver)
                getAIMove(gameBoard);

        }
    }


    //Checks to see if the game has ended provided with the last player to make a move
    private boolean checkEnd(CellState player) {
        //Checks the virtual board for a winner if there's a winner announce it with the provided player
        if (gameBoard.isWinner()) {
            announce(true, player.toString());
            return true;
        }
        //Check to see if we've reached our move total meaning it's a draw
        else if (moveCount >= 9) {
            announce(false, player.toString());
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
        Point move = computerPlayer.getMove(board);
        TextView cell = null;
        //Determine the right cell to use by id first go to the right row then the right column
        switch (move.x) {
            case 0:
                switch (move.y) {
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
                switch (move.y) {
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
                switch (move.y) {
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
            board.placeMark(new Point(0, 1), aiMark);
            cell.setText(aiMark.toString());
            moveCount++;
            isOver = checkEnd(aiMark);
        }
    }

}
