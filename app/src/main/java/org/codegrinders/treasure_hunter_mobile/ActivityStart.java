package org.codegrinders.treasure_hunter_mobile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStart extends AppCompatActivity
{
    MediaService audioService;
    Intent intent;
    Button bt_play;
    Button bt_settings;
    Button bt_puzzles;
    Button bt_leaderboard;

    int backgroundMusic;
    int buttonSound;
    boolean isBound =false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bt_play = findViewById(R.id.bt_play);
        bt_settings = findViewById(R.id.bt_settings);
        bt_puzzles = findViewById(R.id.bt_continue);
        bt_leaderboard = findViewById(R.id.bt_leaderboard);

        bt_puzzles.setOnClickListener(v -> openActivityPuzzles());
        bt_leaderboard.setOnClickListener(v -> openActivityLeaderboard());
        bt_play.setOnClickListener(v -> {
            audioService.play(buttonSound,0);
            openActivityLogin();
        });
        bt_settings.setOnClickListener(v -> {
            audioService.play(buttonSound,0);
            openActivitySettings();
        });

    }


    private void openActivityLogin()
    {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

    private void openActivityLeaderboard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
        startActivity(intent);
    }

    private void openActivityPuzzles()
    {
        Intent intent = new Intent(this, ActivityPuzzles.class);
        startActivity(intent);
    }

    private void openActivitySettings()
    {
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;

            if(Sound.firstInit){

                backgroundMusic = Sound.add(R.raw.wanabe_epic_music,"music");
                buttonSound = Sound.add(R.raw.pop, "sound");
                audioService.init(backgroundMusic, Sound.musicVol, true);
                audioService.init(buttonSound, Sound.soundVol, false);
                Sound.firstInit =false;
            }else{
                backgroundMusic = Sound.searchByResid(R.raw.wanabe_epic_music);
                buttonSound = Sound.searchByResid(R.raw.pop);
            }
                audioService.play(backgroundMusic, Sound.get(backgroundMusic).position);
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