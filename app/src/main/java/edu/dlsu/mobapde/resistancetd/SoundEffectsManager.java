package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * ResistanceTD
 * Created by Jolene Valenzuela on 2017-12-16.
 */

public class SoundEffectsManager {

    private SoundPool pool;
    private int sBuild1, sBuild2, sBuild3,
        sPain1, sPain2, sPain3, sDeath, sBacDeath, sFire;
    public static final int
        // by tower level
        BUILD1 = 0, BUILD2 = 1, BUILD3 = 2,
        // by remaining health (0-30%, 31-60%, 61-90%)
        PAIN1 = 3, PAIN2 = 4, PAIN3 = 5,
        //dead
        DEATH = 6,
        // bacteria death
        BACDEATH = 7,
        // firing
        FIRE = 8;


    SoundEffectsManager(Context context) {
        SoundPool.Builder builder = new SoundPool.Builder().setMaxStreams(10);
        pool = builder.build();
        sBuild1 = pool.load(context, R.raw.tower_build_1, 1);
        sBuild2 = pool.load(context, R.raw.tower_build_2, 1);
        sBuild3 = pool.load(context, R.raw.tower_build_3,1);
        sPain1 = pool.load(context, R.raw.first_30_percent, 1);
        sPain2 = pool.load(context, R.raw.next_30_percent, 1);
        sPain3 = pool.load(context, R.raw.last_30_percent, 1);
        sDeath = pool.load(context, R.raw.dead_trix, 1);
        sBacDeath = pool.load(context, R.raw.bac_death, 1);
        sFire = pool.load(context, R.raw.fire, 1);
        pool.setRate(sFire, 66150);
        pool.setRate(sBacDeath, 66150);
        pool.setRate(sDeath, 66150);
    }

    public void playSFX(int type) {
        switch(type) {
            case BUILD1: pool.play(sBuild1, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case BUILD2: pool.play(sBuild2, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case BUILD3: pool.play(sBuild3, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case PAIN1: pool.play(sPain1, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case PAIN2: pool.play(sPain2, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case PAIN3: pool.play(sPain3, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case DEATH: pool.play(sDeath, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case BACDEATH: pool.play(sBacDeath, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
//            case FIRE: pool.play(sFire, 1.0f, 1.0f, 0, 0, 1.5f);
//                break;
        }
    }
}
