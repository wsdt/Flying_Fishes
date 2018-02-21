package yourowngame.com.yourowngame.gameEngine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

/**
 * Created by Solution on 16.02.2018.
 *
 * GameView Surface, draw players here and in the end add it to the GameViewActivity
 *
 *
 */

public class GameView extends SurfaceView {
    private int counterOneTimeRendering = 0; //todo:no better solution that time
    private Canvas currentCanvas;
    private SurfaceHolder holder;
    private GameLoopThread thread;
    private Player playerOne;
    private OnTouchHandler touchHandler;
    private static final String TAG = "GameView";
    private int viewWidth;
    private int viewHeight;

    private GameView(Context context) {super(context);} //dummy constructor for android tools

    public GameView(Activity context) {
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

    //initialize components that match GameObject()
    private void initGameObjects(){
        /** Player creation*/
        playerOne = new Player(100, getRootView().getHeight()/4, 5, 1, new int[] {
                R.drawable.player_heli_blue_1, R.drawable.player_heli_blue_2, R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_4,
                R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_2}, "Rezy");

        /** Enemy creation */
        Enemy enemyFactory = Enemy.getInstance();
        enemyFactory.createEnemys(150, randomX(), randomY(),
                                  10, 10, null, "Enemy");

        /** other creations creating here */
    }

    //initialize components that do not match other categories
    private void initComponents(){
        touchHandler = new OnTouchHandler();

        //this.setBackground(R.drawable.bglayer0_blue); //todo: in future static backgroud image instead of color
    }

    private void setGameBackground(@NonNull Canvas canvas, int bgColor) {
        //for now only color, later here image set
        if ((this.counterOneTimeRendering) == 0) { //todo do only onetime! (NOTE: incrementation removed, because we use drawColor 0 in redraw(), so we need to set bg color steadily! [BAD DESIGN, sry])
            //canvas.drawARGB(90,0,136,255);
            canvas.drawColor(getResources().getColor(bgColor));
            Log.d(TAG, "setGameBackground: Tried to set bg!");
            //this.setBackgroundColor(this.getResources().getColor(bgColor));
        }
    }



    /***************************
     * 1. Draw GameObjects here *
     ***************************/
    public void redraw(Canvas canvas, long loopCount) { //Create separate method, so we could add some things here
        Log.d(TAG, "redraw: Trying to invalidate/redraw GameView.");
        if (canvas != null) {
            //in loop, BUT we don't do anything if already set
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //remove previous bitmaps etc. (it does not work to set here only bg color!, because of mode)
            this.setCurrentCanvas(canvas); //so we can access it in other classes
            this.setGameBackground(canvas, R.color.colorSkyBlue);

            try {
                //draw background
                loadDynamicBackgroundLayer(canvas);

                /** TODO draw enemys (on level 1 every second will spawn 10 enemys etc..) */
                // much fun kevin, i wont draw anything anymore! haha

                //draw player
                canvas.drawBitmap(this.playerOne.getCraftedBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), 5f, 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
                //tried to make a nice flying animation (slight rotating to -5/+5 degree every few seconds) but hmm haha, too stupid now (just a normal animation above to show method functionality)
                //todo --> BUT: Flying animations could be also fully done in images itself (so no separate calcutation necessary (battery) and the SAME battery/cpu usage! :)
                //canvas.drawBitmap(this.playerOne.getCraftedBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), (float) ((((loopCount%100)+1)*Constants.GameLogic.GameView.playerEffectTiltDegreeChangeRate))*((loopCount%100 >= 50) ? 1 : (-1)), 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
                //canvas.drawBitmap(this.playerOne.getCraftedBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), (Constants.GameLogic.GameView.playerEffectTiltDegreePositive * ((loopCount%90 >= 45) ? 1 : (-1)))*(((loopCount%5)+1)*(0.25f)), 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
            } catch (Exception e) {
                Log.e(TAG, "redraw: Could not draw images.");
                e.printStackTrace();
            }
            Log.d(TAG, "Tried to draw animationdrawable.");
        } else {
            exitGame();
        }
    }

    public void loadDynamicBackgroundLayer(final Canvas canvas) {
        //Set background (this = GAMEVIEW!!)
        Background layer1_clouds = BackgroundManager.getInstance(this).getBackgroundLayers().get(0);
        if (layer1_clouds != null) {
            //canvas.drawBitmap(layer1_clouds.getCraftedBitmap(getContext()),
                    //(int) layer1_clouds.getX(), (int) layer1_clouds.getY(), null);
        } else {
            Log.w(TAG, "redraw: Background layer 1 (clouds) not found!");
        }
    }

    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    public void updateGameObjects(){
        //Update the player handling                    should only be true if player collects box or equivalent!
        playerOne.update(this.touchHandler.isTouched(), false);
        //Check if player is still in sight
        this.checkPlayerBounds();


        //Update background
        BackgroundManager.getInstance(this).updateAllBackgroundLayers();
    }

    /** Check if player hits the ground or top */
    public void checkPlayerBounds(){
        //first, get the size of the player's image (unfortunately not dynamic, neet to be the current player!)
        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.player_heli_blue_1);
        int height = bd.getBitmap().getHeight();
        //int width  = bd.getBitmap().getWidth(); no need for, player will never reach left or right end!


        //(1) player reaches bottom
        //would work just fine, but i guess the drawable gets drawn bigger than it really is.. should have made bounds :-(
        if(playerOne.getPosY() + height > this.getRootView().getHeight()) {
            Log.d(TAG, "PosY at: " + playerOne.getPosY() + height);
            exitGame();
        }
    }


    //returns a random x - Position on the screen
    private double randomX(){
        return Math.random() * getRootView().getWidth();
    }
    //returns a random y - Position on the screen
    private double randomY(){
        return Math.random() * getRootView().getHeight();
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public Canvas getCurrentCanvas() {
        return currentCanvas;
    }

    public void setCurrentCanvas(Canvas currentCanvas) {
        this.currentCanvas = currentCanvas;
    }
}
