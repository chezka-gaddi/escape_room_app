package edu.sdsmt.id7180120;

/**
 * Program Name: Android
 * Author: Chezka Gaddi
 * Class: CSC468 GUI M001
 * Description:
 * Last Tier Passed:
 * Extensions:
 * Known Bugs: None
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * The type Main activity.
 */
public class MainActivity extends Activity {

    private static final String SCORE_KEY = "currentScore";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView score;
    private GameView view;
    private Timer timer;
    private int current_score = 0;


    /**
     * @param savedInstanceState state saved
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (view.getEnd()) {
                            timer.cancel();
                            launchEndActivity();
                        }
                        else {
                            current_score += 1;
                            score.setText(getString(R.string.score, current_score));
                        }
                    }
                });
            }
        }, 0, 1000);

        score = (TextView) findViewById(R.id.score);
        view = (GameView) findViewById(R.id.gameView);

        // Get player type selection from SelectPlayerActivity
        String savedExtra = getIntent().getStringExtra("player");
        if (savedExtra != null)
            view.setPlayerShape(savedExtra);
    }

    /**
     * @param outState state to save
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SCORE_KEY, current_score);
        super.onSaveInstanceState(outState);
    }

    /**
     * @param savedInstanceState state saved
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        current_score = savedInstanceState.getInt(SCORE_KEY);
    }


    /**
     * Launch select player activity.
     *
     * @param view the view
     */
    public void launchSelectPlayerActivity(View view) {
        Log.d(LOG_TAG, "Select Player Activity");

        Intent intent = new Intent(this, PlayerSelectActivity.class);
        startActivity(intent);
    }


    /**
     * Launch EndActivity with the score.
     */
    private void launchEndActivity() {
        Log.d(LOG_TAG, "End Activity");

        Intent intent = new Intent(this, EndActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("score", "Score: " + current_score);
        startActivity(intent);
        finish();
    }
}
