package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.mcp.matthew.bigtictactoe.Core.Logger;
import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Enums.ComputerPlayerType;
import com.dev.mcp.matthew.bigtictactoe.Game.Board;
import com.dev.mcp.matthew.bigtictactoe.Helpers.App;
import com.dev.mcp.matthew.bigtictactoe.Helpers.ComputerPlayerHelper;
import com.dev.mcp.matthew.bigtictactoe.Helpers.MessagesHelper;
import com.dev.mcp.matthew.bigtictactoe.Helpers.SharedPreferencesHelper;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.R;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleGameActivity extends MyFullScreenActivity {

    @Inject
    Logger logger;

    @Inject
    ComputerPlayerHelper computerPlayerHelper;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    Board gameBoard;
    IComputerPlayer computerPlayer;


    private int moveCount = 0;

    private CellState playerMark = CellState.XMark;
    private CellState aiMark = CellState.OMark;

    private boolean isOver = false;

    private Map<Point, Integer> cellPointMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);
        ButterKnife.bind(this);
        ((App) getApplication()).getMyComponent().inject(this);

        logger.i("SingleGameActivity", "Loading...");

        CreateDictionary();

        Bundle extras = getIntent().getExtras();
        ComputerPlayerType computerPlayerType = null;
        if (extras != null) {
            String stringComputerPlayerType = extras.getString(getString(R.string.computerDifficulty_Key));
            computerPlayerType = ComputerPlayerType.valueOf(stringComputerPlayerType);
        }

        computerPlayer = computerPlayerHelper.CreateComputerPlayer(computerPlayerType);

        logger.i("SingleGameActivity", "Loaded Successfully");
    }

    @Override
    public void onBackPressed() {
        clear();
        super.onBackPressed();
    }

    private void CreateDictionary() {
        cellPointMap = new HashMap<>();
        cellPointMap.put(new Point(0, 0), R.id.cell11);
        cellPointMap.put(new Point(0, 1), R.id.cell12);
        cellPointMap.put(new Point(0, 2), R.id.cell13);
        cellPointMap.put(new Point(1, 0), R.id.cell21);
        cellPointMap.put(new Point(1, 1), R.id.cell22);
        cellPointMap.put(new Point(1, 2), R.id.cell23);
        cellPointMap.put(new Point(2, 0), R.id.cell31);
        cellPointMap.put(new Point(2, 1), R.id.cell32);
        cellPointMap.put(new Point(2, 2), R.id.cell33);
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

        for (int item : cellPointMap.values()) {
            TextView cell = (TextView) findViewById(item);
            cell.setBackgroundResource(R.drawable.cell);
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

        if (!isOver) {
            TextView cell = (TextView) findViewById(v.getId());
            Point pointClicked = GetPlayerClickPoint(cell);

            if (gameBoard.isValidMark(pointClicked)) {
                gameBoard.placeMark(pointClicked, playerMark);
                int playerDrawableId = getPlayerDrawable();
                cell.setBackgroundResource(playerDrawableId);
                EndOfTurn(playerMark);
            }
        }
    }

    //I don't like this -better option?
    private Point GetPlayerClickPoint(TextView cell) {
        for (Map.Entry<Point, Integer> entry : cellPointMap.entrySet()) {
            if (entry.getValue() == cell.getId()) {
                return entry.getKey();
            }
        }
        return null;
    }

    private int getPlayerDrawable() {
        if (playerMark == CellState.XMark) {
            return R.drawable.cross_player;
        }
        return R.drawable.circle_player;
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
            announceAndUpdateStats(true, playerMark);
            return true;
        } else if (moveCount >= 9) {
            logger.i("SingleGameActivity", "Draw");
            announceAndUpdateStats(false, playerMark);
            return true;
        }
        return false;
    }

    private void announceAndUpdateStats(boolean endState, CellState playerMark) {

        String message;
        if (endState && this.playerMark == playerMark) {
            message = sharedPreferencesHelper.GetPlayerName(getApplicationContext());
            sharedPreferencesHelper.IncreaseWins(getApplicationContext());
        } else if (endState && playerMark == aiMark) {
            message = MessagesHelper.GetComputerWinsMessage(computerPlayer.getName(), computerPlayer.getPlayerType());
            sharedPreferencesHelper.IncreaseLosses(getApplicationContext());
        } else {
            message = "It's a draw!";
            sharedPreferencesHelper.IncreaseDraws(getApplicationContext());
        }

        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }


    private void getAIMove(IBoard board) {
        logger.i("SingleGameActivity", "Getting AI move");

        Point computerMove = computerPlayer.getMove(board);
        if (gameBoard.isValidMark(computerMove)) {
            TextView cell = GetAiCell(computerMove);
            board.placeMark(computerMove, aiMark);
            if (aiMark == CellState.XMark) {
                cell.setBackgroundResource(R.drawable.cross_player);
            } else {
                cell.setBackgroundResource(R.drawable.circle_player);
            }

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
