package edu.dlsu.mobapde.resistancetd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tvScore, tvRetry, tvQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvScore = (TextView) findViewById(R.id.tv_score);
        tvRetry = (TextView) findViewById(R.id.tv_score);
        tvQuit = (TextView) findViewById(R.id.tv_quite)
    }
}
