package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    private TextView tvScore, tvQuit;
    private TextView tvRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_over);

        initAttributes ();
    }

    private void initAttributes() {
        tvScore = findViewById(R.id.tv_score);
        tvRetry = findViewById(R.id.tv_retry);
        tvQuit = findViewById(R.id.tv_quit);

        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("retry", 0);
                startActivity(i);
                finish ();
            }
        });

        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish ();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DatabaseHelper db = new DatabaseHelper(getBaseContext());
            int score = extras.getInt("score");
            int waves = extras.getInt("waves");

            Gameplay gameplay = new Gameplay();
            gameplay.setScore(score);
            gameplay.setWaves(waves);

            db.addGameplay(gameplay);

            tvScore.setText(String.valueOf(score));
        }
    }

}
