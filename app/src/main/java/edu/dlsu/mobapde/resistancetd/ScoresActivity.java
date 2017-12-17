package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

public class ScoresActivity extends Activity {

    RecyclerView rvScores;
    DatabaseHelper databaseHelper;
    ScoresAdapter scoresAdapter;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        scoresAdapter.changeCursor(databaseHelper.getAllGameplayRecordsCursor());
    }
}
