package org.codegrinders.treasure_hunter_mobile;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.codegrinders.treasure_hunter_mobile.tables.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("user/all")
    Call<List<User>> getUsers();

    @GET("puzzle/all")
    Call<List<Puzzle>> getPuzzles();
}
