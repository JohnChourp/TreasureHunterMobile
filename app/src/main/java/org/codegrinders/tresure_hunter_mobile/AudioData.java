package org.codegrinders.tresure_hunter_mobile;

import android.media.MediaPlayer;

public class AudioData {

    MediaPlayer player;
    public int resource;
    public int position;
    public int volume;
    public boolean looping;
    public boolean allowPlaying;
    public String type;

    public AudioData(int resource, int volume, String type){
        this.resource = resource;
        this.volume = volume;
        this.type = type;
        position = 0;
        looping = false;
        allowPlaying = true;
    }
}
