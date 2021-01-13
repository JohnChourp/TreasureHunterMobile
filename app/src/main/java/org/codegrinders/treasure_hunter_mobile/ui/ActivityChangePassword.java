package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.UsersCall;

import java.util.regex.Pattern;

public class ActivityChangePassword extends AppCompatActivity {

    EditText et_email, et_currentPassword, et_newPassword, et_confirmPassword;
    Button bt_changePass;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,99}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        et_email = findViewById(R.id.et_emailForPass);
        et_currentPassword = findViewById(R.id.et_currentPassword);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_confirmPassword = findViewById(R.id.et_confirmEditPassword);
        bt_changePass = findViewById(R.id.bt_submit3);


    }


    public boolean validate() {
        String email = et_email.getText().toString();
        String currentPassword = et_currentPassword.getText().toString();
        String newPassword = et_newPassword.getText().toString();
        String confirmedPassword = et_confirmPassword.getText().toString();

        if(!email.equals(UsersCall.user.getEmail())) {
            et_email.setError("The email given doesn't match with the account's email");
            return false;
        } else if(!currentPassword.equals(UsersCall.user.getPassword())) {
            et_currentPassword.setError("The password given is incorrect");
            return false;
        } else if(!PASSWORD_PATTERN.matcher(newPassword).matches() || newPassword.contains(" ")) {
            et_newPassword.setError("Password must be 8–99 characters long and must have at least 1 digit, 1 upper case letter and 1 special character without spaces");
            return false;
        } else if(!confirmedPassword.equals(newPassword)) {
            et_confirmPassword.setError("Passwords do not match");
        } else
            return true;
        return false;
    }
}