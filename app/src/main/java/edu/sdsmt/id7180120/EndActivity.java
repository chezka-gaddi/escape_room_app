package edu.sdsmt.id7180120;

/**
 * @file
 * @brief Contains the functions maintaining the EndActivity
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * The type End activity.
 */
public class EndActivity extends AppCompatActivity {

    /**
     * Display the score and cue end of game.
     * @param savedInstanceState state saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        String savedExtra = getIntent().getStringExtra("score");
        TextView score = (TextView) findViewById(R.id.end_score);
        score.setText(savedExtra);
    }
}
