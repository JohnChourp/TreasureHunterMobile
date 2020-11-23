package org.codegrinders.treasure_hunter_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPuzzle extends AppCompatActivity {

    Button bt_leaderboard;
    Button bt_continue;
    TextView tv_question;
    TextView tv_username;
    TextView tv_points;
    EditText et_answer;
    List<Puzzle> puzzles;
    int questionNumber = 0;

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

        APIService apiService = RetroInstance.get();

        Call<List<User>> callUsers = apiService.getUsers();

        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> usernameList = response.body();
                assert usernameList != null;
                tv_username.setText(usernameList.get(0).getUsername());
                tv_points.setText(String.valueOf(usernameList.get(0).getPoints()));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


        Call<List<Puzzle>> callPuzzles = apiService.getPuzzles();

        callPuzzles.enqueue(new Callback<List<Puzzle>>() {
            @Override
            public void onResponse(Call<List<Puzzle>> call, Response<List<Puzzle>> response) {
                if (!response.isSuccessful()) {
                    tv_question.setText("code: " + response.code());
                    return;
                }
                puzzles = response.body();
                tv_question.setText(puzzles.get(questionNumber).getQuestion());
            }

            @Override
            public void onFailure(Call<List<Puzzle>> call, Throwable t) {
                tv_question.setText(t.getMessage());
            }
        });
        bt_continue.setOnClickListener(v -> {

            if(et_answer.getText().toString().equals(puzzles.get(questionNumber).getAnswer()))
            {
                Toast.makeText(this, "!!! CORRECT !!!", Toast.LENGTH_LONG).show();
                et_answer.setText("");
                questionNumber +=1;

                if(questionNumber < puzzles.size()){
                    tv_question.setText(puzzles.get(questionNumber).getQuestion());
                }else{
                    questionNumber = 0;
                    openActivityStart();
                }
            }
            else{
                Toast.makeText(this, "WRONG ANSWER :(", Toast.LENGTH_LONG).show();
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