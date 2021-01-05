package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.codegrinders.treasure_hunter_mobile.R;

public class ActivityWon extends AppCompatActivity {

    Button bt_results_won;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);
        bt_results_won.findViewById(R.id.bt_results_won);
        bt_results_won.setOnClickListener(v -> openActivityResults());
    }

    private void openActivityResults() {
        Intent intent = new Intent(this, ActivityResults.class);
        startActivity(intent);
    }

}