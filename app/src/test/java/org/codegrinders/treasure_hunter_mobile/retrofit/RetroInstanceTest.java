package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.RegisterRequest;
import org.codegrinders.treasure_hunter_mobile.tables.RegisterResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;

import static org.junit.Assert.assertEquals;

public class RetroInstanceTest {

    MockWebServer server = new MockWebServer();

    @Before
    public void setUp() throws Exception {
        server.start(8080);
    }

    @Test
    public void whenServerStartThenCheckUlrUsers() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("jonhiedarn@gmail.com");
        registerRequest.setUsername("john");
        registerRequest.setPassword("12345678A!");
        Call<RegisterResponse> registerResponseCall = RetroInstance.initializeAPIService().registerUser(registerRequest);
        assertEquals("http://10.0.2.2:8080/user/", registerResponseCall.request().url().toString());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}