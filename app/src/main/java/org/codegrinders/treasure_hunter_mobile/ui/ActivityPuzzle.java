package org.codegrinders.treasure_hunter_mobile.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.model.Puzzle;
import org.codegrinders.treasure_hunter_mobile.retrofit.MarkersCall;
import org.codegrinders.treasure_hunter_mobile.retrofit.PuzzlesCall;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;

public class ActivityPuzzle extends AppCompatActivity {

    Button bt_continue;
    TextView tv_question;
    TextView tv_puzzlePoints;
    EditText et_answer;

    PuzzlesCall puzzlesCall = new PuzzlesCall();
    RetroCallBack retroCallBack;
    Puzzle puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        bt_continue = findViewById(R.id.bt_continue);
        tv_question = findViewById(R.id.tv_question);
        tv_puzzlePoints = findViewById(R.id.tv_puzzlepoints);
        et_answer = findViewById(R.id.et_answer);

        retroCallBack = new RetroCallBack() {
            @Override
            public void onCallFinished(String callType) {
                if (callType.equals("Puzzles")) {
                    tv_question.setText(puzzlesCall.getQuestion());
                    tv_puzzlePoints.setText(getPuzzlePoints());
                }
                if (callType.equals("postAnswer")) {
                    puzzle = puzzlesCall.getPuzzle();
                        if (et_answer.getText().toString().equals(puzzle.getAnswer())) {
                            Toast.makeText(getApplicationContext(), ActivityMap.currentMarkerData.getDescription(), Toast.LENGTH_LONG).show();
                            ActivityMap.currentMarker.setVisible(false);
                            ActivityMap.currentMarkerData.setVisibility(false);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_LONG).show();
                        }
                        et_answer.setText("");
                }
            }

            @Override
            public void onCallFailed(String errorMessage) {
                tv_question.setText(errorMessage);
            }
        };
        puzzlesCall.setCallBack(retroCallBack);
        puzzlesCall.puzzlesGetRequest();
        bt_continue.setOnClickListener(v -> puzzlesCall.puzzleIsCorrect(et_answer.getText().toString()));
    }

    private String getPuzzlePoints() {
        return "Puzzle's points: " + puzzlesCall.getPoints();
    }
}