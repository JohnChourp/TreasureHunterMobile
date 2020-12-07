package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetroInstanceTest {

    @Mock
    RetroInstance mockedInstance = mock(RetroInstance.class);

    RetroInstance retroInstance = new RetroInstance();

    MockWebServer server = new MockWebServer();
    OkHttpClient client = new OkHttpClient();

    Call callPuzzles;
    Call callUsers;
    List<String> puzzlesList;
    List<String> usersList;

    @Before
    public void setUp() throws Exception {
        server.start(8080);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    MockResponse responsePuzzles = new MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody("[" + "{" + "\"id\":\"1\"," + "\"question\":\"5+5 equals? (10)\"," + "\"answer\":\"10\"," + "\"points\":500" + "}" + "]");

    MockResponse responseUsers = new MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody("[" + "{" + "\"id\":\"1\"," + "\"email\":\"pakis@pakis.gr\"," + "\"username\":\"pakis\"," + "\"password\":\"111\"," + "\"points\": 0" + "}" + "]");

    @Test
    public void whenServerStartThenCheckConnectionForPuzzles() {
        HttpUrl urlPuzzles = server.url("/puzzles/");
        Request requestPuzzles = new Builder().url(urlPuzzles).build();
        callPuzzles = client.newCall(requestPuzzles);

        callPuzzles.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                puzzlesList = Collections.singletonList(Objects.requireNonNull(responsePuzzles.getBody()).readUtf8());
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                assertNotEquals("", e.getMessage());
            }
        });
    }

    @Test
    public void whenServerStartThenCheckConnectionForUsers() {
        HttpUrl urlUsers = server.url("/users/");
        Request requestUsers = new Builder().url(urlUsers).build();
        callUsers = client.newCall(requestUsers);

        callUsers.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                usersList = Collections.singletonList(Objects.requireNonNull(responseUsers.getBody()).readUtf8());
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                assertNotEquals("", e.getMessage());
            }
        });
    }

    @Test
    public void whenServerStartThenCheckPortIsCorrect() {
        assertEquals(8080, server.getPort());
    }

    @Test
    public void whenServerStartThenCheckHostNameIsCorrect() {
        assertEquals("127.0.0.1", server.getHostName());
    }

    @Test
    public void whenServerStartThenCheckUrlPuzzlesIsCorrect() {
        assertEquals("http://127.0.0.1:8080/puzzles/", server.url("/puzzles/").toString());
    }

    @Test
    public void whenServerStartThenCheckUrlUsersIsCorrect() {
        assertEquals("http://127.0.0.1:8080/users/", server.url("/users/").toString());
    }

    @Test
    public void whenQuestionNumberWithInLimitThenGetDataSuccessfully() {
        when(mockedInstance.getQuestion()).thenReturn("question1");
        String result = mockedInstance.getQuestion();
        assertEquals("question1", result);
    }

    @Test
    public void whenAnswerIsCorrectThenReturnTrue() {
        when(mockedInstance.isCorrect("10")).thenReturn(true);
        boolean correct = mockedInstance.isCorrect("10");
        assertTrue(correct);
    }

    @Test
    public void whenIsCorrectIsCalledWithCorrectAnswerCheckIfAnswerIsCorrect() {
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        assertTrue(retroInstance.isCorrect("answer1"));
    }

    @Test
    public void whenIsCorrectIsCalledWithWrongAnswerCheckIfAnswerIsNotCorrect() {
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        assertFalse(retroInstance.isCorrect("ans"));
    }

    @Test
    public void whenIsCorrectIsCalledASecondTimeItChecksTheNextAnswerOnThePuzzleList() {
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        retroInstance.getPuzzles().get(1).setAnswer("answer2");
        assertTrue(retroInstance.isCorrect("answer1"));
        assertTrue(retroInstance.isCorrect("answer2"));
    }

    @Test
    public void whenIsCorrectIsCalledASecondTimeIfTheresOnlyOnePuzzleItReturnsFalse() {
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        assertTrue(retroInstance.isCorrect("answer1"));
        assertFalse(retroInstance.isCorrect(""));
    }


    @Test
    public void whenGetQuestionIsCalledAfterIsCorrectItReturnsTheQuestionOfTheNextPuzzle() {
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setQuestion("question1");
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        retroInstance.getPuzzles().get(1).setQuestion("question2");

        assertEquals("question1", retroInstance.getQuestion());
        assertTrue(retroInstance.isCorrect("answer1"));
        assertEquals("question2", retroInstance.getQuestion());
    }

    @Test
    public void whenGetQuestionIsCalledAfterIsCorrectIfTheresOnlyOnePuzzleItReturnsEmptyString() {
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setQuestion("question1");
        retroInstance.getPuzzles().get(0).setAnswer("answer1");

        assertEquals("question1", retroInstance.getQuestion());
        assertTrue(retroInstance.isCorrect("answer1"));
        assertEquals("", retroInstance.getQuestion());
    }
}