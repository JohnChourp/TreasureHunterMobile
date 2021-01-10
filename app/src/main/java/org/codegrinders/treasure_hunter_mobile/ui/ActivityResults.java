package org.codegrinders.treasure_hunter_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.codegrinders.treasure_hunter_mobile.R;

public class ActivityResults extends AppCompatActivity {

    TextView tv_result;
    Button bt_results_won;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        bt_results_won = findViewById(R.id.bt_won);
        tv_result = findViewById(R.id.tv_result);
        bt_results_won.setOnClickListener(v -> openActivityLeaderBoard());

        if(ActivityMap.usersCall.user.getPoints() > 400){
            tv_result.setText(ActivityMap.usersCall.user.getUsername() + " You Won the Game!!!!");
        }else{
            tv_result.setText(ActivityMap.usersCall.user.getUsername() + " You Didn't Win the Game,Try Again Next Time");
        }
    }

    private void openActivityLeaderBoard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
        startActivity(intent);
    }

}