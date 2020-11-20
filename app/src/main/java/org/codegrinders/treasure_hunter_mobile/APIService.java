package org.codegrinders.treasure_hunter_mobile;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("users")
    Call<List<Users>> getUsers();

    @GET("puzzles")
    Call<List<Puzzles>> getPuzzles();
}
