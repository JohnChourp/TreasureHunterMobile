package org.codegrinders.treasure_hunter_mobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityLeaderboard extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        APIService apiService = RetroInstance.get();

        Call<List<Users>> callUsers = apiService.getUsers();
        callUsers.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> pointsList = response.body();
                String[] leaderboard = new String[pointsList.size()];
                for(int i=0; i<pointsList.size();i++){
                    leaderboard[i] = pointsList.get(i).getUsername()
                            + " : " + pointsList.get(i).getPoints();
                }
                listView.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, leaderboard));
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });

    }








}
