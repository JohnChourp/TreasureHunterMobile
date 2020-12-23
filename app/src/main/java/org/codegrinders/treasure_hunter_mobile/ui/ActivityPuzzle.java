package org.codegrinders.treasure_hunter_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.PuzzlesCall;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;
import org.codegrinders.treasure_hunter_mobile.retrofit.UsersCall;

public class ActivityPuzzle extends AppCompatActivity {

    Button bt_continue;
    TextView tv_question;
    TextView tv_username;
    TextView tv_points;
    EditText et_answer;

    PuzzlesCall puzzlesCall = new PuzzlesCall();
    UsersCall usersCall = new UsersCall();
    RetroCallBack retroCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        bt_continue = findViewById(R.id.bt_continue);
        tv_question = findViewById(R.id.tv_question);
        tv_username = findViewById(R.id.tv_username);
        tv_points = findViewById(R.id.tv_points);
        et_answer = findViewById(R.id.et_answer);

        retroCallBack = new RetroCallBack() {
            @Override
            public void onCallFinished(String callType) {
                if (callType.equals("Users")) {
                    tv_username.setText(usersCall.getUsers().get(0).getUsername());
                    tv_points.setText(String.valueOf(usersCall.getUsers().get(0).getPoints()));
                }
                if (callType.equals("Puzzles")) {
                    tv_question.setText(puzzlesCall.getQuestion());
                }
            }

            @Override
            public void onCallFailed(String errorMessage) {
                tv_question.setText(errorMessage);
            }
        };

        puzzlesCall.setCallBack(retroCallBack);
        usersCall.setCallBack(retroCallBack);

        puzzlesCall.puzzlesGetRequest();
        usersCall.usersGetRequest();

        bt_continue.setOnClickListener(v -> {

            if (puzzlesCall.isCorrect(et_answer.getText().toString())) {
                Toast.makeText(this, "CORRECT", Toast.LENGTH_LONG).show();
                ActivityMap.currentMarkerData.setVisibility(false);
                ActivityMap.currentMarker.setVisible(false);
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