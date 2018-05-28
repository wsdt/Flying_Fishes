package yourowngame.com.yourowngame.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;
import yourowngame.com.yourowngame.gameEngine.GameLoopThread;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * The GameViewActivity does only add the GameView!
 * Painting and action happens in GameView or GameLoopThread
 * <p>
 * TODO: create image asset for player, draw player, implement touchHandler
 */


public class GameViewActivity extends AppCompatActivity {
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


        Log.d(TAG, "onCreate: Trying to load game.");

        /** Master-call, create GameView*/
        setGameView(((GameView) findViewById(R.id.gameViewActivity_gameView)));
        getGameView().startGame(this);
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

    /** This method should only be called by Observer-Pattern! (better performance)*/
    public void setNewHighscoreOnUI() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getHighscoreVal_textView().setText(String.valueOf(getGameView().getHighscore().getValue())); //pure integers are not allowed by setText() so we transform it to a string

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGame(null);
    } //do not make onResume(), bc. dialog should be shown and game should only resume, when resume is clicked and not automatically.
    public void pauseGame(@Nullable View v) {
        this.getGameView().getThread().pauseGame();
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


