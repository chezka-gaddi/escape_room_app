package edu.sdsmt.id7180120;

/**
 * @file
 * @brief Contains the functions to maintain PlayerSelectActivity.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * The type Player select activity.
 */
public class PlayerSelectActivity extends AppCompatActivity {

    /**
     * Displays options.
     * @param savedInstanceState state saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
    }

    /**
     * Return square player.
     *
     * @param view the view
     */
    public void returnSquarePlayer(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("player", "square");
        startActivity(intent);
    }

    /**
     * Return circle player.
     *
     * @param view the view
     */
    public void returnCirclePlayer(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("player", "circle");
        startActivity(intent);
    }
}
