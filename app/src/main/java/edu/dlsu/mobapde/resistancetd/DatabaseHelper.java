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
    public static final String SCHEMA = "resistance-td";
    public static final int VERSION = 1;
    public static final String TABLE_MESSAGE = "loading-messages";

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    /**
     * This creates the tables of the schema and will only
     * be called at the start of the game. Schema will be
     * created if there is no DB found.
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * CREATE TABLE gameplay-record (
         *      _id INTEGER AUTOINCREMENT PRIMARY KEY,
         *      name TEXT,
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
                + Gameplay.COLUMN_NAME + " TEXT, "
                + Gameplay.COLUMN_SCORE + " INTEGER, "
                + Gameplay.COLUMN_WAVES + " INTEGER, "
                + ");";

        String createLoadingMessages = "CREATE TABLE " + TABLE_MESSAGE + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "message TEXT"
                + ");";

        db.execSQL(createGameplayRecord);
        db.execSQL(createLoadingMessages);
        initializeMessages();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Adds loading messages to the DB.
     * @return whether initialization is successful or not
     */
    public boolean initializeMessages() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv;
        Long id;

        String[] messages = {
                "Trivia #1",
                "Trivia #2",
                "Trivia #3",
                "Trivia #4",
                "Trivia #5"
        };

        for (String s : messages) {
            cv = new ContentValues();
            cv.put("message", s);
            id = db.insert(TABLE_MESSAGE,
                    null,
                    cv);
            if (id == -1)
                return false;
        }

        db.close();
        return true;
    }

    /**
     * Returns a random message from the DB
     * @return message
     */
    public String getRandomMessage() {
        Random rand = new Random();
        // TODO do not make this constant 5. Make a way na kukuhanin ilan yung current messages
        int n = rand.nextInt(5) + 1;
        // 5 is the maximum and the 1 is our minimum

        String message = "";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_MESSAGE,
                null,
                "_id = ? ",
                new String[] {
                    n + ""
                },
                null,
                null,
                null);

        if(c.moveToFirst()) {
            message = c.getString(c.getColumnIndex("message"));
        }

        c.close();
        db.close();

        return message;
    }

    /**
     * Adds gameplay to the db
     * @param g gameplay record
     * @return whether initialization is successful or not
     */
    public boolean addGameplay(Gameplay g) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Gameplay.COLUMN_ID, g.getId());
        contentValues.put(Gameplay.COLUMN_NAME, g.getName());
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
     * @param g gameplay record
     * @param db database
     * @return whether initialization is successful or not
     */
    public boolean addGameplay(SQLiteDatabase db, Gameplay g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Gameplay.COLUMN_ID, g.getId());
        contentValues.put(Gameplay.COLUMN_NAME, g.getName());
        contentValues.put(Gameplay.COLUMN_WAVES, g.getWaves());

        // id = -1 if fail
        long id = db.insert(Gameplay.TABLE_NAME,
                null,
                contentValues);
        db.close();

        return (id != -1);
    }

    /**
     * Returns all gameplays
     * @return cursor
     */
    public ArrayList<Gameplay> getAllGameplayRecords() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Gameplay.TABLE_NAME,null,null,null,null,null,null);

        ArrayList<Gameplay> gameplays = null;

        if (c.moveToFirst()) {
            do {
                Gameplay g = null;
                g.setId(c.getLong(c.getColumnIndex(Gameplay.COLUMN_ID)));
                g.setName(c.getString(c.getColumnIndex(Gameplay.COLUMN_NAME)));
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
     * @return cursor
     */
    public Cursor getAllGameplayRecordsCursor() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Gameplay.TABLE_NAME, null,null,null,null,null,null);
    }
}
