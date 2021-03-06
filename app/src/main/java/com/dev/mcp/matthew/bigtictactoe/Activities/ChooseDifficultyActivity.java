package com.dev.mcp.matthew.bigtictactoe.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dev.mcp.matthew.bigtictactoe.Core.MyFullScreenActivity;
import com.dev.mcp.matthew.bigtictactoe.Enums.ComputerPlayerType;
import com.dev.mcp.matthew.bigtictactoe.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseDifficultyActivity extends MyFullScreenActivity {

    private String computerDifficultyKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);
        ButterKnife.bind(this);
        computerDifficultyKey = getString(R.string.computerDifficulty_Key);
    }

    @OnClick(value = R.id.difficulty_easy_btn)
    public void easyAIBtnClick() {
        final Context context = this;
        Intent intent = new Intent(context, SingleGameActivity.class);
        intent.putExtra(computerDifficultyKey, ComputerPlayerType.Easy.name());
        startActivity(intent);
    }

    @OnClick(value = R.id.difficulty_medium_btn)
    public void mediumAIBtnClick() {
        final Context context = this;
        Intent intent = new Intent(context, SingleGameActivity.class);
        intent.putExtra(computerDifficultyKey, ComputerPlayerType.Medium.name());
        startActivity(intent);
    }

    @OnClick(value = R.id.difficulty_hard_btn)
    public void hardAIBtnClick() {
        final Context context = this;
        Intent intent = new Intent(context, SingleGameActivity.class);
        intent.putExtra(computerDifficultyKey, ComputerPlayerType.Hard.name());
        startActivity(intent);
    }

    @OnClick(value = R.id.difficulty_random_btn)
    public void randomAIBtnClick() {
        final Context context = this;
        Intent intent = new Intent(context, SingleGameActivity.class);
        intent.putExtra(computerDifficultyKey, ComputerPlayerType.Random.name());
        startActivity(intent);
    }

    @OnClick(value = R.id.difficulty_home_btn)
    public void homeBtnClick() {
        final Context context = this;
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }
}
