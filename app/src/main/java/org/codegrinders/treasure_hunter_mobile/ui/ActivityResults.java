package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.settings.MediaService;

public class ActivityResults extends AppCompatActivity {

    TextView tv_result;
    Button bt_results_won;

    MediaService audioService;
    Intent intent;
    boolean isBound = false;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        bt_results_won = findViewById(R.id.bt_won);
        tv_result = findViewById(R.id.tv_result);
        bt_results_won.setOnClickListener(v -> openActivityLeaderBoard());

        if(ActivityMap.usersCall.user.getPoints() > 400){
            tv_result.setText(ActivityMap.usersCall.user.getUsername() + " You Won the Game!!!!");
        }else{
            tv_result.setText(ActivityMap.usersCall.user.getUsername() + " You Didn't Win the Game,Try Again Next Time");
        }
    }

    private void openActivityLeaderBoard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
        startActivity(intent);
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        intent = new Intent(this, MediaService.class);
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