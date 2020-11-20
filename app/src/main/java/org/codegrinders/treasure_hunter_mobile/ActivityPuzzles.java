package org.codegrinders.treasure_hunter_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityPuzzles extends AppCompatActivity {

    Button bt_continue;
    TextView tv_question;
    EditText et_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        bt_continue = findViewById(R.id.bt_continue);
        tv_question = findViewById(R.id.tv_question);
        et_answer = findViewById(R.id.et_answer);



        bt_continue.setOnClickListener(v -> {
            //tv_question.setText("");
        });

    }


}