package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class TrixVideoActivity extends Activity {

	private VideoView vvTrixVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_trix_video);

		SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor dspEditor = dsp.edit();

		dspEditor.putBoolean("resistance_td_opening_viewed", true);
		dspEditor.apply ();

		vvTrixVideo = findViewById(R.id.vvTrixVideo);
		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.trix);
		vvTrixVideo.setVideoURI(uri);
		vvTrixVideo.start();

		vvTrixVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				Intent i = new Intent (getBaseContext(), MainActivity.class);
				startActivity(i);
				finish ();
			}
		});
	}
}
