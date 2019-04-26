package edu.sdsmt.id7180120;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ColorSelectActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_select);
    }

    public void selectColor(int color) {
        Log.d(LOG_TAG, "Made it inside the Color Picker Activity");
    }
}
