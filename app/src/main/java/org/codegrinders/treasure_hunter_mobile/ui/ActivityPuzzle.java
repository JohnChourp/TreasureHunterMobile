package org.codegrinders.treasure_hunter_mobile.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.model.Puzzle;
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
    Puzzle puzzle;

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

                if (callType.equals("postAnswer")) {
                    puzzle = puzzlesCall.getPuzzle();
                    if (puzzle != null) {
                        Toast.makeText(getBaseContext(), "Answer Sent Successfully...", Toast.LENGTH_SHORT).show();
                        if (et_answer.getText().toString().equals(puzzle.getAnswer())) {
                            Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_LONG).show();
                            ActivityMap.currentMarkerData.setVisibility(false);
                            ActivityMap.currentMarker.setVisible(false);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_LONG).show();
                        }
                        et_answer.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Answer Didn't Sent...", Toast.LENGTH_SHORT).show();
                    }

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
            puzzlesCall.puzzleIsCorrect(et_answer.getText().toString());
        });
    }
}