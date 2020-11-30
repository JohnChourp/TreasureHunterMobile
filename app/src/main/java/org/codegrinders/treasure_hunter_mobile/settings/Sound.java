package org.codegrinders.treasure_hunter_mobile.settings;

import java.util.ArrayList;

public class Sound {
    static ArrayList<AudioData> audioList = new ArrayList<>();
    static int entryCount = 0;
    public static boolean firstInit = true;

    public static int add(int resource, String type){
        audioList.add(new AudioData(resource, type));
        entryCount++;
        return entryCount-1;
    }

    public static AudioData get(int index){
        return audioList.get(index);
    }

    public static int searchByResId(int resId){
        int i;
        for(i=0;i<entryCount;i++){
            if(get(i).resource == resId){
                break;
            }
        }
        return i;
    }
}
