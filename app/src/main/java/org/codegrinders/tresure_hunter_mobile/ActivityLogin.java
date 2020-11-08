package org.codegrinders.tresure_hunter_mobile;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class ActivityLogin extends AppCompatActivity
{
    Button bt_login;
    TextView tv_register;
    EditText etUsername,etPassword;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=\\S+$).{3,99}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,99}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        tv_register = findViewById(R.id.tv_register);

        tv_register.setOnClickListener(v -> openActivityRegister());
        bt_login.setOnClickListener(v -> {
            if(validate()){
                Toast.makeText(getApplicationContext(),"Login Successfully...",Toast.LENGTH_SHORT).show();
                openActivityMain();
            }else{
                Toast.makeText(getApplicationContext(),"Login Failed...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openActivityMain()
    {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }

    private void openActivityRegister()
    {
        Intent intent = new Intent(this, ActivityRegister.class);
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