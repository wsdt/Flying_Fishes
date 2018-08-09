package yourowngame.com.yourowngame.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.manager.WorldMgr;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

/**
 * The GameViewActivity does only add the GameView!
 * Painting and action happens in GameView or GameLoopThread
 * <p>
 * TODO: create image asset for player, draw player, implement touchHandler
 */


public class GameViewActivity extends DrawableSurfaceActivity {
    private static final String TAG = "GameViewActivity";
    private GameView gameView;
    private TextView highscoreVal; //for the points

    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        /* Set highscore val textview */
        this.setHighscoreVal_textView((TextView) findViewById(R.id.gameViewActivity_highscoreVal));

        /* Master-call, create GameView*/
        setGameView(((GameView) findViewById(R.id.gameViewActivity_gameView)));

        //Start game
        getGameView().startGame(this, WorldMgr.getWorlds(this).get(
                WorldMgr.getCurr_world_index()).getAllLevels().get(WorldMgr.getCurr_lvl_index()));

        Log.d(TAG, "onCreate: Tried to load game.");
    }

    /**
     * This method should only be called by Observer-Pattern! (better performance)
     */
    public void setNewHighscoreOnUI() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getHighscoreVal_textView().setText(String.valueOf(getGameView().getHighscore().getValue())); //pure integers are not allowed by setText() so we transform it to a string
            }
        });
    }


    @Override
    protected void onStop() { //not onPause, bc. on exiting activity and onStart also onPause is called!
        super.onStop();
        pauseGame(null);
    } //do not make onResume(), bc. dialog should be shown and game should only resume, when resume is clicked and not automatically.

    public void pauseGame(@Nullable View v) {
        this.getGameView().getThread().pauseThread();
    }

    //GETTER/SETTER (Base class)
    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public TextView getHighscoreVal_textView() {
        return highscoreVal;
    }

    public void setHighscoreVal_textView(TextView highscoreVal) {
        this.highscoreVal = highscoreVal;
    }
}


