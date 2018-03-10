package yourowngame.com.yourowngame.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * The GameViewActivity does only add the GameView!
 * Painting and action happens in GameView or GameLoopThread
 * <p>
 * TODO: create image asset for player, draw player, implement touchHandler
 */


public class GameViewActivity extends AppCompatActivity {
    private static final String TAG = "GameViewActivity";
    private FrameLayout gameLayout;
    private GameView gameView;
    public static int GAME_HEIGHT;
    public static int GAME_WIDTH;


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
        this.setGameView(new GameView(this));
        getGameLayout().addView(this.getGameView());

    }

    //Gets the current dimens, and saves it into STATIC Values, so we dont need to f* pass the activity onto the darkest point of our prog
    private void getGameDimens() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GAME_WIDTH = displayMetrics.widthPixels;
        GAME_HEIGHT = displayMetrics.heightPixels;
        Log.d(TAG, "HEIGHT = " + GAME_HEIGHT + "WIDTH = " + GAME_WIDTH);
    }

    /**
     * Custom OnClickListeners (recommendation: add them directly in xml)
     * --> Foreach action a own method (Principle: The only reason to change code should be an error) so switch case violates that
     */
    public void onShootBtn(View v) {
        getGameView().getPlayerOne().addProjectiles();
    }


    //GETTER/SETTER (Base class)
    public FrameLayout getGameLayout() {
        return gameLayout;
    }

    public void setGameLayout(FrameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    public FrameLayout getView() {
        return gameLayout;
    }

    public int getWidth() {
        return gameLayout.getWidth();
    }

    public int getHeight() {
        return gameLayout.getHeight();
    }


    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}


