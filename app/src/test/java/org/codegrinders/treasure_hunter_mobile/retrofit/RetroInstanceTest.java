package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.junit.Test;

import static org.junit.Assert.*;

public class RetroInstanceTest {
boolean wasInCallPuzzles = false;
boolean wasInCallUsers = false;
    @Test
    public void initializeAPIService() {
    }

    @Test
    public void getQuestion() {
    }

    @Test
    public void isCorrect() {
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