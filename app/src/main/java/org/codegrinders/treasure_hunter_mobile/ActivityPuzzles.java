package org.codegrinders.treasure_hunter_mobile;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityPuzzles extends AppCompatActivity {

    Button bt_continue;
    TextView tv_question;
    EditText et_answer;

    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        bt_continue = findViewById(R.id.bt_continue);
        tv_question = findViewById(R.id.tv_question);
        et_answer = findViewById(R.id.et_answer);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);

        Call<List<Users>> callUsers = apiService.getUsers();
        Call<List<Puzzles>> callPuzzles = apiService.getPuzzles();

        callPuzzles.enqueue(new Callback<List<Puzzles>>() {
            @Override
            public void onResponse(Call<List<Puzzles>> call, Response<List<Puzzles>> response) {
                if (!response.isSuccessful()) {
                    tv_question.setText("code: " + response.code());
                    return;
                }
                List<Puzzles> puzzles = response.body();
                answer = puzzles.get(0).getAnswer();
                tv_question.setText(puzzles.get(0).getQuestion());
            }

            @Override
            public void onFailure(Call<List<Puzzles>> call, Throwable t) {
                tv_question.setText(t.getMessage());
            }
        });

        bt_continue.setOnClickListener(v -> {
            //Toast.makeText(this, "res= "+answer, Toast.LENGTH_LONG).show();
            if(et_answer.getText().toString().equals(answer)){
                Toast.makeText(this, "!!! CORRECT !!!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "WRONG ANSWER :(", Toast.LENGTH_LONG).show();
            }
        });

    }


}