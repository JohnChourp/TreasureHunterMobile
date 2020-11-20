package org.codegrinders.treasure_hunter_mobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        Call<List<Users>> call = apiService.getUsers();
        Call<List<Puzzles>> call2 = apiService.getPuzzles();

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (!response.isSuccessful()) {
                    tv_question.setText("code: " + response.code());
                }
                List<Users> users = response.body();
                for (Users user : users) {
                    String content = "";
                    content += "ID: " + user.getId() + "\n";
                    content += "EMAIL: " + user.getEmail() + "\n";
                    content += "USERNAME: " + user.getUsername() + "\n";
                    content += "PASSWORD: " + user.getPassword() + "\n";
                    content += "POINTS: " + user.getPoints() + "\n\n";

                    tv_question.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                tv_question.setText(t.getMessage());
            }
        });


        bt_continue.setOnClickListener(v -> {
            //tv_question.setText("");
        });

    }


}