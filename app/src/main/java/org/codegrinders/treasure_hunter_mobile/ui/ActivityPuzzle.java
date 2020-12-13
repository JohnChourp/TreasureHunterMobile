package org.codegrinders.treasure_hunter_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.MapData;
import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroInstance;

public class ActivityPuzzle extends AppCompatActivity {

    Button bt_leaderBoard;
    Button bt_continue;
    TextView tv_question;
    TextView tv_username;
    TextView tv_points;
    EditText et_answer;

    RetroInstance retroInstance = new RetroInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        bt_leaderBoard = findViewById(R.id.bt_leaderBoard);
        bt_continue = findViewById(R.id.bt_continue);
        tv_question = findViewById(R.id.tv_question);
        tv_username = findViewById(R.id.tv_username);
        tv_points = findViewById(R.id.tv_points);
        et_answer = findViewById(R.id.et_answer);
        bt_leaderBoard.setOnClickListener(v -> openActivityLeaderBoard());

        retroInstance.setQuestionNumber(MapData.searchNameList());

        retroInstance.setCallListener(new RetroCallBack() {
            @Override
            public void onCallFinished(String callType) {
                if(callType.equals("Users")){
                    tv_username.setText(retroInstance.getUsers().get(0).getUsername());
                    tv_points.setText(String.valueOf(retroInstance.getUsers().get(0).getPoints()));
                }
                if(callType.equals("Puzzles")){
                    tv_question.setText(retroInstance.getQuestion());
                }

            }

            @Override
            public void onCallFailed(String errorMessage) {
                    tv_question.setText(errorMessage);
            }
        });
        retroInstance.puzzlesGetRequest();
        retroInstance.usersGetRequest();

        bt_continue.setOnClickListener(v -> {

            if (retroInstance.isCorrect(et_answer.getText().toString())) {
                Toast.makeText(this, "CORRECT", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "WRONG", Toast.LENGTH_LONG).show();
            }
            et_answer.setText("");
        });
    }

    private void openActivityLeaderBoard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
        startActivity(intent);
    }
}