package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-16.
 */

public class ScoresAdapter extends CursorRecyclerViewAdapter  {

    public class ScoresViewHolder extends RecyclerView.ViewHolder {
        TextView tvScore;
        TextView tvWaves;

        public ScoresViewHolder (View itemView) {
            super (itemView);

            tvScore = itemView.findViewById(R.id.tvScore);
            tvWaves = itemView.findViewById(R.id.tvWaves);
        }
    }

    public ScoresAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        onBindViewHolder((ScoresViewHolder) viewHolder, cursor);
    }

    public void onBindViewHolder(ScoresViewHolder viewHolder, Cursor cursor) {
        int score = cursor.getInt(cursor.getColumnIndex(Gameplay.COLUMN_SCORE));
        int waves = cursor.getInt(cursor.getColumnIndex(Gameplay.COLUMN_WAVES));
        viewHolder.tvScore.setText(String.valueOf(score));
        viewHolder.tvWaves.setText(String.valueOf(waves));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_score, parent, false);

        return new ScoresViewHolder(v);
    }
}
