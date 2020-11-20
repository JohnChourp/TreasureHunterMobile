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

public class ActivityPuzzles extends AppCompatActivity {

    Button bt_continue;
    TextView tv_question;
    EditText et_answer;
    List<Puzzles> puzzles;
    int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        bt_continue = findViewById(R.id.bt_continue);
        tv_question = findViewById(R.id.tv_question);
        et_answer = findViewById(R.id.et_answer);

        APIService apiService = RetroInstance.get();

        Call<List<Users>> callUsers = apiService.getUsers();
        Call<List<Puzzles>> callPuzzles = apiService.getPuzzles();

        callPuzzles.enqueue(new Callback<List<Puzzles>>() {
            @Override
            public void onResponse(Call<List<Puzzles>> call, Response<List<Puzzles>> response) {
                if (!response.isSuccessful()) {
                    tv_question.setText("code: " + response.code());
                    return;
                }
                puzzles = response.body();
                tv_question.setText(puzzles.get(questionNumber).getQuestion());
            }

            @Override
            public void onFailure(Call<List<Puzzles>> call, Throwable t) {
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
    private void openActivityStart()
    {

        Intent intent = new Intent(this, ActivityStart.class);
        startActivity(intent);
    }

}