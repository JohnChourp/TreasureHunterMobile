package org.codegrinders.treasure_hunter_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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

        musicIsMuted = (Settings.musicVol==0);
        soundsAreMuted = (Settings.soundVol==0);

        displayMuteUnMute(musicIsMuted, muteMusic);
        displayMuteUnMute(soundsAreMuted, muteSounds);

        musicVolSlider.setProgress(Settings.musicVol);
        soundVolSlider.setProgress(Settings.soundVol);

        muteMusic.setOnClickListener(v -> {
            if(musicIsMuted){
                muteMusic.setImageResource(R.drawable.unmuted);
                musicVolSlider.setProgress(60);
                musicIsMuted = false;
            }else{
                muteMusic.setImageResource(R.drawable.muted);
                musicVolSlider.setProgress(0);
                musicIsMuted = true;
            }
            Settings.setElement("musicVol",String.valueOf(Settings.musicVol));
            Settings.writeToFile(Settings.data, getApplicationContext());
        });

        muteSounds.setOnClickListener(v -> {
            if(soundsAreMuted){
                muteSounds.setImageResource(R.drawable.unmuted);
                soundVolSlider.setProgress(60);
                soundsAreMuted = false;
            }else{
                muteSounds.setImageResource(R.drawable.muted);
                soundVolSlider.setProgress(0);
                soundsAreMuted = true;
            }
            Settings.setElement("soundVol",String.valueOf(Settings.soundVol));
            Settings.writeToFile(Settings.data, getApplicationContext());
        });

        musicVolSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.musicVol = progress;
                musicIsMuted = (progress==0);
                displayMuteUnMute(musicIsMuted, muteMusic);
                audioService.setAllMusicVol(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.setElement("musicVol",String.valueOf(Settings.musicVol));
                Settings.writeToFile(Settings.data, getApplicationContext());
            }
        });

        soundVolSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.soundVol = progress;
                soundsAreMuted = (progress==0);
                displayMuteUnMute(soundsAreMuted ,muteSounds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audioService.play(buttonSound, 0);
                Settings.setElement("soundVol",String.valueOf(Settings.soundVol));
                Settings.writeToFile(Settings.data, getApplicationContext());
            }
        });

    }

    void displayMuteUnMute(boolean isMuted, ImageButton button){
        if(isMuted){
            button.setImageResource(R.drawable.muted);
        }else{
            button.setImageResource(R.drawable.unmuted);
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;

            backgroundMusic = Sound.searchByResid(R.raw.wanabe_epic_music);
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