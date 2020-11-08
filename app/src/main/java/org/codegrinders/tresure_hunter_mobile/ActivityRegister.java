package org.codegrinders.tresure_hunter_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.regex.Pattern;

public class ActivityRegister extends AppCompatActivity
{
    EditText etUsername,etEmail,etPassword,etConfirmPassword;
    Button bt_submit;
    AwesomeValidation emailValidation;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=\\S+$).{3,99}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,99}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        bt_submit = findViewById(R.id.bt_submit);

        emailValidation = new AwesomeValidation(ValidationStyle.BASIC);
        emailValidation.addValidation(this,R.id.et_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        bt_submit.setOnClickListener(v -> {
            if(emailValidation.validate() && validate()){
                Toast.makeText(getApplicationContext(),"Form Validate Successfully...",Toast.LENGTH_SHORT).show();
                openActivityLogin();
            }else{
                Toast.makeText(getApplicationContext(),"Validation Failed...",Toast.LENGTH_SHORT).show();
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
        String confirmPasswordInput = etConfirmPassword.getText().toString();

        if (USERNAME_PATTERN.matcher(usernameInput).matches() || usernameInput.contains(" ")) {
            etUsername.setError("Username must be 3–99 characters long without spaces");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches() || passwordInput.contains(" ")) {
            etPassword.setError("Password must be 8–99 characters long and must have at least 1 digit, 1 upper case letter and 1 special character without spaces");
            return false;
        } else if (!confirmPasswordInput.equals(passwordInput)) {
            etConfirmPassword.setError("Passwords do not match");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }
}
