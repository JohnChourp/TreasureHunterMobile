package org.codegrinders.treasure_hunter_mobile;

import java.util.ArrayList;

public class Sound {
    static ArrayList<AudioData> audioList = new ArrayList<>();
    static int entryCount = 0;
    static boolean firstInit = true;

    public static int add(int resource, String type){
        audioList.add(new AudioData(resource, type));
        entryCount++;
        return entryCount-1;
    }

    public static AudioData get(int index){
        return audioList.get(index);
    }

    public static int searchByResid(int resid){
        int i;
        for(i=0;i<entryCount;i++){
            if(get(i).resource == resid){
                break;
            }
        }
        return i;
    }
}
