package yourowngame.com.yourowngame.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.World;
import yourowngame.com.yourowngame.classes.gamedesign.WorldManager;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;
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
    private static SoundMgr soundMgr = new SoundMgr();
    private TextView highscoreVal; //for the points
    public static int GAME_HEIGHT;
    public static int GAME_WIDTH;


    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGameDimens();
        setContentView(R.layout.activity_game_view);

        /* Set highscore val textview */
        this.setHighscoreVal_textView((TextView) findViewById(R.id.gameViewActivity_highscoreVal));

        /* Master-call, create GameView*/
        setGameView(((GameView) findViewById(R.id.gameViewActivity_gameView)));

        /* RECEIVE Level */
        Intent intent = getIntent();
        if (intent != null) {
            // [0] = World ; [1] = X ; [2] = Y
            int[] lvlRepresentants = intent.getIntArrayExtra(World.INTENT_EXTRAID_POINT);
            if (lvlRepresentants != null) {
                getGameView().startGame(this, WorldManager.getWorlds().get(
                        lvlRepresentants[0]).getAllLevels().get(
                        new Point(lvlRepresentants[1], lvlRepresentants[2])));
            } else {
                showLvlLoadError();
            }
        } else {
            showLvlLoadError();
        }

        Log.d(TAG, "onCreate: Tried to load game.");
    }

    private void showLvlLoadError() {
        Toast.makeText(this,R.string.error_gameViewActivity_lvlLoad, Toast.LENGTH_LONG).show();
        Log.e(TAG, getResources().getString(R.string.error_gameViewActivity_lvlLoad));
    }

    @Deprecated
    @Enhance(byDeveloper = "Solution",
            message = "I wanna keep it that way, the GameViewActivity should provide the metrics," +
                    "so the levelHierarchy will deliver it! See @LevelHierarchyActivity.java",
            priority = Enhance.Priority.LOW)
    public void getGameDimens() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GAME_WIDTH = displayMetrics.widthPixels;
        GAME_HEIGHT = displayMetrics.heightPixels;
        Log.d(TAG, "HEIGHT = " + GAME_HEIGHT + "WIDTH = " + GAME_WIDTH);
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


