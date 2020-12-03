package org.codegrinders.treasure_hunter_mobile.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.APIService;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroInstance;
import org.codegrinders.treasure_hunter_mobile.settings.MediaService;
import org.codegrinders.treasure_hunter_mobile.settings.Sound;
import org.codegrinders.treasure_hunter_mobile.tables.RegisterRequest;
import org.codegrinders.treasure_hunter_mobile.tables.RegisterResponse;
import org.codegrinders.treasure_hunter_mobile.tables.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegister extends AppCompatActivity
{
    EditText etUsername,etEmail,etPassword,etConfirmPassword;
    Button bt_submit;
    AwesomeValidation emailValidation;
    MediaService audioService;
    RetroInstance retroInstance;



    Intent intent;
    boolean isBound =false;
    int backgroundMusic;
    int buttonSound;

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
            audioService.play(buttonSound,0);
            if(emailValidation.validate() && validate()){
                Toast.makeText(getApplicationContext(),"Form Validate Successfully...",Toast.LENGTH_SHORT).show();
//                String username=etUsername.getText().toString(); //TODO place these in validate method
//                String email=etEmail.getText().toString();
//                String password=etPassword.getText().toString();
//               // String cPass=etConfirmPassword.getText().toString();
//                createUser(username,email,password);
                RegisterRequest registerRequest=new RegisterRequest();
                registerRequest.setEmail(etEmail.getText().toString());
                registerRequest.setUsername(etUsername.getText().toString());
                registerRequest.setPassword(etPassword.getText().toString());
                registerRequest.setPoints(0);
                registerRequest.setId("12as");
                registerUser(registerRequest);
                openActivityLogin();
            }else{
                Toast.makeText(getApplicationContext(),"Validation Failed...",Toast.LENGTH_SHORT).show();
            }
        });
    }

 /*   public void createUser(String username,String email,String password){
        RegisterRequest registerRequest=new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);


       // retroInstance.createPostRequest(registerRequest);


    } */

    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse>registerResponseCall=retroInstance.initializeAPIService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    String message="Successfully Registered";

                }else{
                    String message="Erron on response DES TO sto ActivityRegister";
                    Toast.makeText(ActivityRegister.this, message, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                String message=t.getLocalizedMessage();
                Toast.makeText(ActivityRegister.this, message, Toast.LENGTH_SHORT).show();
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
     //   String email=etEmail.getText().toString();

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
           // createUser(usernameInput,email,passwordInput,confirmPasswordInput);
            return true;
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;
            backgroundMusic = Sound.searchByResId(R.raw.wanabe_epic_music);
            buttonSound = Sound.searchByResId(R.raw.pop);
            audioService.play(backgroundMusic, Sound.get(backgroundMusic).position);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        intent = new Intent(this, MediaService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }
}
