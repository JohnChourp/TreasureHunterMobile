package org.codegrinders.tresure_hunter_mobile;

import java.util.ArrayList;

//Μπακάλικη μέθοδος δυστυχώς δεν κυκλοφορει αρκετή πληροφορία online.
//Το intent.putExtra που δοκίμασα δεν δούλεψε.
//Όταν οι τροχοί που έχεις δεν δουλεύουν τότε αναγκαστικά τους επανεφεύρεις.

public class Sound {
    static ArrayList<AudioData> audioList = new ArrayList<>();
    static int musicVol = 50;
    static int soundVol = 100;
    static int entryCount = 0;
    static boolean firstInit = true;

    public static int add(int resource, int volume, String type){
        audioList.add(new AudioData(resource, volume, type));
        entryCount++;
        return entryCount-1;
    }

    public static AudioData get(int index){
        return audioList.get(index);
    }

    //Αναζητά έναν ήχο στην λίστα σύμφωνα με το resid του πχ R.raw.beep και επιστρέφει την θέση του στην λίστα αν δεν βρεθεί επιστρέφεται η τελευταία θέση.
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
