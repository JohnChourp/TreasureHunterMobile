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
    PuzzlesCall puzzlesCall = new PuzzlesCall();

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

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}