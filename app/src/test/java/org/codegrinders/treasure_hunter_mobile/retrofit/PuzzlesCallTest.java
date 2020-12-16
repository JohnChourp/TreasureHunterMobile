package org.codegrinders.treasure_hunter_mobile.retrofit;

import com.google.gson.Gson;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.codegrinders.treasure_hunter_mobile.tables.PuzzlesResponse;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PuzzlesCallTest {

    MockWebServer server = new MockWebServer();
    PuzzlesCall puzzlesCall = new PuzzlesCall();
    List<Puzzle> puzzles;

    @Before
    public void setUp() throws Exception {
        server.start(8080);
    }

    @Test
    public void whenGetQuestionIsCalledCheckIfQuestionIsCorrect() {
        puzzlesCall.setPuzzles(new ArrayList<>());
        puzzlesCall.getPuzzles().add(new Puzzle());
        puzzlesCall.getPuzzles().get(0).setQuestion("question1");
        assertEquals("question1", puzzlesCall.getQuestion());
    }

    @Test
    public void whenGetQuestionIsCalledCheckIfQuestionIsWrong() {
        puzzlesCall.setPuzzles(new ArrayList<>());
        puzzlesCall.getPuzzles().add(new Puzzle());
        puzzlesCall.getPuzzles().get(0).setQuestion("question1");
        assertNotEquals("ques", puzzlesCall.getQuestion());
    }

    @Test
    public void whenIsCorrectIsCalledWithCorrectAnswerCheckIfAnswerIsCorrect() {
        puzzlesCall.setPuzzles(new ArrayList<>());
        puzzlesCall.getPuzzles().add(new Puzzle());
        puzzlesCall.getPuzzles().get(0).setAnswer("answer1");
        assertTrue(puzzlesCall.isCorrect("answer1"));
    }

    @Test
    public void whenIsCorrectIsCalledWithWrongAnswerCheckIfAnswerIsNotCorrect() {
        puzzlesCall.setPuzzles(new ArrayList<>());
        puzzlesCall.getPuzzles().add(new Puzzle());
        puzzlesCall.getPuzzles().get(0).setAnswer("answer1");
        assertFalse(puzzlesCall.isCorrect("ans"));
    }

    @Test
    public void puzzlesGetRequest() {
        PuzzlesRequest puzzlesRequest = new PuzzlesRequest();
        puzzlesRequest.setId("1");
        puzzlesRequest.setQuestion("1+1=?");
        puzzlesRequest.setAnswer("2");
        puzzlesRequest.setPoints(100);
        puzzlesRequest.setPuzzleId("2");
        Call<PuzzlesResponse> call = RetroInstance.initializeAPIService().postPuzzles(puzzlesRequest);
        call.enqueue(new Callback<PuzzlesResponse>() {
            @Override
            public void onResponse(@NotNull Call<PuzzlesResponse> call, @NotNull Response<PuzzlesResponse> response) {
                Gson gson = new Gson();
                assert response.errorBody() != null;
                PuzzlesResponse registerResponse = gson.fromJson(String.valueOf(response.body()), PuzzlesResponse.class);
                puzzles = registerResponse.getCall();
                assertEquals(1, puzzles.size());
            }

            @Override
            public void onFailure(@NotNull Call<PuzzlesResponse> call, @NotNull Throwable t) {

            }
        });
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}