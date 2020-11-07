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
    //initialize Variable
    EditText etUsername,etEmail,etPassword,etConfirmPassword;
    Button bt_submit;
    AwesomeValidation awesomeValidation;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assign Variable
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        bt_submit = findViewById(R.id.bt_submit);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validation for Username
        awesomeValidation.addValidation(this,R.id.et_username, ".{3,}",R.string.invalid_username);

        //Add Email
        awesomeValidation.addValidation(this,R.id.et_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //Add Password
        awesomeValidation.addValidation(this,R.id.et_password,".{6,}",R.string.invalid_register_password);

        //Add Confirm Password
        awesomeValidation.addValidation(this,R.id.et_confirm_password,R.id.et_password,R.string.invalid_confirm_password);
        bt_submit.setOnClickListener(v -> {
            //Check Validation
            if(awesomeValidation.validate() && validatePassword()){
                //On Success
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

    private boolean validatePassword() {
        String passwordInput = etPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            etPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etPassword.setError("Password must have at least 1 digit, 1 upper case letter and 1 special character");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }
}