package org.codegrinders.treasure_hunter_mobile;
import java.util.ArrayList;
import java.util.List;

public class MapData {
    public static String markerName;
    public static List <String> names = new ArrayList<>();

    public static int searchNameList() {
        int i;
        for (i = 0; i < names.size(); i++) {
            if (names.get(i).equals(markerName)) {
                break;
            }
        }
        return i;
    }
}