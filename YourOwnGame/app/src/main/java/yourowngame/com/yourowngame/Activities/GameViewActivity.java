package yourowngame.com.yourowngame.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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


public class GameViewActivity extends AppCompatActivity {
    private static final String TAG = "GameViewActivity";
    private FrameLayout gameLayout;
    private DrawingPanel drawingPanel;
    private Player playerOne;

    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        /*If we want to hide actionbar: (but I think we could add pause button etc. in actionbar)
        if (getSupportActionBar() == null) {Log.i(TAG, "Actionbar already gone/hidden.");} else {getSupportActionBar().hide();}*/

        setDrawingPanel(new DrawingPanel(this));

        //layout referenzieren
        setGameLayout((FrameLayout) findViewById(R.id.gameViewLayout));
        /* Did following in XML (faster):
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 750);
        getGameLayout().setLayoutParams(params);
        getGameLayout().setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));*/

        initGameObjects();
        getGameLayout().addView(getDrawingPanel());
    }

    public void startGame() {
        while (true) {

            //1. initialization has already been done (onCreate())
            //2. check whether positions have changed
            //3. repaint screen

            //2.
            //playerOne.update();

            //3.
            getDrawingPanel().repaint();

        }
    }

    private void initGameObjects(){
        setPlayerOne(new Player(this, 0, 0, 5, 5, R.drawable.player_testingpurpose,  "Gerhard Anus"));
    }


    //Drawing panel - draw Objects
    @SuppressLint("AppCompatCustomView")
    class DrawingPanel extends ImageView {

        public DrawingPanel(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint p = new Paint();
            p.setColor(Color.WHITE);
            p.setStrokeWidth(5);

            canvas.drawLine(0, 0, getWidth(), getHeight(), p);
            p.setTextSize(75);
            canvas.drawText("Create GameObjects here", 0, getHeight()-5, p);

            //Maybe draw members like this:
            //getResources().getDrawable(getPlayerOne().getImg()).draw(canvas);

        }

        public void repaint(){
            this.repaint();
        }
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


