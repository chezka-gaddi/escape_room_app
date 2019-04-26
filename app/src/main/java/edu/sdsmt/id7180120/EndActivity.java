package edu.sdsmt.id7180120;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        String savedExtra = getIntent().getStringExtra("score");
        TextView score = (TextView) findViewById(R.id.end_score);
        score.setText(savedExtra);
    }
}
