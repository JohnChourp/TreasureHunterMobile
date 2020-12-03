package org.codegrinders.treasure_hunter_mobile.retrofit;


import android.widget.Toast;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.codegrinders.treasure_hunter_mobile.tables.RegisterRequest;
import org.codegrinders.treasure_hunter_mobile.tables.RegisterResponse;
import org.codegrinders.treasure_hunter_mobile.tables.User;
import org.codegrinders.treasure_hunter_mobile.ui.ActivityRegister;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    private int questionNumber = 0;
    private List<Puzzle> puzzles;
    private List<User> users;

    private RetroCallBack callBack;
    APIService apiService;
    Call<List<User>> callUsers;
    Call<List<Puzzle>> callPuzzles;
    Call<RegisterResponse> userCall;
    private  ActivityRegister activityRegister;

    public APIService initializeAPIService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        return retrofit.create(APIService.class);
    }

    public String getQuestion(){
        String ret = "";

        if (questionNumber < puzzles.size()) {
            ret =  puzzles.get(questionNumber).getQuestion();
        }
        return ret;
    }

    public boolean isCorrect(String input){
        boolean correct = false;

        if (questionNumber < puzzles.size()){
            if(puzzles.get(questionNumber).getAnswer().equals(input)){
                correct = true;
                questionNumber++;
            }
        }
        return correct;
    }

   public void createPostRequest(RegisterRequest registerRequest){
        userCall=apiService.registerUser(registerRequest);

        userCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    String message="Successfully Registered";

                }else{
                    String message="Erron on response DES TO sto ActivityRegister";
                    Toast.makeText(activityRegister, message, Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                String message=t.getLocalizedMessage();
              //  Toast.makeText(ActivityRegister.this, message, Toast.LENGTH_SHORT).show();
                Toast.makeText(activityRegister, message, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void usersGetRequest(){
        callUsers = apiService.getUsers();

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

    public void puzzlesGetRequest(){
        apiService = initializeAPIService();
        callPuzzles = apiService.getPuzzles();

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

    public void setCallListener(RetroCallBack callBack){
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
}
