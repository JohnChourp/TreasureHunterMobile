package org.codegrinders.tresure_hunter_mobile;

public class AudioData {
    //Μπακάλικη μέθοδος δυστυχώς δεν κυκλοφορει αρκετή πληροφορία online.
    //Το intent.putExtra που δοκίμασα δεν δούλεψε.
    //Αν είναι να μπουν κιάλοι ήχοι βγάζουμε τα static από δω και θα γίνει static λίστα σε καινουρια κλάση με AudioData και κάποιο id για να τα βρίσκουμε.
    //Όταν οι τροχοί που έχεις δεν δουλεύουν τότε αναγκαστικά τους επανεφεύρεις.

    public static int resource;
    public static int position;
    public static int volume;
    public static boolean looping;
    public static boolean playing;
    void init(int resource, int position, int volume, boolean looping, boolean playing){
        this.resource = resource;
        this.position = position;
        this.volume = volume;
        this.looping = looping;
        this.playing = playing;
    }
}
