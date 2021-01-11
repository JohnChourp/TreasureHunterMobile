package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.model.ChangeEmailRequest;
import org.codegrinders.treasure_hunter_mobile.model.ChangeEmailResponse;
import org.codegrinders.treasure_hunter_mobile.model.Markers;
import org.codegrinders.treasure_hunter_mobile.model.Puzzle;
import org.codegrinders.treasure_hunter_mobile.model.RegisterRequest;
import org.codegrinders.treasure_hunter_mobile.model.RegisterResponse;
import org.codegrinders.treasure_hunter_mobile.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    @GET("/user/leaderboard")
    Call<List<User>> getUsers();

    @GET("/puzzle/")
    Call<List<Puzzle>> getPuzzles();

    @GET("/marker/")
    Call<List<Markers>> getMarkers();

    @POST("/user/")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/user/change/email/")
    Call<ChangeEmailResponse> changeEmail(@Body ChangeEmailRequest changeEmailRequest);

    @GET("/user/login/")
    Call<User> loginRequest(@Query("username") String username, @Query("password") String password);

    @GET("/user/update/points")
    Call<User> updateUserPoints(@Query("id") String userId);

    @GET("/puzzle/answer")
    Call<Puzzle> answerIsCorrect(@Query("id") String puzzleId, @Query("answer") String answer, @Query("userId") String userId);
}
