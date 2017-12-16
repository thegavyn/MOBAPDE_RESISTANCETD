package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends Activity {

    TextView tvBack;
    private BackgroundMusicManager bmm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        tvBack = findViewById(R.id.tv_back);
        tvBack = (TextView) findViewById(R.id.tv_back);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

        // Background music
        bmm = new BackgroundMusicManager(getBaseContext());
        bmm.playMusic(BackgroundMusicManager.MAIN_MENU);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bmm.pauseMusic(); //pause player
    }

    @Override
    protected void onResume() {
        super.onResume();
        bmm.resumeMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bmm.stopMusic();
    }
}
