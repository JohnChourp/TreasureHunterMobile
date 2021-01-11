package org.codegrinders.treasure_hunter_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.model.User;

public class ActivityUserMenu extends AppCompatActivity {

    Button bt_start;
    Button bt_editProfile;
    Button bt_deleteProfile;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermenu);

        bt_start = findViewById(R.id.bt_start);
        bt_editProfile = findViewById(R.id.bt_editProfile);
        bt_deleteProfile = findViewById(R.id.bt_deleteProfile);

        bt_start.setOnClickListener(v -> openActivityMap());
        bt_editProfile.setOnClickListener(v -> openActivityEditProfile());
        bt_deleteProfile.setOnClickListener(v -> openActivityDeleteProfile());

    }

    private void openActivityMap() {
        Intent intent = new Intent(this,ActivityMap.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    private void openActivityEditProfile() {
        Intent intent = new Intent(this, ActivityEditProfile.class);
        startActivity(intent);
    }

    private void openActivityDeleteProfile() {
        Intent intent = new Intent(this, ActivityDeleteProfile.class);
        startActivity(intent);
    }

}
