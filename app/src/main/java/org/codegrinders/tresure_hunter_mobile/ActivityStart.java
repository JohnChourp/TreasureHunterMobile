package org.codegrinders.tresure_hunter_mobile;

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
    boolean isBound =false;
    Intent intent;
    Button bt_play;

    boolean firstInit=true;

    AudioData data = new AudioData();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bt_play = findViewById(R.id.bt_play);

        bt_play.setOnClickListener(v -> openActivityLogin());
    }


    private void openActivityLogin()
    {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;
            if(firstInit){
                audioService.initAudioFile(R.raw.beep,0, 100,true,true);
                data.init(R.raw.beep, 0,100,true, true);
                firstInit=false;
            }else{
                if(!AudioData.playing) {
                    audioService.initAudioFile(AudioData.resource, AudioData.position, AudioData.volume, AudioData.looping, true);
                }
            }
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