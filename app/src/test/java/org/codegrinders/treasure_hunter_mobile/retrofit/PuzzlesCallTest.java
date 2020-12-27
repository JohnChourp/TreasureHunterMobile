package org.codegrinders.treasure_hunter_mobile.retrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.codegrinders.treasure_hunter_mobile.model.Puzzle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PuzzlesCallTest {

    MockWebServer server = new MockWebServer();
    PuzzlesCall puzzlesCall = new PuzzlesCall();

    @Before
    public void setUp() throws Exception {
        server.start(8080);
    }

    @Test
    public void whenSearchPuzzleByIDIsCalledWithExistingIDItReturnsCorrespondingPuzzleIndex() {
//        puzzlesCall.setPuzzles(new ArrayList<>());
//        puzzlesCall.getPuzzles().add(new Puzzle());
//        puzzlesCall.getPuzzles().add(new Puzzle());
//
//        puzzlesCall.getPuzzles().get(0).setId("first id");
//        puzzlesCall.getPuzzles().get(0).setQuestion("Question1");
//
//        puzzlesCall.getPuzzles().get(1).setId("second id");
//        puzzlesCall.getPuzzles().get(1).setQuestion("Question2");
//
//        assertEquals("Question2", puzzlesCall.getPuzzles().get(puzzlesCall.searchPuzzleByID("second id")).getQuestion());
//        assertEquals("Question1", puzzlesCall.getPuzzles().get(puzzlesCall.searchPuzzleByID("first id")).getQuestion());
    }

    @Test
    public void whenSearchPuzzleByIDIsCalledWithNonExistingIDItReturnsMinusOne() {
//        puzzlesCall.setPuzzles(new ArrayList<>());
//        puzzlesCall.getPuzzles().add(new Puzzle());
//        puzzlesCall.getPuzzles().add(new Puzzle());
//
//        puzzlesCall.getPuzzles().get(0).setId("first id");
//        puzzlesCall.getPuzzles().get(1).setId("second id");
//
//        assertEquals(-1, puzzlesCall.searchPuzzleByID("some id"));
    }

    @Test
    public void searchPuzzleByID() {
//        puzzlesCall.setPuzzles(new ArrayList<>());
//        puzzlesCall.getPuzzles().add(new Puzzle());
//        puzzlesCall.getPuzzles().add(new Puzzle());
//        puzzlesCall.getPuzzles().get(0).setId("first id");
//        puzzlesCall.getPuzzles().get(0).setQuestion("question1");
//
//        puzzlesCall.getPuzzles().get(1).setId("second id");
//        puzzlesCall.getPuzzles().get(1).setQuestion("question2");
//
//        assertEquals("question2", puzzlesCall.getPuzzles().get(puzzlesCall.searchPuzzleByID("second id")).getQuestion());
//        assertEquals("question1", puzzlesCall.getPuzzles().get(puzzlesCall.searchPuzzleByID("first id")).getQuestion());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}