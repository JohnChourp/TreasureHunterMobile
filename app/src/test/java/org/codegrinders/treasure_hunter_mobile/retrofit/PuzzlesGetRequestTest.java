package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PuzzlesGetRequestTest {

    MockWebServer server = new MockWebServer();
    PuzzlesGetRequest puzzlesGetRequest = new PuzzlesGetRequest();

    @Before
    public void setUp() throws Exception {
        server.start(8080);
    }

    @Test
    public void whenGetQuestionIsCalledCheckIfQuestionIsCorrect() {
        puzzlesGetRequest.setPuzzles(new ArrayList<>());
        puzzlesGetRequest.getPuzzles().add(new Puzzle());
        puzzlesGetRequest.getPuzzles().get(0).setQuestion("question1");
        assertEquals("question1", puzzlesGetRequest.getQuestion());
    }

    @Test
    public void whenGetQuestionIsCalledCheckIfQuestionIsWrong() {
        puzzlesGetRequest.setPuzzles(new ArrayList<>());
        puzzlesGetRequest.getPuzzles().add(new Puzzle());
        puzzlesGetRequest.getPuzzles().get(0).setQuestion("question1");
        assertNotEquals("ques", puzzlesGetRequest.getQuestion());
    }

    @Test
    public void whenIsCorrectIsCalledWithCorrectAnswerCheckIfAnswerIsCorrect() {
        puzzlesGetRequest.setPuzzles(new ArrayList<>());
        puzzlesGetRequest.getPuzzles().add(new Puzzle());
        puzzlesGetRequest.getPuzzles().get(0).setAnswer("answer1");
        assertTrue(puzzlesGetRequest.isCorrect("answer1"));
    }

    @Test
    public void whenIsCorrectIsCalledWithWrongAnswerCheckIfAnswerIsNotCorrect() {
        puzzlesGetRequest.setPuzzles(new ArrayList<>());
        puzzlesGetRequest.getPuzzles().add(new Puzzle());
        puzzlesGetRequest.getPuzzles().get(0).setAnswer("answer1");
        assertFalse(puzzlesGetRequest.isCorrect("ans"));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}