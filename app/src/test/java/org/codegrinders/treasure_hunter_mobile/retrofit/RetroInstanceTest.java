package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RetroInstanceTest {
boolean wasInCallPuzzles = false;
boolean wasInCallUsers = false;

    @Mock
    RetroInstance mockedInstance = new RetroInstance();

    @Test
    public void initializeAPIService() {
    }

    @Test
    public void getQuestion() {
        mockedInstance.setPuzzles(new ArrayList<>());

        mockedInstance.getPuzzles().add(new Puzzle());
        mockedInstance.getPuzzles().get(0).setQuestion("question1");

        mockedInstance.getPuzzles().add(new Puzzle());
        mockedInstance.getPuzzles().get(1).setQuestion("question2");

        assertEquals("question1", mockedInstance.getQuestion());
        mockedInstance.setQuestionNumber(1);
        assertEquals("question2", mockedInstance.getQuestion());
        //and so on and so forth
    }

    @Test
    public void isCorrect() {
        mockedInstance.setPuzzles(new ArrayList<>());

        mockedInstance.getPuzzles().add(new Puzzle());
        mockedInstance.getPuzzles().get(0).setAnswer("answer1");

        mockedInstance.getPuzzles().add(new Puzzle());
        mockedInstance.getPuzzles().get(1).setAnswer("answer2");

        assertFalse(mockedInstance.isCorrect("answ"));
        assertTrue(mockedInstance.isCorrect("answer1"));
        //will get the next element automatically
        assertFalse(mockedInstance.isCorrect("answ"));
        assertTrue(mockedInstance.isCorrect("answer2"));
    }

    @Test
    public void usersGetRequest() {
    }

    @Test
    public void puzzlesGetRequest() {
    }

    @Test
    public void setCallListener() {
        RetroInstance retroInstance = new RetroInstance();

        retroInstance.setCallListener(new RetroCallBack() {
            @Override
            public void onCallUsersFinished() {
                wasInCallUsers = true;
            }

            @Override
            public void onCallPuzzlesFinished() {
                wasInCallPuzzles = true;
            }

            @Override
            public void onCallFailed(String errorMessage) {
                assertEquals("error example", errorMessage);
            }
        });
        retroInstance.callBack.onCallPuzzlesFinished();
        assertTrue(wasInCallPuzzles);
        retroInstance.callBack.onCallUsersFinished();
        assertTrue(wasInCallUsers);
        retroInstance.callBack.onCallFailed("error example");
    }
}