package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersGetRequest {

    private RetroCallBack callBack;
    private List<User> users;
    private Call<List<User>> callUsers;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Call<List<User>> getCallUsers() {
        return callUsers;
    }

    public void setCallUsers(Call<List<User>> callUsers) {
        this.callUsers = callUsers;
    }

    public RetroCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(RetroCallBack callBack) {
        this.callBack = callBack;
    }
}
