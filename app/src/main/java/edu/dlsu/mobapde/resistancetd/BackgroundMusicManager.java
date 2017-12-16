package edu.dlsu.mobapde.resistancetd;

/**
 * ResistanceTD
 * Created by Jolene Valenzuela on 2017-12-16.
 */

import android.content.Context;
import android.media.MediaPlayer;

public class BackgroundMusicManager {

    private MediaPlayer player;
    private Context context;
    public static final int MAIN_MENU = 0,
        GAME_PANEL = 1;

    public BackgroundMusicManager(Context context) {
        this.context = context;
    }

    public void playMusic(int type) {
        if (player != null) {
            player.reset();
            player.release();
        }
        switch (type) {
            case MAIN_MENU:
                player = MediaPlayer.create(context, R.raw.main_menu_bgm);
                player.setLooping(true);
                player.start();
                break;
            case GAME_PANEL:
                player = MediaPlayer.create(context, R.raw.game_bgm);
                player.setLooping(true);
                player.start();
                break;
        }
    }

    public void pauseMusic() {
        if (player != null)
            player.pause();
    }

    public void stopMusic() {
        if (player != null) {
            player.stop();
            player = null;
        }
    }

    public void resumeMusic() {
        if (player != null)
            player.start();
    }
}
