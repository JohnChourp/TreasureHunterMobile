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

//    @Test
//    public void puzzlesGetRequest() {
//        PuzzlesRequest puzzlesRequest = new PuzzlesRequest();
//        puzzlesRequest.setId("1");
//        puzzlesRequest.setQuestion("1+1=?");
//        puzzlesRequest.setAnswer("2");
//        puzzlesRequest.setPoints(100);
//        puzzlesRequest.setPuzzleId("2");
//        Call<PuzzlesResponse> call = RetroInstance.initializeAPIService().postPuzzles(puzzlesRequest);
//
//        call.enqueue(new Callback<PuzzlesResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<PuzzlesResponse> call, @NotNull Response<PuzzlesResponse> response) {
//                Gson gson = new Gson();
//                assert response.errorBody() != null;
//                PuzzlesResponse registerResponse = gson.fromJson(String.valueOf(response.body()), PuzzlesResponse.class);
//                puzzles = registerResponse.getCall();
//                assertEquals(1, puzzles.size());
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<PuzzlesResponse> call, @NotNull Throwable t) {
//
//            }
//        });
//    }
    @Test
    public void puzzlesGetRequestWithMockedCalls() {
        APIService mockedAPIService = mock(APIService.class);
        Call<List<Puzzle>> mockedCall = mock(Call.class);
        when(mockedAPIService.getPuzzles()).thenReturn(mockedCall);

        doAnswer(invocation -> {
            Callback<List<Puzzle>> callback = invocation.getArgument(0, Callback.class);
            callback.onResponse(mockedCall, Response.success(new List<Puzzle>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(@Nullable Object o) {
                    return false;
                }

                @NonNull
                @Override
                public Iterator<Puzzle> iterator() {
                    return null;
                }

                @NonNull
                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @NonNull
                @Override
                public <T> T[] toArray(@NonNull T[] a) {
                    return null;
                }

                @Override
                public boolean add(Puzzle puzzle) {
                    return false;
                }

                @Override
                public boolean remove(@Nullable Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(@NonNull Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(@NonNull Collection<? extends Puzzle> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, @NonNull Collection<? extends Puzzle> c) {
                    return false;
                }

                @Override
                public boolean removeAll(@NonNull Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(@NonNull Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public Puzzle get(int index) {
                    return null;
                }

                @Override
                public Puzzle set(int index, Puzzle element) {
                    return null;
                }

                @Override
                public void add(int index, Puzzle element) {

                }

                @Override
                public Puzzle remove(int index) {
                    return null;
                }

                @Override
                public int indexOf(@Nullable Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(@Nullable Object o) {
                    return 0;
                }

                @NonNull
                @Override
                public ListIterator<Puzzle> listIterator() {
                    return null;
                }

                @NonNull
                @Override
                public ListIterator<Puzzle> listIterator(int index) {
                    return null;
                }

                @NonNull
                @Override
                public List<Puzzle> subList(int fromIndex, int toIndex) {
                    return null;
                }
            }));
            return null;
        }).when(mockedCall).enqueue(any(Callback.class));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}