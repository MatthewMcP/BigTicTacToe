package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
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
import com.dev.mcp.matthew.bigtictactoe.Helpers.CellStateHelper;
import com.dev.mcp.matthew.bigtictactoe.Helpers.PreferenceNames;
import com.dev.mcp.matthew.bigtictactoe.Helpers.SharedPreferencesHelper;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.ILogger;
import com.dev.mcp.matthew.bigtictactoe.Modules.IBoardModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.IComputerPlayerModule;
import com.dev.mcp.matthew.bigtictactoe.Modules.ILoggerModule;
import com.dev.mcp.matthew.bigtictactoe.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleGameActivity extends MyFullScreenActivity {

    ILogger logger;
    IBoard gameBoard;
    IComputerPlayer computerPlayer;
    CellStateHelper cellStateHelper;
    SharedPreferencesHelper sharedPreferencesHelper;

    private int moveCount = 0;

    private CellState playerMark = CellState.XMark;
    private CellState aiMark = CellState.OMark;

    private boolean isOver = false;

    private int[] idList = {R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21,
            R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);
        ButterKnife.bind(this);

        ILoggerComponent logComponent;
        logComponent = DaggerILoggerComponent.builder().iLoggerModule(new ILoggerModule()).build();
        logger = logComponent.provideILogger();

        logger.i("SingleGameActivity", "Loading...");

        IBoardComponent boardComponent;
        boardComponent = DaggerIBoardComponent.builder().iBoardModule(new IBoardModule()).build();
        gameBoard = boardComponent.provideBoard();

        IComputerPlayerComponent computerPlayerComponentComponent;
        computerPlayerComponentComponent = DaggerIComputerPlayerComponent.builder().iComputerPlayerModule(new IComputerPlayerModule()).build();
        computerPlayer = computerPlayerComponentComponent.provideComputerPlayer();

        cellStateHelper = new CellStateHelper(this);
        sharedPreferencesHelper = new SharedPreferencesHelper();
        logger.i("SingleGameActivity", "Loaded Successfully");
    }

    @OnClick(value = R.id.singlegame_reset)
    public void resetClick() {
        logger.i("SingleGameActivity", "Resetting board...");
        clear();
        if (aiMark == CellState.XMark) {
            getAIMove(gameBoard);
        }
    }

    private void clear() {
        logger.i("SingleGameActivity", "Clearing the board");

        sharedPreferencesHelper.IncreaseWins(getApplicationContext());
        for (int item : idList) {
            TextView cell = (TextView) findViewById(item);
            cell.setText("");
        }

        isOver = false;
        moveCount = 0;
        gameBoard.clear();
    }

    @OnClick(value = R.id.singlegame_radioBtn_X)
    public void xRadioBtnClicked(View view) {
        logger.i("SingleGameActivity", "X RadioBtn Clicked, Player is X");
        playerMark = CellState.XMark;
        aiMark = CellState.OMark;
        clear();
    }

    @OnClick(value = R.id.singlegame_radioBtn_O)
    public void oRadioBtnClicked(View view) {
        logger.i("SingleGameActivity", "O RadioBtn Clicked, Player is O");
        playerMark = CellState.OMark;
        aiMark = CellState.XMark;
        clear();
        getAIMove(gameBoard);
    }

    public void cellClick(View v) {
        TextView cell = (TextView) findViewById(v.getId());

        if (ValidMove(cell)) {
            Point pointClicked = GetPlayerClickPoint(cell);
            gameBoard.placeMark(pointClicked, playerMark);
            cell.setText(cellStateHelper.CellStateToString(playerMark));
            EndOfTurn(playerMark);
        }
    }

    private Point GetPlayerClickPoint(TextView cell) {
        switch (cell.getId()) {
            case R.id.cell11:
                return new Point(0, 0);
            case R.id.cell12:
                return new Point(0, 1);
            case R.id.cell13:
                return new Point(0, 2);
            case R.id.cell21:
                return new Point(1, 0);
            case R.id.cell22:
                return new Point(1, 1);
            case R.id.cell23:
                return new Point(1, 2);
            case R.id.cell31:
                return new Point(2, 0);
            case R.id.cell32:
                return new Point(2, 1);
            default:
                return new Point(2, 2);
        }
    }

    private boolean ValidMove(TextView cell) {
        String content = (String) cell.getText();
        CellState current = cellStateHelper.StringToCellState(content);

        if (current == CellState.Empty && !isOver) {
            return true;
        }

        return false;
    }

    private void EndOfTurn(CellState mark) {
        moveCount++;
        isOver = checkEnd(mark);

        if (!isOver && mark != aiMark) {
            getAIMove(gameBoard);
        }
    }

    private boolean checkEnd(CellState playerMark) {
        if (gameBoard.isWinner()) {
            logger.i("SingleGameActivity", "Winner");
            announce(true, playerMark);
            return true;
        } else if (moveCount >= 9) {
            logger.i("SingleGameActivity", "Draw");
            announce(false, playerMark);
            return true;
        }
        return false;
    }

    private void announce(boolean endState, CellState playerMark) {

        String message;
        if (endState && this.playerMark == playerMark) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String player = sharedPref.getString(PreferenceNames.NamePreference(), "Player 1");
            message = player + " wins!";
            sharedPreferencesHelper.IncreaseWins(getApplicationContext());
        } else if (endState && playerMark == aiMark) {
            message = "The Almighty computer wins!";
            sharedPreferencesHelper.IncreaseLosses(getApplicationContext());
        } else {
            message = "It's a draw!";
            sharedPreferencesHelper.IncreaseDraws(getApplicationContext());
        }

        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }


    private void getAIMove(IBoard board) {
        logger.i("SingleGameActivity", "Getting AI move");

        Point move = computerPlayer.getMove(board);
        TextView cell = GetAiCell(move);

        if (cell != null && cell.getText().equals("")) {
            board.placeMark(move, aiMark);
            String cellStateString = cellStateHelper.CellStateToString(aiMark);
            cell.setText(cellStateString);
            EndOfTurn(aiMark);
        }
    }


    private TextView GetAiCell(Point aiMove) {
        switch (aiMove.x) {
            case 0:
                switch (aiMove.y) {
                    case 0:
                        return (TextView) findViewById(R.id.cell11);
                    case 1:
                        return (TextView) findViewById(R.id.cell12);
                    case 2:
                        return (TextView) findViewById(R.id.cell13);
                }
            case 1:
                switch (aiMove.y) {
                    case 0:
                        return (TextView) findViewById(R.id.cell21);
                    case 1:
                        return (TextView) findViewById(R.id.cell22);
                    case 2:
                        return (TextView) findViewById(R.id.cell23);
                }
            case 2:
                switch (aiMove.y) {
                    case 0:
                        return (TextView) findViewById(R.id.cell31);
                    case 1:
                        return (TextView) findViewById(R.id.cell32);
                    case 2:
                        return (TextView) findViewById(R.id.cell33);
                }
        }
        return null;
    }
}
