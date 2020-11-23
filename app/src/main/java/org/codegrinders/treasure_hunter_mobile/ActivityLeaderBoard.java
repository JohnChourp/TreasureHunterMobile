package org.codegrinders.treasure_hunter_mobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityLeaderBoard extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = findViewById(R.id.listView);

        APIService apiService = RetroInstance.get();

        Call<List<User>> callUsers = apiService.getUsers();
        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> pointsList = response.body();
                String[] leaderBoard = new String[pointsList.size()];
                for(int i=0; i<pointsList.size();i++){
                    leaderBoard[i] = pointsList.get(i).getUsername()
                            + " : " + pointsList.get(i).getPoints();
                }
                listView.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, leaderBoard));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

}
