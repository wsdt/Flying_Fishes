package yourowngame.com.yourowngame.gameEngine.surfaces;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Map;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.activities.WorldActivity;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.World;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * Similar to the GameView Obj.
 */
public class WorldView extends DrawableSurfaces {
    private static final String TAG = "WorldView";

    private World currWorld;
    private Bitmap initializedLevelRepresentant;

    public WorldView(Context context) {
        super(context);
    }

    public WorldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startWorldAnimations(@NonNull WorldActivity worldActivity, @NonNull World currWorld) {
        this.setDrawableSurfaceActivity(worldActivity);
        this.setCurrWorld(currWorld);

        this.initializeDrawableObjs();
        this.initializeDrawing();
    }

    @Override
    public void exitNow() {
        try {
            getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.getDrawableSurfaceActivity().finish();
    }

    @Override
    public void startExitProcedure() {
        //No warning dialogue
        exitNow();
    }

    /**
     * Here we have to manually initialize DrawableObjs, bc. here we have no levelObj which does that.
     */
    private void initializeDrawableObjs() {
        for (Background background : this.getCurrWorld().getAllBackgroundLayers()) {
            background.initialize();
        }

        //Initialize Level Representant
        this.setInitializedLevelRepresentant(BitmapFactory.decodeResource(this.getResources(), this.getCurrWorld().getLevelRepresentativeResId()));
        Log.d(TAG, "initializeDrawableObjs: Have initialized worldView.");
    }

    @Override
    public void drawAll(@NonNull Canvas canvas, long loopCount) {
        /* Update bglayers */
        try {
            for (Background background : this.getCurrWorld().getAllBackgroundLayers()) {
                background.setCanvas(canvas);
                background.draw();
            }

            /* Draw levels */
            //Log.d(TAG, "drawAll: Trying to draw levelRepresentants. Count: "+this.getCurrWorld().getAllLevels().size());
            Point positionOfLastLevel = null; // to draw line
            // Calculate how much we need to add so the lines are in the mid of the icons.
            float addToX = this.getInitializedLevelRepresentant().getWidth() / 2;
            float addToY = this.getInitializedLevelRepresentant().getHeight() / 2;
            for (Map.Entry<Point, Level> level : this.getCurrWorld().getAllLevels().entrySet()) {
                //Draw connection lines
                if (positionOfLastLevel != null) {
                    canvas.drawLine(addToX + positionOfLastLevel.x, addToY + positionOfLastLevel.y, addToX + level.getKey().x, addToY + level.getKey().y, new Paint(R.color.colorBlack));
                }

                canvas.drawBitmap(this.getInitializedLevelRepresentant(), level.getKey().x, level.getKey().y, null);
                positionOfLastLevel = level.getKey();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plain rectobj (outside to avoid obj allocations in draw()), for determining whether lvlObj
     * is clicked.
     */
    Rect r = new Rect();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                for (Map.Entry<Point, Level> level : this.getCurrWorld().getAllLevels().entrySet()) {
                    int lvlReprX = this.getInitializedLevelRepresentant().getWidth();
                    int lvlReprY = this.getInitializedLevelRepresentant().getHeight();
                    int lvlX = level.getKey().x;
                    int lvlY = level.getKey().y;

                    r.set(lvlX, lvlY, lvlX + lvlReprX, lvlY + lvlReprY);

                    if (r.contains(x, y)) {
                        Log.d(TAG, "onTouchEvent: Lvl clicked.");
                        //TODO: Change 0 for first index world repr with var
                        Intent startGameIntent = new Intent(this.getDrawableSurfaceActivity(),GameViewActivity.class);
                        startGameIntent.putExtra(World.INTENT_EXTRAID_POINT,
                                new int[]{0,lvlX,lvlY});
                        this.getDrawableSurfaceActivity().startActivity(startGameIntent);
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void updateAll() {
        /* Update bglayers */
        for (Background background : this.getCurrWorld().getAllBackgroundLayers()) {
            background.update();
        }
    }


    //GETTER/SETTER +++++++++++++++++++++++++++++++++++++++++++
    public World getCurrWorld() {
        return currWorld;
    }

    public void setCurrWorld(World currWorld) {
        this.currWorld = currWorld;
    }

    public Bitmap getInitializedLevelRepresentant() {
        return initializedLevelRepresentant;
    }

    public void setInitializedLevelRepresentant(Bitmap initializedLevelRepresentant) {
        this.initializedLevelRepresentant = initializedLevelRepresentant;
    }
}
