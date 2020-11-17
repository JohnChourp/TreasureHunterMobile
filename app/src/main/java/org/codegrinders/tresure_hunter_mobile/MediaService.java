package org.codegrinders.tresure_hunter_mobile;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MediaService extends Service {

    private MediaPlayer player;

    private final IBinder mBinder = new MediaBinder();

    public class MediaBinder extends Binder {
        MediaService getService() {
            return MediaService.this;
        }
    }


    @Override
    public void onCreate() {
        //Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()){
            player.pause();
            sendPos(player.getCurrentPosition());
            player.release();
        }
    }

    //Παίζει να μη χρειάζεται.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO write your own code
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void  initAudioFile(int resource, int position, int volume, boolean looping, boolean playOnInit){
        player = MediaPlayer.create(this, resource);
        player.setLooping(looping);
        player.seekTo(position);
        volume(volume);
        if(playOnInit){
            player.start();
            AudioData.playing=true;
        }
    }

    public void volume(int vol){
        if(vol>=0 && vol<=100){
            float fvol = (float)(vol*0.01);
            player.setVolume(fvol,fvol);
        }else{
            player.setVolume(0,0);
        }
    }

    public void sendPos(int pos){
        AudioData.position=pos;
        AudioData.playing=false;
        Toast.makeText(this, "position: "+AudioData.position, Toast.LENGTH_LONG).show();

    }
}