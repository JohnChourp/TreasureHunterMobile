package org.codegrinders.treasure_hunter_mobile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class ActivityLogin extends AppCompatActivity
{
    Button bt_login;
    TextView tv_register,deleteAccount;
    EditText etUsername,etPassword;
    MediaService audioService;

    Intent intent;
    boolean isBound =false;
    int backgroundMusic;
    int buttonSound;

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
        deleteAccount = findViewById(R.id.deleteAccount);

        tv_register.setOnClickListener(v -> openActivityRegister());
        deleteAccount.setOnClickListener(v -> openActivityRegister());
        bt_login.setOnClickListener(v -> {
            audioService.play(buttonSound,0);
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

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            audioService = binder.getService();
            isBound = true;

            backgroundMusic = Sound.searchByResid(R.raw.wanabe_epic_music);
            buttonSound = Sound.searchByResid(R.raw.pop);
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
        // Bind to LocalService
        intent = new Intent(this, MediaService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }
}