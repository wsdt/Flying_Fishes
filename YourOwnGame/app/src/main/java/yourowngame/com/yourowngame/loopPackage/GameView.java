package yourowngame.com.yourowngame.loopPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

/**
 * Created by Solution on 16.02.2018.
 *
 * GameView Surface, draw players here and in the end add it to the GameViewActivity
 *
 *
 */

public class GameView extends SurfaceView {
    private Bitmap bmp; //example for Player
    private AnimationDrawable animationDrawable; //TODO: player animation (temporary, should be saved as int into Player class!)
    private SurfaceHolder holder;
    private GameLoopThread thread;
    private Player playerOne;
    private OnTouchHandler touchHandler;
    private boolean isTouched = false;
    private static final String TAG = "GameView";



    public GameView(Context context) {
        super(context);

        /** Initialize GameObjects & eq here! */
        initGameObjects();
        initComponents();


        /**************************************
         * Start of the Surface & Thread Page *
         **************************************/
        thread = new GameLoopThread(this);
        holder = getHolder();

        getRootView().setOnTouchListener(touchHandler);

        //Callback-method: 3 Types
        holder.addCallback(new SurfaceHolder.Callback() {

            //Surface gets created, Thread starts
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                thread.setRunning(true);
                thread.start();
            }

            //No need
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                exitGame();
            }
        });

        //Just for testing purpose, create a bitmap for showing (will later be the GameObjects...)
        //bmp = BitmapFactory.decodeResource(getResources(), this.playerOne.getImg());
        //bmp = this.playerOne.getAnimatedImg(context);
        //animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.player_heli_blue_animated);
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.player_heli_blue_animated);
    }

    private void exitGame() {
        Log.d(TAG, "exitGame: Trying to exit game.");
        boolean retry = true;
        thread.setRunning(false);
        while(retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    public void initGameObjects(){
        playerOne = new Player(0, 400, 3, 1, new int[] {
                R.drawable.player_heli_blue_1, R.drawable.player_heli_blue_2, R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_4,
                R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_2}, "Rezy");
    }

    public void initComponents(){
        touchHandler = new OnTouchHandler();
    }


    /***************************
     * 1. Draw GameObjects here *
     ***************************/
    public void redraw(Canvas canvas, long loopCount) { //Create separate method, so we could add some things here
        Log.d(TAG, "redraw: Trying to invalidate/redraw GameView.");
        if (canvas != null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //remove previous bitmaps etc.

            //X coord bleibt gleich, sollte aber mitwachsen. Denkfehler von mir? haha
            // --> playerOne.update() --> Werte werden nicht statisch verändert, sondern hängen von SpeedX/Y ab (wo bei dir speedY == 0 war, vl hat sich deshalb ein Wert nicht geändert)


            try {
                canvas.drawBitmap(this.playerOne.getCraftedBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), 5f, 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
                //tried to make a nice flying animation (slight rotating to -5/+5 degree every few seconds) but hmm haha, too stupid now (just a normal animation above to show method functionality)
                //todo --> BUT: Flying animations could be also fully done in images itself (so no separate calcutation necessary (battery) and the SAME battery/cpu usage! :)
                //canvas.drawBitmap(this.playerOne.getCraftedBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), (float) ((((loopCount%100)+1)*Constants.GameLogic.GameView.playerEffectTiltDegreeChangeRate))*((loopCount%100 >= 50) ? 1 : (-1)), 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
                //canvas.drawBitmap(this.playerOne.getCraftedBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), (Constants.GameLogic.GameView.playerEffectTiltDegreePositive * ((loopCount%90 >= 45) ? 1 : (-1)))*(((loopCount%5)+1)*(0.25f)), 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
            } catch (NoDrawableInArrayFound_Exception e) {
                Log.e(TAG, "redraw: Could not draw image (error code: 404)");
                e.printStackTrace();
            }
            Log.d(TAG, "Tried to draw animationdrawable.");
        } else {
            exitGame();
        }
    }

    /* When refreshing/invalidating view surfaceView returns a black screen when we use onDraw() [use redraw() method above]


    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    public void updateGameObjects(){
        playerOne.update(this.touchHandler.isTouched(), true);
        System.out.println("Position x: " +playerOne.getPosX() + " / Position Y " + playerOne.getPosY());
    }

}
