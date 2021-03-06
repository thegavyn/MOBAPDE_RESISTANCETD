package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.view.Window;
import android.view.WindowManager;

public class ScoresActivity extends Activity {

    RecyclerView rvScores;
    DatabaseHelper databaseHelper;
    ScoresAdapter scoresAdapter;
    TextView tvBack;
    private BackgroundMusicManager bmm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scores);

        rvScores = findViewById(R.id.rvScores);

        databaseHelper = new DatabaseHelper(getBaseContext());
        scoresAdapter = new ScoresAdapter(getBaseContext(), databaseHelper.getAllGameplayRecordsCursor());
        rvScores.setAdapter(scoresAdapter);
        rvScores.setLayoutManager (new LinearLayoutManager(getBaseContext()));

        tvBack = findViewById(R.id.tv_back);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Background music
        bmm = new BackgroundMusicManager(getBaseContext());
        bmm.playMusic(BackgroundMusicManager.MAIN_MENU);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bmm.resumeMusic();
        scoresAdapter.changeCursor(databaseHelper.getAllGameplayRecordsCursor());
        bmm.resumeMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bmm.pauseMusic(); //pause player
    }

    @Override
    protected void onStop() {
        super.onStop();
        bmm.stopMusic();
    }
}
