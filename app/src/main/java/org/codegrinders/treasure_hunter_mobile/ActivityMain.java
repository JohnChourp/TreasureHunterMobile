package org.codegrinders.treasure_hunter_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class ActivityMain extends AppCompatActivity
{

    MediaService audioService;
    boolean isBound =false;
    Intent intent;

    int backgroundMusic;
    int buttonSound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;

            backgroundMusic = Sound.searchByResid(R.raw.beep);
            buttonSound = Sound.searchByResid(R.raw.pop);
            audioService.stop(backgroundMusic);//Επαναφορά ήχου στην αρχή και παύση.
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