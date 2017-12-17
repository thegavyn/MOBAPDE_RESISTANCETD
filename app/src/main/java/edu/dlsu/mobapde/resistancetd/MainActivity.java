package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tvMainSinglePlayer, tvMainScore, tvHelp;
	private BackgroundMusicManager bmm;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        try {
			int n = getIntent().getExtras().getInt("retry", -1);

			if (n == 0)
				startGame(n);
		} catch (Exception e) {}

		initAttributes();

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

    private void initAttributes () {
		tvMainSinglePlayer = findViewById(R.id.tvMainSinglePlayer);
		tvMainScore = findViewById(R.id.tvMainScore);
		tvHelp = findViewById(R.id.tvHelp);

		tvMainSinglePlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startGame(0);
			}
		});

		tvMainScore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent (getBaseContext(), ScoresActivity.class);
				startActivity(i);
				finish();
			}
		});

		tvHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), HelpActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	public void startGame (int gameMode) {
		switch (gameMode) {
			case 0:
				bmm.stopMusic();
				new GameLoader().execute(new SinglePlayerGamePanel(this));
				break;
			case 1:
		}
	}

	private class GameLoader extends AsyncTask <GamePanel, Integer, GamePanel> {

        private ProgressBar progressBar;

		@Override
		protected GamePanel doInBackground(GamePanel... gamePanels) {
			GamePanel gamePanel = gamePanels[0];

			gamePanel.initialize();

			while (!(gamePanel.isInitialized())) {
				publishProgress(gamePanel.status());
				try {
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			publishProgress(gamePanel.status());

			return gamePanel;
		}

		@Override
		protected void onPreExecute() {
            setContentView(R.layout.activity_loading_screen);
            progressBar = findViewById(R.id.pbLoadingProgress);
            progressBar.setProgress(0);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				progressBar.setProgress(values[0], true);
			} else {
				progressBar.setProgress(values[0]);
			}
		}

		@Override
		protected void onPostExecute(GamePanel gamePanels) {
			setContentView(gamePanels);
		}

	}

}
