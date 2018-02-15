package yourowngame.com.yourowngame.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 *
 */


public class GameViewActivity extends AppCompatActivity {
    private RelativeLayout gameLayout;
    private DrawingPanel drawingPanel;
    Player playerOne = new Player(this, 0, 0, 0, 0, "Revin Kiedl");

    //(1.) Initialize objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        drawingPanel = new DrawingPanel(this);

        //layout referenzieren
        gameLayout = (RelativeLayout) findViewById(R.id.gameViewLayout);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 750);
        gameLayout.setLayoutParams(params);
        gameLayout.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));

        gameLayout.addView(drawingPanel);
    }

    public void startGame() {
        while (true) {

            //1. initialization has already been done (onCreate())
            //2. check whether positions have changed
            //3. repaint screen

            //2.
            //playerOne.update();

            //3.
            drawingPanel.repaint();

        }
    }

    //Drawing panel - draw Objects
    @SuppressLint("AppCompatCustomView")
    class DrawingPanel extends ImageView{

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

        }

        public void repaint(){
            this.repaint();
        }
    }
}


