package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.codegrinders.treasure_hunter_mobile.R;

public class ActivityLost extends AppCompatActivity {

    Button bt_results_lost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        bt_results_lost.findViewById(R.id.bt_results_lost);
        bt_results_lost.setOnClickListener(v -> openActivityResults());
    }

    private void openActivityResults() {
        Intent intent = new Intent(this, ActivityResults.class);
        startActivity(intent);
    }
}