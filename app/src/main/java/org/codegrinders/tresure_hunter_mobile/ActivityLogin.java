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
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class ActivityLogin extends AppCompatActivity
{
    Button bt_login;
    TextView tv_register;
    EditText etUsername;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        bt_login = findViewById(R.id.bt_login);
        tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(v -> openActivityRegister());

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validation for Username
        awesomeValidation.addValidation(this,R.id.et_username, RegexTemplate.NOT_EMPTY,R.string.invalid_name_or_email);

        //Add Email
        awesomeValidation.addValidation(this,R.id.et_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //Add Password
        awesomeValidation.addValidation(this,R.id.et_password,".{6,}",R.string.invalid_login_password);

        bt_login.setOnClickListener(v -> {
            //Check Validation
            if(awesomeValidation.validate()){
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
        Intent intent = new Intent(this,ActivityRegister.class);
        startActivity(intent);
    }

}