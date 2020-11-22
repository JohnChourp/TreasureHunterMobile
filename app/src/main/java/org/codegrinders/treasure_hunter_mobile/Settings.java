package org.codegrinders.treasure_hunter_mobile;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {
    static String data = "";
    static int musicVol;
    static int soundVol;

static void init(Context context){
    //read file
    try {

        InputStream inputStream = context.openFileInput("settings.conf");

        if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bufferedReader.readLine());
            inputStream.close();
            data = stringBuilder.toString();
        }
    }
    catch (FileNotFoundException e) {
        Log.e("activity", "File not found: " + e.toString());
        writeToFile("",context);
    } catch (IOException e) {
        Log.e("activity", "Can not read file: " + e.toString());
    }

    musicVol = Integer.parseInt(getElement("musicVol","60"));
    soundVol = Integer.parseInt(getElement("soundVol","60"));
    writeToFile(data, context);
}

    static int searchElement(String fileString, String element) {
        int lastElementPos = 0;
        int pos = -1;
        if (!fileString.equals("") || !element.equals("")) {
            for (int i = 0; i < fileString.length(); i++) {
                if (fileString.charAt(i) == ':') {
                    if (element.equals(fileString.substring(lastElementPos, i))) {
                        pos = i +1;
                        System.out.println("found"+pos);
                        break;
                    }
                } else if (fileString.charAt(i) == ';') {
                    lastElementPos = i + 1;
                }
            }
        }
        return pos;
    }


    public static void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("settings.conf", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    static void setElement(String element, String value) {
        int startPos = searchElement(data, element);
        if (startPos != -1) {
            int currentPos = startPos;
            while (data.charAt(currentPos) != ';') {
                currentPos++;
            }
            data = data.substring(0, startPos) + value + data.substring(currentPos, data.length());
        }else {
            data = data + element + ":" + value + ";";
        }
    }



    static String getElement(String element) {
        String value = "";
        int startPos = searchElement(data, element);
        if (startPos != -1) {
            int currentPos = startPos;
            while (data.charAt(currentPos) != ';') {
                currentPos++;
            }
            value = data.substring(startPos, currentPos);
        }

        return value;
    }


    static String getElement(String element, String valueIfNotExist){
        String value = "";
        int startPos = searchElement(data, element);
        if(startPos != -1){
            int currentPos = startPos;
            while (data.charAt(currentPos) != ';') {
                currentPos++;
            }
            value = data.substring(startPos,currentPos);
        }else{
            data = data + element + ":" + valueIfNotExist + ";";
            value = valueIfNotExist;
        }

        return value;
    }
}

