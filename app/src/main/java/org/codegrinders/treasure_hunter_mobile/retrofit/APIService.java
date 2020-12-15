package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Markers;
import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.codegrinders.treasure_hunter_mobile.tables.RegisterRequest;
import org.codegrinders.treasure_hunter_mobile.tables.RegisterResponse;
import org.codegrinders.treasure_hunter_mobile.tables.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("/user/")
    Call<List<User>> getUsers();

    @GET("/puzzle/")
    Call<List<Puzzle>> getPuzzles();

    @GET("/marker/")
    Call<List<Markers>> getMarkers();

    @POST("/user/")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/puzzle/")
    Call<RegisterResponse> registerPuzzle(@Body RegisterRequest registerRequest);

    @POST("/marker/")
    Call<RegisterResponse> registerMarker(@Body RegisterRequest registerRequest);
}
