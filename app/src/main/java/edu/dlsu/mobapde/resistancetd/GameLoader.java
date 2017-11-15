package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.SurfaceView;
import android.widget.ProgressBar;

public class GameLoader extends AsyncTask<Integer, Integer, SurfaceView> {

	private ProgressBar progressBar;
	private Activity activity;

	public GameLoader(ProgressBar progressBar, Activity activity) {
		this.progressBar = progressBar;
		this.activity = activity;
	}

	@Override
	protected SurfaceView doInBackground(Integer... integers) {
		publishProgress(0);
		switch (integers[0]) {
			case 0:
//				SinglePlayerGamePanel spgp = new SinglePlayerGamePanel(activity.getBaseContext());
//
//
//				return
			case 1:
				// TODO: 11/15/2017
		}

		return null;
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		progressBar.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(SurfaceView surfaceView) {
		//activity.setContentView(surfaceView);
	}

}
