package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tvMainSinglePlayer, tvMainMultiplayer;
	private SharedPreferences dsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		// set to full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// remove title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

		// shared preferences
		dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		checkOpeningViewed();

		// initialize elements and add listeners
		initAttributes();
    }

    private void initAttributes () {
		tvMainSinglePlayer = findViewById(R.id.tvMainSinglePlayer);
		tvMainMultiplayer = findViewById(R.id.tvMainMultiplayer);

		tvMainSinglePlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startGame(0);
			}
		});
		tvMainMultiplayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startGame(1);
			}
		});
	}

	private void startGame (int gameMode) {
		switch (gameMode) {
			case 0:
				new GameLoader().execute(new SinglePlayerGamePanel(this));
				break;
			case 1:
		}
	}

    private void checkOpeningViewed () {
		if (!dsp.contains("resistance_td_opening_viewed")) {
			Intent i = new Intent (getBaseContext(), TrixVideoActivity.class);
			startActivity(i);
			finish();
		}
	}

	private class GameLoader extends AsyncTask <SurfaceView, Integer, SurfaceView> {

		ProgressBar progressBar;

		@Override
		protected SurfaceView doInBackground(SurfaceView... surfaceViews) {
			SurfaceView gameView = surfaceViews[0];

			GamePanelInitializer gpi = (GamePanelInitializer) gameView;
			gpi.initialize();

			while (!(gpi.isInitialized())) {
				publishProgress(gpi.status());
				try {
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return gameView;
		}

		@Override
		protected void onPreExecute() {
			setContentView(R.layout.activity_loading_screen);
			progressBar = findViewById(R.id.pbLoadingProgress);
			progressBar.setProgress(0);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressBar.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(SurfaceView surfaceView) {
			setContentView(surfaceView);
		}

	}

}
