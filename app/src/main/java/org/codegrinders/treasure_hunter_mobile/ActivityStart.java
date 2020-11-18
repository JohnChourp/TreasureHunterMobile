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
    boolean isBound =false;
    Intent intent;
    Button bt_play;
    Button bt_settings;

    int backgroundMusic;
    int buttonSound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bt_play = findViewById(R.id.bt_play);
        bt_settings = findViewById(R.id.bt_settings);

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
                //Τα παρακάτω πρέπει να γίνουν μόνο εδώ και σε κανένα άλο activity.
                backgroundMusic = Sound.add(R.raw.beep,"music");//Πρώτα προσθέτουμε τους ήχους.
                buttonSound = Sound.add(R.raw.pop, "sound");
                audioService.init(backgroundMusic, 60, true);//Μετά τους αρχικοποιούμε.
                audioService.init(buttonSound, 100, false);
                Sound.firstInit =false;//Τέλος setάρουμε το firstInit σε false για να μήν προστεθούν και αρχικοποιηθούν ξανά.
            }
                audioService.play(backgroundMusic, Sound.get(backgroundMusic).position);//Αναπαραγωγή ήχου.
                //audioService.stop(backgroundMusic); //Επαναφορά ήχου στην αρχή και παύση.
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