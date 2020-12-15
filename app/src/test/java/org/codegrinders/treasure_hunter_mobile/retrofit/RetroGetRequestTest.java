package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class RetroGetRequestTest {

    MockWebServer server = new MockWebServer();
    RetroGetRequest retroGetRequest = new RetroGetRequest();

    @Before
    public void setUp() throws Exception {
        server.start(8080);
    }

    @Test
    public void whenGetQuestionIsCalledCheckIfQuestionIsCorrect() {
        retroGetRequest.setPuzzles(new ArrayList<>());
        retroGetRequest.getPuzzles().add(new Puzzle());
        retroGetRequest.getPuzzles().get(0).setQuestion("question1");
        assertEquals("question1", retroGetRequest.getQuestion());
    }

    @Test
    public void whenGetQuestionIsCalledCheckIfQuestionIsWrong() {
        retroGetRequest.setPuzzles(new ArrayList<>());
        retroGetRequest.getPuzzles().add(new Puzzle());
        retroGetRequest.getPuzzles().get(0).setQuestion("question1");
        assertNotEquals("ques", retroGetRequest.getQuestion());
    }

    @Test
    public void whenIsCorrectIsCalledWithCorrectAnswerCheckIfAnswerIsCorrect() {
        retroGetRequest.setPuzzles(new ArrayList<>());
        retroGetRequest.getPuzzles().add(new Puzzle());
        retroGetRequest.getPuzzles().get(0).setAnswer("answer1");
        assertTrue(retroGetRequest.isCorrect("answer1"));
    }

    @Test
    public void whenIsCorrectIsCalledWithWrongAnswerCheckIfAnswerIsNotCorrect() {
        retroGetRequest.setPuzzles(new ArrayList<>());
        retroGetRequest.getPuzzles().add(new Puzzle());
        retroGetRequest.getPuzzles().get(0).setAnswer("answer1");
        assertFalse(retroGetRequest.isCorrect("ans"));
    }
    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}