package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if (!dsp.contains("resistance_td_opening_viewed")) {
            Intent i = new Intent (getBaseContext(), TrixVideoActivity.class);
            startActivity(i);
            finish();
        }
    }
}
