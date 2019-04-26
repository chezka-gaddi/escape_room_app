package edu.sdsmt.id7180120;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlayerSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
    }

    public void returnSquarePlayer(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("player", "square");
        startActivity(intent);
    }

    public void returnCirclePlayer(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("player", "circle");
        startActivity(intent);
    }
}
