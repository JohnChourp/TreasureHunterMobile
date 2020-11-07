package org.codegrinders.tresure_hunter_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.regex.Pattern;

public class ActivityLogin extends AppCompatActivity
{
    Button bt_login;
    TextView tv_register;
    EditText etUsername,etPassword;

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
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(v -> openActivityRegister());

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validation for Username
        awesomeValidation.addValidation(this,R.id.et_username, ".{3,}",R.string.invalid_name_or_email);

        //Add Email
        awesomeValidation.addValidation(this,R.id.et_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //Add Password
        awesomeValidation.addValidation(this,R.id.et_password,".{6,}",R.string.invalid_login_password);

        bt_login.setOnClickListener(v -> {
            //Check Validation
            if(awesomeValidation.validate() && validatePassword()){
                //On Success
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

    private boolean validatePassword() {
        String passwordInput = etPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            etPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etPassword.setError("Please Enter Valid Password");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

}