package org.codegrinders.treasure_hunter_mobile.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.model.User;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;
import org.codegrinders.treasure_hunter_mobile.retrofit.UsersCall;
import org.codegrinders.treasure_hunter_mobile.settings.MediaService;
import org.codegrinders.treasure_hunter_mobile.settings.Sound;

import java.util.List;

public class ActivityLeaderBoard extends AppCompatActivity {

    ListView listView;
    UsersCall usersCall = new UsersCall();
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = findViewById(R.id.listView);

        RetroCallBack retroCallBack = new RetroCallBack() {
            @Override
            public void onCallFinished(String callType) {
                    getLeaderBoard();
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


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            MediaService audioService = binder.getService();
            isBound = true;
            audioService.stop(Sound.gameMusic);
            audioService.play(Sound.menuMusic, Sound.get(Sound.menuMusic).position);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }
}
