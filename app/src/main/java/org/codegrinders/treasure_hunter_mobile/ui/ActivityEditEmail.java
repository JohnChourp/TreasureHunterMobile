package org.codegrinders.treasure_hunter_mobile.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.model.ChangeEmailRequest;
import org.codegrinders.treasure_hunter_mobile.model.ChangeEmailResponse;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroInstance;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditEmail extends AppCompatActivity {

    EditText et_currentEmail, et_newEmail, et_password;
    Button bt_changeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editemail);

        et_currentEmail = findViewById(R.id.et_currentEmail);
        et_newEmail = findViewById(R.id.et_newEmail);
        et_password = findViewById(R.id.et_password2);
        bt_changeEmail = findViewById(R.id.bt_submit2);

        bt_changeEmail.setOnClickListener(v -> {
            ChangeEmailRequest changeEmailRequest = new ChangeEmailRequest();
            changeEmailRequest.setEmail(et_newEmail.getText().toString());
            changeEmail(changeEmailRequest);
            finish();
        });

    }

    public void changeEmail(ChangeEmailRequest changeEmailRequest) {
        Call<ChangeEmailResponse> emailResponseCall = RetroInstance.initializeAPIService().
                changeEmail(changeEmailRequest);
        emailResponseCall.enqueue(new Callback<ChangeEmailResponse>() {
            @Override
            public void onResponse(@NotNull Call<ChangeEmailResponse> call, @NotNull Response<ChangeEmailResponse> response) {
                if(response.isSuccessful()) {
                    String message = "Email has been successfully changed";
                    Toast.makeText(ActivityEditEmail.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Gson gson = new Gson();
                        assert response.errorBody() != null;
                        ChangeEmailResponse emailResponse = gson.fromJson(response.errorBody().string(), ChangeEmailResponse.class);
                        String message = emailResponse.getMessage();
                        Toast.makeText(ActivityEditEmail.this, message, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChangeEmailResponse> call, @NotNull Throwable t) {

            }
        });
    }
}
