package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class InfoActivity extends Activity {

    TextView tvBack;
    private BackgroundMusicManager bmm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info);

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
