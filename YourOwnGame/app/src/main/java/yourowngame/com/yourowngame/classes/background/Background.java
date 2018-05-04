package yourowngame.com.yourowngame.classes.background;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Random;

import yourowngame.com.yourowngame.classes.background.interfaces.IBackground;
import yourowngame.com.yourowngame.gameEngine.GameView;
import yourowngame.com.yourowngame.gameEngine.interfaces.Initializer;

/**
 * Created on 18.02.2018.
 *
 * Class for creating Backgrounds, only data!
 *
 */

public abstract class Background implements Initializer, IBackground {
    private static final String TAG = "Background";

    private Context context;
    private int[] img;
    private String name;
    private float backgroundSpeedX = 0;

    public Background(@NonNull Context context, int[] img, String name, float backgroundSpeed) {
        Log.d(TAG, "getBackgroundInstance: Created new instance.");
        this.setContext(context);
        this.setImg(img);
        this.setName(name);
        this.setBackgroundSpeed(backgroundSpeed);
    }


    /** abstract method for updating the Background (Layers should provide a method to update themselves! */
    public abstract void updateBackground();

    /** abstract method for drawing the Background (Layers should provide a method to draw themselves!*/
    public abstract void drawBackground(Canvas canvas);

    public void setBackgroundSpeed(float backgroundSpeedX){
        this.backgroundSpeedX = backgroundSpeedX;
    }

    public float getBackgroundSpeedX() {
        return backgroundSpeedX;
    }

    public int[] getImg() {
        return img;
    }

    public void setImg(int[] img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
