package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Puzzle;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

public class RetroInstanceTest {
    //Given_When_Then
    @Mock
    RetroInstance mockedInstance = mock(RetroInstance.class);

    @Mock
    MockWebServer mockWebServer = new MockWebServer();


    @Test
    public void whenQuestionNumberWithInLimitThenGetDataSuccessfully(){
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
    public void usersGetRequest() {
    }

    @Test
    public void puzzlesGetRequest() {
    }

}