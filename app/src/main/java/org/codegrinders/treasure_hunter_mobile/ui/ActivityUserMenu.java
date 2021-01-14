package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.UsersCall;

public class ActivityUserMenu extends AppCompatActivity {

    Button bt_resume, bt_editProfile, bt_leaderBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermenu);

        bt_resume = findViewById(R.id.bt_resume);
        bt_editProfile = findViewById(R.id.bt_editProfile);
        bt_leaderBoard = findViewById(R.id.bt_leaderBoardMenu);

        bt_resume.setOnClickListener(v -> returnToActivityMap());
        bt_editProfile.setOnClickListener(v -> openActivityEditProfile());
    }

    private void returnToActivityMap() {
        finish();
    }

    private void openActivityEditProfile() {
        Intent intent = new Intent(this, ActivityEditProfile.class);
        startActivity(intent);
    }

}