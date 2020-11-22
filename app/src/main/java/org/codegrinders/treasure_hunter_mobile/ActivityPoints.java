package org.codegrinders.treasure_hunter_mobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPoints extends AppCompatActivity {

    TextView tv_username;
    TextView tv_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        tv_username = findViewById(R.id.tv_username);
        tv_points = findViewById(R.id.tv_points);

        APIService apiService = RetroInstance.get();
        Call<List<Users>> callUsers = apiService.getUsers();

        callUsers.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> usernameList = response.body();
                tv_username.setText(usernameList.get(0).getUsername());
                tv_points.setText(usernameList.get(0).getPoints());
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });




//        Call<userPoints> usersCall = apiService.getUserPoints();
//
//        usersCall.enqueue(new Callback<userPoints>() {
//            @Override
//            public void onResponse(Call<userPoints> call, Response<userPoints> response) {
//                String username = response.body().getUsername();
//                int points = response.body().getPoints();
//                tv_username.setText(username);
//                tv_points.setText(points);
//            }
//
//            @Override
//            public void onFailure(Call<userPoints> call, Throwable t) {
//                tv_username.setText("Null");
//                tv_points.setText("1000");
//
//            }
//        });
    }
}
