package org.codegrinders.tresure_hunter_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStart extends AppCompatActivity
{
    Button bt_play;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bt_play = findViewById(R.id.bt_play);

        bt_play.setOnClickListener(v -> openActivityLogin());
    }


    private void openActivityLogin()
    {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

}