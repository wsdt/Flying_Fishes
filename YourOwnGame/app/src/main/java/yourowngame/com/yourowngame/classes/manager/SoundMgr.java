package yourowngame.com.yourowngame.classes.manager;


import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;

public class SoundMgr {
    private MediaPlayer mediaPlayer;

    public void play(@NonNull Context context, int resSound, boolean looping) {
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

    public void stop() {
        if (getMediaPlayer() != null) {
            getMediaPlayer().stop();
        }
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        finishMediaPlayer();
    }

    public void finishMediaPlayer() {
        //Release memory
        if (getMediaPlayer() != null) {
            getMediaPlayer().release();
            setMediaPlayer(null);
        }
    }


    //GETTER/SETTER ---------------------------------
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
