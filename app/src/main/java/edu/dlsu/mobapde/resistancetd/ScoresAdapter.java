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
        TextView tvId;
        TextView tvScore;
        TextView tvWaves;

        public ScoresViewHolder (View itemView) {
            super (itemView);

            tvId = itemView.findViewById(R.id.tvID);
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
        long id = cursor.getLong(cursor.getColumnIndex(Gameplay.COLUMN_ID));
        int score = cursor.getInt(cursor.getColumnIndex(Gameplay.COLUMN_SCORE));
        int waves = cursor.getInt(cursor.getColumnIndex(Gameplay.COLUMN_WAVES));
        viewHolder.tvId.setText(id + "");
        viewHolder.tvScore.setText(score + "");
        viewHolder.tvWaves.setText(waves + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_score, parent, false);

        return new ScoresViewHolder(v);
    }
}
