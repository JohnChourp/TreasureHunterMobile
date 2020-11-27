package org.codegrinders.treasure_hunter_mobile;

import android.widget.Toast;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    static int questionNumber = 0;
    static List<Puzzle> puzzles;

    static APIService get(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIService.class);
    }


    static String getQuestion(){
        String ret = "";
        if (questionNumber < puzzles.size()) {
            ret =  puzzles.get(questionNumber).getQuestion();
        }
        return ret;
    }

    static boolean isCorrect(String input){
        boolean correct = false;
        if (questionNumber < puzzles.size()){
            if(puzzles.get(questionNumber).getAnswer().equals(input)){
                correct = true;
                questionNumber++;
            }
        }
        return correct;
    }
}
