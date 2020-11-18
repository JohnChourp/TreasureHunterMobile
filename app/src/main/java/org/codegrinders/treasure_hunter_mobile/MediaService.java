package org.codegrinders.treasure_hunter_mobile;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MediaService extends Service {

    private final IBinder mBinder = new MediaBinder();

    public class MediaBinder extends Binder {
        MediaService getService() {
            return MediaService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        int i;
        for(i=0;i<Sound.entryCount;i++){
            pause(i);
        }
    }

    //Παίζει να μη χρειάζεται αλά το αφήνω σαν σχόλιο.
    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void  init(int index, int vol, boolean looping){
        AudioData data = Sound.get(index);
        data.player = MediaPlayer.create(this, data.resource);
        data.player.setLooping(looping);
        data.looping = looping;
        volume(index, vol);
    }

    public void play(int index, int pos){
        AudioData data = Sound.get(index);
        if(data.type.equals("sound")){
            volume(index, Sound.soundVol); //ενημέρωση volume.
        }
        if(data.allowPlaying){
            data.player.seekTo(pos);
            data.player.start();
        }
        data.allowPlaying = (!data.looping && !data.type.equals("music"));
    }

    public  void  pause(int index){
        AudioData data = Sound.get(index);

        if(!data.allowPlaying) {
            data.player.pause();
            data.position = data.player.getCurrentPosition();
            data.allowPlaying = true;
        }
    }

    public  void  stop(int index){
        AudioData data = Sound.get(index);

        if(!data.allowPlaying) {
            data.player.pause();
            data.position = 0;
            data.allowPlaying = true;
        }
    }

    public void volume(int index, int vol){
        AudioData data = Sound.get(index);

        if(vol>=0 && vol<=100){
            float fvol = (float)(vol*0.01);
            data.player.setVolume(fvol,fvol);
        }else{
            data.player.setVolume(0,0);
        }
    }

    public void setAllmusicVol(int volume){
        int i;
        for (i=0;i<Sound.entryCount;i++){
            if(Sound.get(i).type.equals("music")){
                volume(i, volume);
            }
        }
    }
}