package yourowngame.com.yourowngame.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.LevelAssignment;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.worlds.World_Earth;
import yourowngame.com.yourowngame.classes.game_modes.mode_survival.Level_Survival;
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

    /**
     * GameViewActivity Constants +++++++++++++++++++++++++++++++
     */
    public static final String INTENT_GAME_MODE = "GAME_MODE";
    public static final int GAMEMODE_ADVENTURE = 0;
    public static final int GAMEMODE_SURVIVAL = 1;

    private GameView gameView;
    private TextView highscoreVal;  // points counter


    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        /* Set highscore val textview */
        this.setHighscoreVal_textView((TextView) findViewById(R.id.gameViewActivity_highscoreVal));
        this.setTargetpoints((TextView) findViewById(R.id.targetPoints));



        /* Master-call, create GameView*/
        setGameView(((GameView) findViewById(DRAWABLE_SURFACE_ID)));

        //Start game
        Intent intent = getIntent();
        switch (intent.getIntExtra(INTENT_GAME_MODE, GAMEMODE_ADVENTURE)) { //by default adventure mode
            case GAMEMODE_ADVENTURE:
                getGameView().startGame(this, WorldMgr.getCurrLvl(this,false));
                break;
            case GAMEMODE_SURVIVAL:
                //TODO: make difficulty selectable
                getGameView().startGame(this, new Level_Survival(this, Level_Survival.DIFFICULTY.MEDIUM));
                break;
            default:
                Log.e(TAG, "onCreate: Gamemode not found.");
        }

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

    public void setTargetpoints(TextView targetPoints){
        DrawableLevel currLvl = WorldMgr.getCurrLvl(this,true);
        for (LevelAssignment la : ((Level) currLvl).getAllLevelAssignments()) {
            targetPoints.setText(getResources().getString(R.string.targetPoints, ""+((Level) currLvl).getAllLevelAssignments().get(0).getAmount()));
        }
    }
}


