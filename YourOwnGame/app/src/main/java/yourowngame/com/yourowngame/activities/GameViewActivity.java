package yourowngame.com.yourowngame.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.webkit.WebIconDatabase;
import android.widget.FrameLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * The GameViewActivity does only add the GameView!
 * Painting and action happens in GameView or GameLoopThread
 *
 * TODO: create image asset for player, draw player, implement touchHandler
 */


public class GameViewActivity extends AppCompatActivity {
    private static final String TAG = "GameViewActivity";
    private FrameLayout gameLayout;
    private Player playerOne;
    public static int GAME_HEIGHT;
    public static int GAME_WIDTH;
    private String munition;


    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        getGameDimens();

        /*If we want to hide actionbar: (but I think we could add pause button etc. in actionbar)
        if (getSupportActionBar() == null) {Log.i(TAG, "Actionbar already gone/hidden.");} else {getSupportActionBar().hide();}*/

        Log.d(TAG, "onCreate: Trying to load game.");
        setGameLayout((FrameLayout) findViewById(R.id.gameViewLayout));

        /** Master-call, create GameView*/
        getGameLayout().addView(new GameView(this));

    }

    //Gets the current dimens, and saves it into STATIC Values, so we dont need to f* pass the activity onto the darkest point of our prog
    private void getGameDimens(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GAME_WIDTH = displayMetrics.widthPixels;
        GAME_HEIGHT = displayMetrics.heightPixels;
        Log.d(TAG, "HEIGHT = " + GAME_HEIGHT + "WIDTH = " + GAME_WIDTH);
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

    public FrameLayout getView(){
        return gameLayout;
    }

    public int getWidth(){
        return gameLayout.getWidth();
    }

    public int getHeight(){
        return gameLayout.getHeight();
    }


}


