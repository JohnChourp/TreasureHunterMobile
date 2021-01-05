package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.codegrinders.treasure_hunter_mobile.R;

public class ActivityResults extends AppCompatActivity {

    Button bt_results_won;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);
        bt_results_won = findViewById(R.id.bt_won);
        bt_results_won.setOnClickListener(v -> openActivityLeaderBoard());
    }

    private void openActivityLeaderBoard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
        startActivity(intent);
    }

}