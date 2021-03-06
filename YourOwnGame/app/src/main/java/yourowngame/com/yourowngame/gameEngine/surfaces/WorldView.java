package yourowngame.com.yourowngame.gameEngine.surfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Map;
import java.util.Set;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.activities.WorldActivity;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.manager.WorldMgr;
import yourowngame.com.yourowngame.classes.manager.dialog.LevelInformationDialog;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * Similar to the GameView Obj.
 */
public class WorldView extends DrawableSurfaces {
    private static final String TAG = "WorldView";
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

    public void startWorldAnimations(@NonNull WorldActivity worldActivity) {
        this.setDrawableSurfaceActivity(worldActivity);
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
            for (Background background : WorldMgr.getCurrWorld(this.getDrawableSurfaceActivity(),false).getAllBackgroundLayers()) {
                background.initialize();

                //Initialize Level Representant
                this.setInitializedLevelRepresentant(BitmapFactory.decodeResource(
                        this.getResources(), WorldMgr.getCurrWorld(this.getDrawableSurfaceActivity(),false).getLevelRepresentativeResId()));
                Log.d(TAG, "initializeDrawableObjs: Have initialized worldView.");
            }
    }

    @Override
    public void drawAll(@NonNull Canvas canvas, long loopCount) {
        /* Update bglayers */
        try {
            for (Background background : WorldMgr.getCurrWorld(this.getDrawableSurfaceActivity(),false).getAllBackgroundLayers()) {
                background.setCanvas(canvas);
                background.draw();
            }

            /* Draw levels */
            //Log.d(TAG, "drawAll: Trying to draw levelRepresentants. Count: "+this.getCurrWorld().getAllLevels().size());
            Point positionOfLastLevel = null; // to draw line

            //Create a Paint-Object for further details
            Paint paint = new Paint();
            paint.setStrokeWidth(5);

            // Calculate how much we need to add so the lines are in the mid of the icons.
            float addToX = this.getInitializedLevelRepresentant().getWidth() / 2;
            float addToY = this.getInitializedLevelRepresentant().getHeight() / 2;
            for (Point position : WorldMgr.getCurrWorld(this.getDrawableSurfaceActivity(),false).getAllLevels().values()) {
                //Draw connection lines
                if (positionOfLastLevel != null) {
                    canvas.drawLine(addToX + positionOfLastLevel.x,
                            addToY + positionOfLastLevel.y,
                            addToX + position.x,
                            addToY + position.y, paint);
                }

                canvas.drawBitmap(this.getInitializedLevelRepresentant(), position.x, position.y, null);
                positionOfLastLevel = position;
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
                int lvlIndex = 0;
                for (Point position : WorldMgr.getCurrWorld(this.getDrawableSurfaceActivity(),false).getAllLevels().values()) {
                    int lvlReprX = this.getInitializedLevelRepresentant().getWidth();
                    int lvlReprY = this.getInitializedLevelRepresentant().getHeight();
                    int lvlX = position.x;
                    int lvlY = position.y;

                    //In which area a lvl gets opened/activated?
                    r.set(lvlX, lvlY, lvlX + lvlReprX, lvlY + lvlReprY);

                    //Is clickedPosition inside a rect of a lvlRepresentant?
                    if (r.contains((int) event.getX(), (int) event.getY())) {
                        Log.d(TAG, "onTouchEvent: Lvl clicked.");
                        // Pause/Stop thread before opening new activity --> is done in LevelInformationDialog
                        LevelInformationDialog.show(this, lvlIndex);

                        return true;
                    }
                    lvlIndex++;
                }
                break;
        }
        return false;
    }

    @Override
    public void updateAll() {
        /* Update bglayers */
        for (Background background : WorldMgr.getCurrWorld(this.getDrawableSurfaceActivity(),false).getAllBackgroundLayers()) {
            background.update();
        }
    }


    //GETTER/SETTER +++++++++++++++++++++++++++++++++++++++++++
    public Bitmap getInitializedLevelRepresentant() {
        return initializedLevelRepresentant;
    }

    public void setInitializedLevelRepresentant(Bitmap initializedLevelRepresentant) {
        this.initializedLevelRepresentant = initializedLevelRepresentant;
    }
}
