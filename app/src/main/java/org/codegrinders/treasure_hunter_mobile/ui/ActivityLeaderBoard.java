package org.codegrinders.treasure_hunter_mobile.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;
import org.codegrinders.treasure_hunter_mobile.retrofit.UsersCall;
import org.codegrinders.treasure_hunter_mobile.tables.User;

import java.util.List;

public class ActivityLeaderBoard extends AppCompatActivity {

    private ListView listView;
    UsersCall usersCall = new UsersCall();
    RetroCallBack retroCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = findViewById(R.id.listView);

        retroCallBack = new RetroCallBack() {
            @Override
            public void onCallFinished(String callType) {
                if (callType.equals("Users")) {
                    getLeaderBoard();
                }
            }

            @Override
            public void onCallFailed(String errorMessage) {

            }
        };

        usersCall.setCallBack(retroCallBack);

        usersCall.usersGetRequest();
    }

    void getLeaderBoard() {
        List<User> pointsList = usersCall.getUsers();
        String[] leaderBoard = new String[pointsList.size()];
        for (int i = 0; i < pointsList.size(); i++) {
            leaderBoard[i] = pointsList.get(i).getUsername()
                    + " : " + pointsList.get(i).getPoints();
        }
        listView.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, leaderBoard));
    }
}
