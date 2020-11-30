package org.codegrinders.treasure_hunter_mobile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.settings.MediaService;
import org.codegrinders.treasure_hunter_mobile.settings.Settings;
import org.codegrinders.treasure_hunter_mobile.settings.Sound;

public class ActivityStart extends AppCompatActivity
{
    MediaService audioService;
    Intent intent;
    Button bt_play;
    Button bt_map;
    Button bt_settings;
    Button bt_puzzles;
    Button bt_leaderBoard;

    int backgroundMusic;
    int buttonSound;
    boolean isBound =false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bt_play = findViewById(R.id.bt_play);
        bt_puzzles = findViewById(R.id.bt_continue);
        bt_map = findViewById(R.id.bt_map);
        bt_leaderBoard = findViewById(R.id.bt_leaderBoard);
        bt_settings = findViewById(R.id.bt_settings);

        bt_play.setOnClickListener(v -> {
            audioService.play(buttonSound,0);
            openActivityLogin();
        });
        bt_puzzles.setOnClickListener(v -> openActivityPuzzles());
        bt_map.setOnClickListener(v -> openActivityMap());
        bt_leaderBoard.setOnClickListener(v -> openActivityLeaderBoard());

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

    private void openActivityPuzzles()
    {
        Intent intent = new Intent(this, ActivityPuzzle.class);
        startActivity(intent);
    }

    private void openActivityMap() {
        Intent intent = new Intent(this, ActivityMap.class);
        startActivity(intent);
    }

    private void openActivityLeaderBoard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
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
            Settings.init(getApplicationContext());

            if(Sound.firstInit){
                backgroundMusic = Sound.add(R.raw.wanabe_epic_music,"music");
                buttonSound = Sound.add(R.raw.pop, "sound");
                audioService.init(backgroundMusic, Settings.musicVol, true);
                audioService.init(buttonSound, Settings.soundVol, false);
                Sound.firstInit =false;
            }else{
                backgroundMusic = Sound.searchByResId(R.raw.wanabe_epic_music);
                buttonSound = Sound.searchByResId(R.raw.pop);
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