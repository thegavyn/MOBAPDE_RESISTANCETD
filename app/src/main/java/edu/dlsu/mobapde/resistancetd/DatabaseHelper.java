package edu.dlsu.mobapde.resistancetd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by muonsei on 12/7/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String SCHEMA = "resistanceTD";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    /**
     * This creates the tables of the schema and will only
     * be called at the start of the game. Schema will be
     * created if there is no DB found.
     *
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * CREATE TABLE gameplay-record (
         *      _id INTEGER AUTOINCREMENT PRIMARY KEY,
         *      score INTEGER
         * );
         *
         * CREATE TABLE loading-messages (
         *      _id INTEGER AUTOINCREMENT PRIMARY KEY,
         *      message TEXT
         * );
         */

        String createGameplayRecord = "CREATE TABLE " + Gameplay.TABLE_NAME + " ("
                + Gameplay.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Gameplay.COLUMN_SCORE + " INTEGER, "
                + Gameplay.COLUMN_WAVES + " INTEGER "
                + ");";

        db.execSQL(createGameplayRecord);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + Gameplay.TABLE_NAME + ";";

        db.execSQL(sql);

        onCreate(db);
    }

    /**
     * Adds gameplay to the db
     *
     * @param g gameplay record
     * @return whether initialization is successful or not
     */
    public boolean addGameplay(Gameplay g) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Gameplay.COLUMN_SCORE, g.getScore());
        contentValues.put(Gameplay.COLUMN_WAVES, g.getWaves());

        // id = -1 if fail
        long id = db.insert(Gameplay.TABLE_NAME,
                null,
                contentValues);
        db.close();

        return (id != -1);
    }

    /**
     * Adds gameplay to the db
     *
     * @param g  gameplay record
     * @param db database
     * @return whether initialization is successful or not
     */
    public boolean addGameplay(SQLiteDatabase db, Gameplay g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Gameplay.COLUMN_SCORE, g.getScore());
        contentValues.put(Gameplay.COLUMN_WAVES, g.getWaves());

        // id = -1 if fail
        long id = db.insert(Gameplay.TABLE_NAME,
                null,
                contentValues);

        return (id != -1);
    }

    /**
     * Returns all gameplays
     *
     * @return cursor
     */
    public ArrayList<Gameplay> getAllGameplayRecords() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Gameplay.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Gameplay.COLUMN_SCORE + " , " + Gameplay.COLUMN_WAVES + " DESC");

        ArrayList<Gameplay> gameplays = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Gameplay g = new Gameplay();
                g.setId(c.getLong(c.getColumnIndex(Gameplay.COLUMN_ID)));
                g.setScore(c.getInt(c.getColumnIndex(Gameplay.COLUMN_SCORE)));
                g.setWaves(c.getInt(c.getColumnIndex(Gameplay.COLUMN_WAVES)));

                gameplays.add(g);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return gameplays;
    }

    /**
     * Returns all gameplays - cursor version
     *
     * @return cursor
     */
    public Cursor getAllGameplayRecordsCursor() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(Gameplay.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Gameplay.COLUMN_SCORE + " , " + Gameplay.COLUMN_WAVES + " DESC");
    }
}