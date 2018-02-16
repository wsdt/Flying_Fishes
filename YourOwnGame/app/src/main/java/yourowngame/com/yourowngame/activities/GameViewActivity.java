package yourowngame.com.yourowngame.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.loopPackage.GameView;

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

        setGameLayout((FrameLayout) findViewById(R.id.gameViewLayout));

        //Master-Call, GameView gets instantiated!
        getGameLayout().addView(new GameView(this));

    }

 //Deleted: old gameStart() - Method
 //Deleted: setPlayerOne()
 //Deleted: setBitMap



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


