package yourowngame.com.yourowngame.gameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.background.layers.BgLayerClouds1;

/**
 * Created by Solution on 16.02.2018.
 * <p>
 * GameView Surface, draw players here and in the end add it to the GameViewActivity
 */

public class GameView extends SurfaceView {
    private static final String TAG = "GameView";
    private static final float WIDTH_IN_PERCENTAGE = 0.35f;     //Should add it later to the Constants Interface
    private static final float HEIGHT_IN_PERCENTAGE = 0.35f;    //Should add it later to the Constants Interface

    private int counterOneTimeRendering = 0; //todo:no better solution that time
    private Canvas currentCanvas;
    private SurfaceHolder holder;
    private GameLoopThread thread;
    private Player playerOne;
    private OnTouchHandler touchHandler;
    private FrameLayout layout;
    private Bitmap viewStaticBackground;

    private GameView(Context context) {
        super(context);
    } //dummy constructor for android tools

    public GameView(GameViewActivity context) {
        super(context);

        /** Get the layout-res */
        layout = context.getView();

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
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                exitGame();
            }
        });
    }

    public void exitGame() {
        Log.d(TAG, "exitGame: Trying to exit game.");
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //initialize components that match GameObject()
    private void initGameObjects() {
        /** Player creation*/
        playerOne = new Player(100, getRootView().getHeight() / 4, 5, 1, new int[]{
                R.drawable.player_heli_blue_1, R.drawable.player_heli_blue_2, R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_4,
                R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_2}, "Rezy");

        /** Enemy creation */
        Enemy enemyFactory = Enemy.getInstance();
        enemyFactory.createEnemys(150, randomX(), randomY(), 10, 10, null, "Enemy");

        /** other creations here */
    }

    //initialize components that do not match other categories
    private void initComponents() {
        touchHandler = new OnTouchHandler();
        this.viewStaticBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        //this.setBackground(R.drawable.bglayer0_blue); //todo: in future static backgroud image instead of color
    }


    /*private void setGameBackground(@NonNull Canvas canvas, int bgColor) {
        //for now only color, later here image set
        if ((this.counterOneTimeRendering) == 0) { //todo do only onetime! (NOTE: incrementation removed, because we use drawColor 0 in redraw(), so we need to set bg color steadily! [BAD DESIGN, sry])
            //canvas.drawARGB(90,0,136,255);
            canvas.drawColor(getResources().getColor(bgColor));
            Log.d(TAG, "setGameBackground: Tried to set bg!");
            //this.setBackgroundColor(this.getResources().getColor(bgColor));
        }
    }*/


    /***************************
     * 1. Draw GameObjects here *
     ***************************/


    public void redraw(Canvas canvas, long loopCount) { //Create separate method, so we could add some things here
        Log.d(TAG, "redraw: Trying to invalidate/redraw GameView.");
        if (canvas != null) {
            //in loop, BUT we don't do anything if already set
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //remove previous bitmaps etc. (it does not work to set here only bg color!, because of mode)
            this.setCurrentCanvas(canvas); //so we can access it in other classes
            //this.setGameBackground(canvas, R.color.colorSkyBlue);

            try {
                //draw background
                loadDynamicBackgroundLayer(canvas);

                /** TODO draw enemies (on level 1 every second will spawn 10 enemys etc..) */
                // much fun kevin, i wont draw anything anymore! haha

                //draw player
                canvas.drawBitmap(this.playerOne.getCraftedDynamicBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length),
                        5f, WIDTH_IN_PERCENTAGE, HEIGHT_IN_PERCENTAGE), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
                //tried to make a nice flying animation (slight rotating to -5/+5 degree every few seconds) but hmm haha, too stupid now (just a normal animation above to show method functionality)
                //todo --> BUT: Flying animations could be also fully done in images itself (so no separate calcutation necessary (battery) and the SAME battery/cpu usage! :)
                //canvas.drawBitmap(this.playerOne.getCraftedDynamicBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), (float) ((((loopCount%100)+1)*Constants.GameLogic.GameView.playerEffectTiltDegreeChangeRate))*((loopCount%100 >= 50) ? 1 : (-1)), 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
                //canvas.drawBitmap(this.playerOne.getCraftedDynamicBitmap(this.getContext(), ((int) loopCount % this.playerOne.getImg().length), (Constants.GameLogic.GameView.playerEffectTiltDegreePositive * ((loopCount%90 >= 45) ? 1 : (-1)))*(((loopCount%5)+1)*(0.25f)), 0.35f, 0.35f), (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
            } catch (Exception e) {
                Log.e(TAG, "redraw: Could not draw images.");
                e.printStackTrace();
            }
            Log.d(TAG, "Tried to draw animationdrawable.");
        } else {
            exitGame();
        }
    }

    public void loadDynamicBackgroundLayer(Canvas canvas) {
        //drawing layer 0 (static background)
        canvas.drawBitmap(this.viewStaticBackground, 0f, 0f, null);

        //Set background (this = GAMEVIEW!!)
        BgLayerClouds1 layer1_clouds = (BgLayerClouds1) BackgroundManager.getInstance(this).getBackgroundLayers().get(0);
        if (layer1_clouds != null) {
            BgLayerClouds1.Cloud cloud1 = layer1_clouds.getCraftedClouds().get(0);
            if (cloud1 != null) {
                canvas.drawBitmap(cloud1.cloudImg,
                        cloud1.posX, cloud1.posY, null);
            }
        } else {
            Log.w(TAG, "redraw: Background layer 1 (clouds) not found!");
        }
    }

    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    public void updateGameObjects() {
        //Update the player handling                    should only be true if player collects box or equivalent!
        playerOne.update(null, this.touchHandler.isTouched(), false);

        //Check if player hits the view's border
        if (playerOne.collision(this, playerOne))
            exitGame();

        //Update background
        BackgroundManager.getInstance(this).updateAllBackgroundLayers();
    }


    //returns a random x - Position on the screen
    private double randomX() {
        return Math.random() * getRootView().getWidth();
    }

    //returns a random y - Position on the screen
    private double randomY() {
        return Math.random() * getRootView().getHeight();
    }

    public Canvas getCurrentCanvas() {
        return currentCanvas;
    }

    public void setCurrentCanvas(Canvas currentCanvas) {
        this.currentCanvas = currentCanvas;
    }

    public FrameLayout getLayout() {
        return layout;
    }
}
