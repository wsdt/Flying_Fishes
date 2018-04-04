package yourowngame.com.yourowngame.classes.background.layers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;


public class BackgroundLayer_staticBgImg extends Background {
    private static final String TAG = "BackgroundLayer_static";
    private int preParsedBgColor; //small performance enhancement

    /** @param bgColor: By only allowing one integer getValue we ensure that at least one color has to be given and not more than one color is provided (because we would ignore it)*/
    public BackgroundLayer_staticBgImg(@NonNull BackgroundManager backgroundManager, int bgColor, String name, float backgroundSpeed) {
        super(backgroundManager, new int[] {bgColor}, name, backgroundSpeed);

        //After all, preparse color resource, so we do not have to do it in drawBg()
        initialize(bgColor); //now at least one
    }

    @Override
    public void updateBackground() {} //not necessary for this layer (lowest layer)

    @Override
    public void drawBackground(Canvas canvas) {
        canvas.drawColor(this.getPreParsedBgColor());
    }

    public int getPreParsedBgColor() {
        return this.preParsedBgColor;
    }

    /** @param unparsedColor: Normal res-drawable integer (get's implicitly casted to color integer*/
    public void setPreParsedBgColor(int unparsedColor) {
        this.preParsedBgColor = this.getGameView().getResources().getColor(unparsedColor);
    }


    /** allObjs[0] -> resColor:int */
    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            if (allObjs != null) {
                this.setPreParsedBgColor((Integer) allObjs[0]); //only here in initialize() because we parse the color (not really signifikant, but ok let's be consistent)
            } else {
                return false;
            }
        } catch (ClassCastException | NullPointerException e) {
            Log.e(TAG, "initialize: Could not initialize Static background!");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean cleanup() {
        return true;
    }
}
