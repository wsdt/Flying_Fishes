package yourowngame.com.yourowngame.classes.manager;


import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;

public class SoundMgr {
    private static MediaPlayer mediaPlayer;

    private SoundMgr() {} //no instance allowed

    public static void play(@NonNull Context context, int resSound, boolean looping) {
        setMediaPlayer(MediaPlayer.create(context, resSound));

        getMediaPlayer().setLooping(looping);
        getMediaPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                getMediaPlayer().start();
            }
        });
        getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //After media has streamed successfully we can free mediaplayer
                finishMediaPlayer();
            }
        });
        getMediaPlayer().prepareAsync();
    }

    public static void stop() {
        if (getMediaPlayer() != null) {
            getMediaPlayer().stop();
        }
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        finishMediaPlayer();
    }

    public static void finishMediaPlayer() {
        //Release memory
        if (getMediaPlayer() != null) {
            getMediaPlayer().release();
            setMediaPlayer(null);
        }
    }


    //GETTER/SETTER ---------------------------------
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        SoundMgr.mediaPlayer = mediaPlayer;
    }
}
