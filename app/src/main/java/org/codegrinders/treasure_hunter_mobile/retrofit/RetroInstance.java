package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.codegrinders.treasure_hunter_mobile.tables.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    private int questionNumber = 0;
    private List<Puzzle> puzzles;
    private List<User> users;

<<<<<<< app/src/main/java/org/codegrinders/treasure_hunter_mobile/retrofit/RetroInstance.java
    private RetroCallBack callBack;
    static APIService apiService;
    Call<List<User>> callUsers;
    Call<List<Puzzle>> callPuzzles;

    public static APIService initializeAPIService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(APIService.class);
    }

    public String getQuestion() {
        String ret = "";

        if (questionNumber < puzzles.size()) {
            ret = puzzles.get(questionNumber).getQuestion();
        }
        return ret;
    }

    public boolean isCorrect(String input) {
        boolean correct = false;

        if (questionNumber < puzzles.size()) {
            if (puzzles.get(questionNumber).getAnswer().equals(input)) {
                correct = true;
                questionNumber++;
            }
        }
        return correct;
    }

    public void usersGetRequest() {
        callUsers = initializeAPIService().getUsers();

        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    callBack.onCallFailed("code: " + response.code());
                    return;
                }
                users = response.body();
                callBack.onCallUsersFinished();
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                callBack.onCallFailed(t.getMessage());
            }
        });
    }

    public void puzzlesGetRequest() {

        callPuzzles = initializeAPIService().getPuzzles();

        callPuzzles.enqueue(new Callback<List<Puzzle>>() {
            @Override
            public void onResponse(@NotNull Call<List<Puzzle>> call, @NotNull Response<List<Puzzle>> response) {
                if (!response.isSuccessful()) {
                    callBack.onCallFailed("code: " + response.code());
                    return;
                }
                puzzles = response.body();
                callBack.onCallPuzzlesFinished();
            }

            @Override
            public void onFailure(@NotNull Call<List<Puzzle>> call, @NotNull Throwable t) {
                callBack.onCallFailed(t.getMessage());
            }
        });
    }

    public void setCallListener(RetroCallBack callBack) {
        this.callBack = callBack;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setCallUsers(Call<List<User>> callUsers) {
        this.callUsers = callUsers;
    }

    public void setCallPuzzles(Call<List<Puzzle>> callPuzzles) {

        this.callPuzzles = callPuzzles;
    }
}
