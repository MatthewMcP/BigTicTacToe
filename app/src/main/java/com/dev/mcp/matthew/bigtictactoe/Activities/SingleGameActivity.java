package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.dev.mcp.matthew.bigtictactoe.Helpers.CellStateHelper;
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
    CellStateHelper cellStateHelper;


    private int moveCount = 0;

    private CellState mark = CellState.XMark;
    private CellState aiMark = CellState.OMark;

    private boolean isOver = false;

    private int[] idList = {R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21,
            R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33};

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

        logger.i("SingleGameActivity", "Loading...");

        IBoardComponent boardComponent;
        boardComponent = DaggerIBoardComponent.builder().iBoardModule(new IBoardModule()).build();
        gameBoard = boardComponent.provideBoard();

        IComputerPlayerComponent computerPlayerComponentComponent;
        computerPlayerComponentComponent = DaggerIComputerPlayerComponent.builder().iComputerPlayerModule(new IComputerPlayerModule()).build();
        computerPlayer = computerPlayerComponentComponent.provideComputerPlayer();

        cellStateHelper = new CellStateHelper(this);
        logger.i("SingleGameActivity", "Loaded Successfully");
    }

    @OnClick(value = R.id.activity_singlegame_reset)
    public void resetClick() {
        logger.i("SingleGameActivity", "Resetting board...");
        clear();
        if (aiMark == CellState.XMark) {
            getAIMove(gameBoard);
        }
    }

    private void clear() {
        logger.i("SingleGameActivity", "Clearing the board");

        for (int item : idList) {
            TextView cell = (TextView) findViewById(item);
            cell.setText("");
        }

        isOver = false;
        moveCount = 0;
        gameBoard.clear();
    }

    @OnClick(value = R.id.activity_singlegame_radioBtns)
    public void onRadioButtonClicked(View view) {
        logger.i("SingleGameActivity", "RadioBtn Clicked.");

        View viewChild = ((ViewGroup) view).getChildAt(0);
        switch (viewChild.getId()) {
            case R.id.radio_X:
                logger.i("SingleGameActivity", "Player is X");
                mark = CellState.XMark;
                aiMark = CellState.OMark;
                clear();
                break;
            case R.id.radio_O:
                logger.i("SingleGameActivity", "Player is O");
                mark = CellState.OMark;
                aiMark = CellState.XMark;
                clear();
                getAIMove(gameBoard);
                break;
        }
    }

    public void cellClick(View v) {
        TextView cell = (TextView) findViewById(v.getId());
        Point pointClicked = GetPlayerClickPoint(cell);

        gameBoard.placeMark(pointClicked, mark);
        cell.setText(cellStateHelper.CellStateToString(mark));
        EndOfTurn(mark);
    }

    private Point GetPlayerClickPoint(TextView cell) {
        String content = (String) cell.getText();

        CellState current = cellStateHelper.StringToCellState(content);

        if (current == CellState.Empty && !isOver) {
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
                case R.id.cell33:
                    return new Point(2, 2);
            }
        }
        return new Point(0, 0);
    }

    private void EndOfTurn(CellState mark) {
        moveCount++;
        isOver = checkEnd(mark);

        if (!isOver && mark != aiMark) {
            getAIMove(gameBoard);
        }
    }

    private boolean checkEnd(CellState player) {
        if (gameBoard.isWinner()) {
            logger.i("SingleGameActivity", "Winner");
            announce(true, player.toString());
            return true;
        } else if (moveCount >= 9) {
            logger.i("SingleGameActivity", "Draw");
            announce(false, player.toString());
            return true;
        }
        return false;
    }

    private void announce(boolean endState, String player) {
        if (endState) {
            player = player + " wins!";
        } else {
            player = "It's a draw!";
        }

        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_LONG);
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
