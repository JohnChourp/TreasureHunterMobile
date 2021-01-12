package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.UsersCall;

public class ActivityUserMenu extends AppCompatActivity {

    Button bt_start;
    Button bt_editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermenu);

        bt_start = findViewById(R.id.bt_start);
        bt_editProfile = findViewById(R.id.bt_editProfile);

        bt_start.setOnClickListener(v -> openActivityMap());
        bt_editProfile.setOnClickListener(v -> openActivityEditProfile());
    }

    private void openActivityMap() {
        Intent intent = new Intent(this, ActivityMap.class);
        intent.putExtra("User", UsersCall.user);
        startActivity(intent);
    }

    private void openActivityEditProfile() {
        Intent intent = new Intent(this, ActivityEditProfile.class);
        startActivity(intent);
    }
}