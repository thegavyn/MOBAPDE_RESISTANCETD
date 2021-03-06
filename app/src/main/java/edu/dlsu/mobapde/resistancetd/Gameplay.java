package edu.dlsu.mobapde.resistancetd;

/**
 * Created by muonsei on 12/7/17.
 */

public class Gameplay {
    public static final String TABLE_NAME = "Records";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_WAVES = "waves";

    private long id;
    private int score;
    private int waves;

    public Gameplay() {

    }

    public Gameplay(int score, int waves) {
        setScore(score);
        setWaves(waves);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWaves() {
        return waves;
    }

    public void setWaves(int waves) {
        this.waves = waves;
    }
}