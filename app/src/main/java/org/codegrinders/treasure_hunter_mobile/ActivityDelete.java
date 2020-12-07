package org.codegrinders.treasure_hunter_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class ActivityDelete extends AppCompatActivity {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=\\S+$).{3,99}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,99}$");

    EditText etUsername, etPassword;
    Button btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btDelete = findViewById(R.id.btDelete);

        btDelete.setOnClickListener(v -> {
            if (validate()) {
                Toast.makeText(getApplicationContext(), "Delete Successfully...", Toast.LENGTH_SHORT).show();
                openActivityLogin();
            } else {
                Toast.makeText(getApplicationContext(), "Delete Failed...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openActivityLogin()
    {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }


    private boolean validate() {
        String usernameInput = etUsername.getText().toString();
        String passwordInput = etPassword.getText().toString();

        if (USERNAME_PATTERN.matcher(usernameInput).matches() || usernameInput.contains(" ")) {
            etUsername.setError("Wrong Username or Email");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches() || passwordInput.contains(" ")) {
            etPassword.setError("Wrong Password");
            return false;
        }  else {
            etPassword.setError(null);
            return true;
        }
    }
}

