package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetroInstanceTest {
    //Given or When_Then_Return or Get
    @Mock
    RetroInstance mockedInstance = mock(RetroInstance.class);

    MockWebServer server = new MockWebServer();

    MockResponse response = new MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody("{}");

    @Test
    public void WhenMockWebServerStartThenCheckConnection() {
        String hostname = server.getHostName();
        int port = server.getPort();

        HttpUrl urlPuzzles = server.url("/puzzles/");
        HttpUrl urlUsers = server.url("/users/");
        OkHttpClient client = new OkHttpClient();
        Request requestPuzzles = new Builder().url("http://127.0.0.1:8080/puzzles/").build();
        Request requestUsers = new Builder().url("http://127.0.0.1:8080/users/").build();

        assertEquals("http://127.0.0.1:8080/puzzles/", urlPuzzles.toString());
        assertEquals("http://127.0.0.1:8080/users/", urlUsers.toString());

        assertEquals(8080, port);
        assertEquals(hostname, "127.0.0.1");
        response.setBody("{\"id\":\"1\",\"question\":\"5+5 equals? (10)\",\"answer\":\"10\",\"points\":\"500\"}");

        client.newCall(requestPuzzles);
        client.newCall(requestUsers);

    }

    @Before
    public void setUp() throws Exception {
        server.start(8080);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
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
    public void whenIsCorrectIsCalledWithCorrectAnswerCheckIfAnswerIsCorrect(){
        RetroInstance retroInstance = new RetroInstance();
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        assertTrue(retroInstance.isCorrect("answer1"));
    }

    @Test
    public void whenIsCorrectIsCalledWithWrongAnswerCheckIfAnswerIsNotCorrect(){
        RetroInstance retroInstance = new RetroInstance();
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        assertFalse(retroInstance.isCorrect("ans"));
    }

    @Test
    public void whenIsCorrectIsCalledASecondTimeItChecksTheNextAnswerOnThePuzzleList(){
        RetroInstance retroInstance = new RetroInstance();
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        retroInstance.getPuzzles().get(1).setAnswer("answer2");
        assertTrue(retroInstance.isCorrect("answer1"));
        assertTrue(retroInstance.isCorrect("answer2"));
    }

    @Test
    public void whenIsCorrectIsCalledASecondTimeAndTheresOnlyOnePuzzleItReturnsFalse(){
        RetroInstance retroInstance = new RetroInstance();
        retroInstance.setPuzzles(new ArrayList<>());
        retroInstance.getPuzzles().add(new Puzzle());
        retroInstance.getPuzzles().get(0).setAnswer("answer1");
        assertTrue(retroInstance.isCorrect("answer1"));
        assertFalse(retroInstance.isCorrect(""));
    }



    @Test
    public void usersGetRequest() {
    }

    @Test
    public void puzzlesGetRequest() {
    }

}