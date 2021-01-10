package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.model.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersCall {

    private RetroCallBack callBack;
    private List<User> users;
    private Call<List<User>> call;
    public static User user;

    public void usersGetRequest() {
        call = RetroInstance.initializeAPIService().getUsers();
        call.enqueue(new Callback<List<User>>() {
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

    public void oneUserGetRequest(String id) {
        Call<User> oneUserCall = RetroInstance.initializeAPIService().updateUserPoints(id);
        oneUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (!response.isSuccessful()) {
                    callBack.onCallFailed("code: " + response.code());
                    return;
                }
                user = response.body();
                callBack.onCallFinished("OneUser");
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                callBack.onCallFailed(t.getMessage());
            }
        });
    }

    public void userLoginRequest(String username, String password) {
        Call<User> call = RetroInstance.initializeAPIService().loginRequest(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    user = new User(response.body().getId(), response.body().getUsername(), response.body().getPoints());
                } else {
                    user = null;
                }
                callBack.onCallFinished("login");
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                user = null;
            }
        });
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        UsersCall.user = user;
    }

    public Call<List<User>> getCall() {
        return call;
    }

    public void setCall(Call<List<User>> call) {
        this.call = call;
    }

    public RetroCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(RetroCallBack callBack) {
        this.callBack = callBack;
    }

}
