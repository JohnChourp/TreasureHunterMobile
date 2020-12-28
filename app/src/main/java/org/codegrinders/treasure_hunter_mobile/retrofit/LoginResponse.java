package org.codegrinders.treasure_hunter_mobile.retrofit;

import android.widget.Toast;

import org.codegrinders.treasure_hunter_mobile.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginResponse {
    private User user;
    public RetroCallBack retroCallBack;

    public void userLoginResponse(User LoggedUser){
        Call<User> call = RetroInstance.initializeAPIService().loggedUser(LoggedUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public User getUser(){
        return user;
    }

}
