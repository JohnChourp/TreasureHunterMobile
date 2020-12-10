package org.codegrinders.treasure_hunter_mobile.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroInstance;
import org.codegrinders.treasure_hunter_mobile.tables.User;

public class ActivityLeaderBoard extends AppCompatActivity {

    private ListView listView;
    RetroInstance retroInstance = new RetroInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = findViewById(R.id.listView);
        RetroInstance.initializeAPIService();

        retroInstance.setCallListener(new RetroCallBack() {
            @Override
            public void onCallUsersFinished() {
                getLeaderBoard();
            }

            @Override
            public void onCallPuzzlesFinished() {

            }

            @Override
            public void onCallFailed(String errorMessage) {
            }
        });
        retroInstance.usersGetRequest();
    }

    void getLeaderBoard(){
        List<User> pointsList = retroInstance.getUsers();
        String[] leaderBoard = new String[pointsList.size()];
        for(int i=0; i<pointsList.size();i++){
            leaderBoard[i] = pointsList.get(i).getUsername()
                    + " : " + pointsList.get(i).getPoints();
        }
        listView.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, leaderBoard));
    }
}
