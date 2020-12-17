package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRequest {
    private User user;

    public void UserLoginRequest(String username, String password){
        Call<User> call = RetroInstance.initializeAPIService().loginRequest(username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = new User(response.body().getId(),response.body().getUsername(),response.body().getPoints());
                }else user = null;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                user = null;
            }
        });

    }

    public User getUser(){
        return user;
    }






}
