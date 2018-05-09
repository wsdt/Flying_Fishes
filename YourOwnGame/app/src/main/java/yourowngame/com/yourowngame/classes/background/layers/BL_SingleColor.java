package yourowngame.com.yourowngame.classes.background.layers;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.classes.background.Background;


public class BL_SingleColor extends Background {
    private static final String TAG = "BackgroundLayer_static";
    private int unparsedBgColor; //normal color resource int
    private int preParsedBgColor; //parsed color (is also an int) -> small performance enhancement

    /**
     * @param bgColor: By only allowing one integer getValue we ensure that at least one color has to be given and not more than one color is provided (because we would ignore it)
     */
    public BL_SingleColor(@NonNull Context context, int bgColor, String name) {
        super(context, name);
        this.setUnparsedBgColor(bgColor);

        //After all, preparse color resource, so we do not have to do it in drawBg()
        this.initialize(); //now at least one
    }

    @Override
    public void updateBackground() {
    } //not necessary for this layer (lowest layer)

    @Override
    public void drawBackground(Canvas canvas) {
        canvas.drawColor(this.getPreParsedBgColor());
    }


    /** No param to provide. */
    @Override
    @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            this.setPreParsedBgColor(this.getUnparsedBgColor()); //only here in initialize() because we parse the color (not really signifikant, but ok let's be consistent)
        } catch (NullPointerException e) {
            Log.e(TAG, "initialize: Could not initialize Static background!");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean cleanup() {
        //its more efficient to do here nothing for this layer
        return true;
    }

    //GETTER/SETTER -------------------------------------------------------------
    public int getUnparsedBgColor() {
        return unparsedBgColor;
    }

    public void setUnparsedBgColor(int unparsedBgColor) {
        this.unparsedBgColor = unparsedBgColor;
    }

    public int getPreParsedBgColor() {
        return this.preParsedBgColor;
    }

    /**
     * @param unparsedColor: Normal res-drawable integer (get's implicitly casted to color integer
     */
    public void setPreParsedBgColor(int unparsedColor) {
        this.preParsedBgColor = this.getContext().getResources().getColor(unparsedColor);
    }
}
