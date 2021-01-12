package org.codegrinders.treasure_hunter_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.R;

public class ActivityEditProfile extends AppCompatActivity {

    Button bt_editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        bt_editEmail = findViewById(R.id.bt_editEmail);
        bt_editEmail.setOnClickListener(v -> openActivityEditEmail());
    }

    private void openActivityEditEmail() {
        Intent intent = new Intent(this, ActivityEditEmail.class);
        startActivity(intent);
    }
}
