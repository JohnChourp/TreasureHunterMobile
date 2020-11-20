package org.codegrinders.treasure_hunter_mobile;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("user/all")
    Call<List<Users>> getUsers();

    @GET("puzzle/all")
    Call<List<Puzzles>> getPuzzles();
}
