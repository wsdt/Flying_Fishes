package yourowngame.com.yourowngame.loopPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.Player;

/**
 * Created by Solution on 16.02.2018.
 *
 * GameView Surface, draw players here and in the end add it to the GameViewActivity
 *
 *
 */

public class GameView extends SurfaceView {
    private Bitmap bmp; //example for Player
    private SurfaceHolder holder;
    private GameLoopThread thread;
    private Player playerOne;
    private OnTouchHandler touchHandler;
    private boolean isTouched = false;



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
                boolean retry = true;
                thread.setRunning(false);
                while(retry){
                    try {
                        thread.join();
                        retry = false;
                    }catch (InterruptedException e) {e.printStackTrace();};
                }
            }
        });

        //Just for testing purpose, create a bitmap for showing (will later be the GameObjects...)
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.player_testingpurpose);
    }

    public void initGameObjects(){
        playerOne = new Player(0, 150, 1, 0, R.drawable.player_testingpurpose, "Rezy");
    }

    public void initComponents(){
        touchHandler = new OnTouchHandler();
    }



    /***************************
     * 1. Draw GameObjects here *
     ***************************/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //X coord bleibt gleich, sollte aber mitwachsen. Denkfehler von mir? haha
        canvas.drawBitmap(bmp, (int) playerOne.getPosX(), (int) playerOne.getPosY(), null);
    }

    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    public void updateGameObjects(){
        playerOne.update(isTouched, true);
        System.out.println("Position x: " +playerOne.getPosX() + "Position Y " + playerOne.getPosY());
    }

}
