package yourowngame.com.yourowngame.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * The GameViewActivity does only add the GameView!
 * Painting and action happens in GameView or GameLoopThread
 *
 * TODO: create image asset for player, draw player, implement touchHandler
 *
 *
 */


public class GameViewActivity extends AppCompatActivity {
    private static final String TAG = "GameViewActivity";
    private FrameLayout gameLayout;
    private Player playerOne;
    private Bitmap map; //just for testing purpose

    private boolean isTouched = false; //Screen is touched, player will move (DrawablePanel manages this)

    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        /*If we want to hide actionbar: (but I think we could add pause button etc. in actionbar)
        if (getSupportActionBar() == null) {Log.i(TAG, "Actionbar already gone/hidden.");} else {getSupportActionBar().hide();}*/

        Log.d(TAG, "onCreate: Trying to load game.");
        setGameLayout((FrameLayout) findViewById(R.id.gameViewLayout));

        //Master-Call, GameView gets instantiated!
        getGameLayout().addView(new GameView(this));

    }

    //GETTER/SETTER (Base class)
    public FrameLayout getGameLayout() {
        return gameLayout;
    }

    public void setGameLayout(FrameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }
}


