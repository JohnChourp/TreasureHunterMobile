package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Markers;
import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.codegrinders.treasure_hunter_mobile.tables.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroGetRequest {

    private int questionNumber = 0;

    private List<Puzzle> puzzles;
    private List<User> users;
    private List<Markers> markers;

    private RetroCallBack callBack;

    private Call<List<User>> callUsers;
    private Call<List<Puzzle>> callPuzzles;
    private Call<List<Markers>> callMarkers;

    public String getQuestion() {
        return puzzles.get(questionNumber).getQuestion();
    }

    public boolean isCorrect(String input) {
        boolean correct = false;
        if (puzzles.get(questionNumber).getAnswer().equals(input)) {
            correct = true;
        }
        return correct;
    }

    public void usersGetRequest() {
        callUsers = RetroInstance.initializeAPIService().getUsers();
        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    callBack.onCallFailed("code: " + response.code());
                    return;
                }
                users = response.body();
                callBack.onCallFinished("Users");
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                callBack.onCallFailed(t.getMessage());
            }
        });
    }

    public void puzzlesGetRequest() {
        callPuzzles = RetroInstance.initializeAPIService().getPuzzles();
        callPuzzles.enqueue(new Callback<List<Puzzle>>() {
            @Override
            public void onResponse(@NotNull Call<List<Puzzle>> call, @NotNull Response<List<Puzzle>> response) {
                if (!response.isSuccessful()) {
                    callBack.onCallFailed("code: " + response.code());
                    return;
                }
                puzzles = response.body();
                callBack.onCallFinished("Puzzles");
            }

            @Override
            public void onFailure(@NotNull Call<List<Puzzle>> call, @NotNull Throwable t) {
                callBack.onCallFailed(t.getMessage());
            }
        });
    }

    public void markersGetRequest() {
        callMarkers = RetroInstance.initializeAPIService().getMarkers();
        callMarkers.enqueue(new Callback<List<Markers>>() {
            @Override
            public void onResponse(@NotNull Call<List<Markers>> call, @NotNull Response<List<Markers>> response) {
                if (!response.isSuccessful()) {
                    callBack.onCallFailed("code: " + response.code());
                    return;
                }

                markers = response.body();
                callBack.onCallFinished("Markers");
            }

            @Override
            public void onFailure(@NotNull Call<List<Markers>> call, @NotNull Throwable t) {
                callBack.onCallFailed(t.getMessage());
            }
        });

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

    public List<Markers> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Markers> markers) {
        this.markers = markers;
    }

    public RetroCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(RetroCallBack callBack) {
        this.callBack = callBack;
    }

    public Call<List<User>> getCallUsers() {
        return callUsers;
    }

    public void setCallUsers(Call<List<User>> callUsers) {
        this.callUsers = callUsers;
    }

    public Call<List<Puzzle>> getCallPuzzles() {
        return callPuzzles;
    }

    public void setCallPuzzles(Call<List<Puzzle>> callPuzzles) {
        this.callPuzzles = callPuzzles;
    }

    public Call<List<Markers>> getCallMarkers() {
        return callMarkers;
    }

    public void setCallMarkers(Call<List<Markers>> callMarkers) {
        this.callMarkers = callMarkers;
    }
}
