package org.codegrinders.treasure_hunter_mobile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class ActivitySettings extends AppCompatActivity {

    MediaService audioService;
    boolean isBound =false;
    Intent intent;

    SeekBar musicVolSlider;
    SeekBar soundVolSlider;
    ImageButton muteMusic;
    ImageButton muteSounds;

    int backgroundMusic;
    int buttonSound;

    boolean musicIsMuted;
    boolean soundsAreMuted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        musicVolSlider = findViewById(R.id.sb_musicVol);
        soundVolSlider = findViewById(R.id.sb_soundVol);

        muteMusic = findViewById(R.id.bt_muteMusic);
        muteSounds = findViewById(R.id.bt_muteSounds);

        musicIsMuted = (Sound.musicVol==0);
        soundsAreMuted = (Sound.soundVol==0);

        if(musicIsMuted){
            muteMusic.setImageResource(R.drawable.muted);
        }else{
            muteMusic.setImageResource(R.drawable.unmuted);
        }

        if(soundsAreMuted){
            muteSounds.setImageResource(R.drawable.muted);
        }else{
            muteSounds.setImageResource(R.drawable.unmuted);
        }

        musicVolSlider.setProgress(Sound.musicVol);
        soundVolSlider.setProgress(Sound.soundVol);;



        muteMusic.setOnClickListener(v -> {
            if(musicIsMuted){
                muteMusic.setImageResource(R.drawable.unmuted);
                musicIsMuted = false;
                Sound.musicVol = 60;
                musicVolSlider.setProgress(60);
            }else{
                muteMusic.setImageResource(R.drawable.muted);
                musicIsMuted = true;
                Sound.musicVol = 0;
                musicVolSlider.setProgress(0);
            }
        });



        muteSounds.setOnClickListener(v -> {
            if(soundsAreMuted){
                muteSounds.setImageResource(R.drawable.unmuted);
                soundsAreMuted = false;
                Sound.soundVol = 60;
                soundVolSlider.setProgress(60);
            }else{
                muteSounds.setImageResource(R.drawable.muted);
                soundsAreMuted = true;
                Sound.soundVol = 0;
                soundVolSlider.setProgress(0);
            }
        });



        musicVolSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Sound.musicVol = progress;
                if(progress == 0){
                    muteMusic.setImageResource(R.drawable.muted);
                }else{
                    muteMusic.setImageResource(R.drawable.unmuted);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        soundVolSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Sound.soundVol = progress;
                if(progress == 0){
                    muteSounds.setImageResource(R.drawable.muted);
                }else{
                    muteSounds.setImageResource(R.drawable.unmuted);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }




    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;

            backgroundMusic = Sound.searchByResid(R.raw.beep);
            buttonSound = Sound.searchByResid(R.raw.pop);
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
        // Bind to LocalService
        intent = new Intent(this, MediaService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }
}