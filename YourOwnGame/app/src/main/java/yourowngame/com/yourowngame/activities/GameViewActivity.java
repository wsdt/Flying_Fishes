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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.Player;

/**
 * The GameViewActivity contains the GameLogic.
 *
 * It defines a inner class providing a drawing Panel,
 * in which the obj are gettin drawn.
 *
 * TODO: create image asset for player, draw player, implement touchHandler
 *
 *
 */


public class GameViewActivity extends AppCompatActivity  {
    private static final String TAG = "GameViewActivity";
    private FrameLayout gameLayout;
    private DrawingPanel drawingPanel;
    private Player playerOne;
    private Bitmap map; //just for testing purpose

    private boolean isTouched = false; //Screen is touched, player will move (DrawablePanel manages this)

    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        initComponents();
        initGameObjects();
        startGame();  // throwed a horrible excecption including a freeze, might have a big bug here

        /*If we want to hide actionbar: (but I think we could add pause button etc. in actionbar)
        if (getSupportActionBar() == null) {Log.i(TAG, "Actionbar already gone/hidden.");} else {getSupportActionBar().hide();}*/

        setDrawingPanel(new DrawingPanel(this));

        //layout referenzieren
        setGameLayout((FrameLayout) findViewById(R.id.gameViewLayout));
        //Adding the drawPanel the TouchListener
        drawingPanel.setOnTouchListener(drawingPanel);

        /*@TODO: deleted code*/

        getGameLayout().addView(getDrawingPanel());
    }
    public void startGame() {
        while (true) {
            //1. initialization has already been done (onCreate())
            System.out.println("Start");
            //2.Check for changes
            playerOne.update(isTouched, true);

            //3. Repaint (Old getDrawingPanel.repaint() was a huge recursive exception, my fault)
            getDrawingPanel().invalidate();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void initGameObjects(){
        setPlayerOne(new Player(this, 40, 100, 5, 5, R.drawable.player_testingpurpose,  "Gerhard Anus"));
    }

    // used to initialize other components, drawPanel has been null
    private void initComponents(){
        getBitMapFromPlayer();
        drawingPanel = new DrawingPanel(this);
    }




    /*
     * update, replacing ImageView with View, for more power (which we surely need)
     */
    //Drawing panel - draw Objects
    @SuppressLint("AppCompatCustomView")
    class DrawingPanel extends View implements View.OnTouchListener{

        public DrawingPanel(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint p = new Paint();

            //Maybe draw members like this:
            //getResources().getDrawable(getPlayerOne().getImg()).draw(canvas);

            canvas.drawBitmap(map, (int) playerOne.getPosX(), (int) playerOne.getPosY(), p);

        }

        // Replacing KeyHandler with inner class, for more possibilities
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "Screen Touched");
                return isTouched = true; //player will move
            } else
                return isTouched = false;
        }
    }

    //Only testing purpose, must be declared in respective class
    public void getBitMapFromPlayer(){
        map = BitmapFactory.decodeResource(getResources(), R.drawable.player_testingpurpose);
    }

    //GETTER/SETTER (Base class)
    public FrameLayout getGameLayout() {
        return gameLayout;
    }

    public void setGameLayout(FrameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }
}


