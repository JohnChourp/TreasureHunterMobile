package org.codegrinders.treasure_hunter_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityPuzzle extends AppCompatActivity {

    Button bt_leaderboard;
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

        bt_leaderboard = findViewById(R.id.bt_leaderboard);
        bt_continue = findViewById(R.id.bt_puzzles);
        tv_question = findViewById(R.id.tv_question);
        tv_username = findViewById(R.id.tv_username);
        tv_points = findViewById(R.id.tv_points);
        et_answer = findViewById(R.id.et_answer);
        bt_leaderboard.setOnClickListener(v -> openActivityLeaderboard());

        retroInstance.initializeAPIService();

        retroInstance.setCallListener(new RetroCallBack() {
            @Override
            public void onCallUsersFinished() {
                tv_username.setText(retroInstance.users.get(0).getUsername());
                tv_points.setText(String.valueOf(retroInstance.users.get(0).getPoints()));
            }

            @Override
            public void onCallPuzzlesFinished() {
                tv_question.setText(retroInstance.getQuestion());
            }

            @Override
            public void onCallFailed(String errorMessage) {
                tv_question.setText(errorMessage);
            }
        });

        retroInstance.puzzlesGetRequest();
        retroInstance.usersGetRequest();

        bt_continue.setOnClickListener(v -> {

                if(retroInstance.isCorrect(et_answer.getText().toString())){
                    Toast.makeText(this, "CORRECT", Toast.LENGTH_LONG).show();
                    if(retroInstance.questionNumber < retroInstance.puzzles.size()){
                        tv_question.setText(retroInstance.getQuestion());
                        et_answer.setText("");
                    }else{
                        retroInstance.questionNumber = 0;
                        openActivityStart();
                    }
                }else{
                    Toast.makeText(this, "WRONG", Toast.LENGTH_LONG).show();
                }
        });
    }

    private void openActivityLeaderboard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
        startActivity(intent);
    }

    private void openActivityStart()
    {
        Intent intent = new Intent(this, ActivityStart.class);
        startActivity(intent);
    }
}