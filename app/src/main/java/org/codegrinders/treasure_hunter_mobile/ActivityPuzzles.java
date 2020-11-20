package org.codegrinders.treasure_hunter_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ActivityPuzzles extends AppCompatActivity {

    Button bt_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        bt_continue = findViewById(R.id.bt_continue);
    }


}