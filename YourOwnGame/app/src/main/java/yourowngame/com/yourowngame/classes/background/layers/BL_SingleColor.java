package yourowngame.com.yourowngame.classes.background.layers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.classes.background.Background;

/** Don't delete, I think we could use this layer for Tutorials or e.g. a "Training"-mode or similar. */
public class BL_SingleColor extends Background {
    private static final String TAG = "BackgroundLayer_static";
    private int unparsedBgColor; //normal color resource int
    private int preParsedBgColor; //parsed color (is also an int) -> small performance enhancement

    /**
     * @param bgColor: By only allowing one integer getValue we ensure that at least one color has to be given and not more than one color is provided (because we would ignore it)
     */
    public BL_SingleColor(@NonNull Activity activity, int bgColor) {
        super(activity);
        this.setUnparsedBgColor(bgColor);
    }

    @Override
    public void update() {
    } //not necessary for this layer (lowest layer)

    @Override
    public void draw() {
        this.getCanvas().drawColor(this.getPreParsedBgColor());
    }


    /** No param to provide. */
    @Override
    public void initialize() {
        try {
            this.setPreParsedBgColor(this.getUnparsedBgColor()); //only here in initialize() because we parse the color (not really signifikant, but ok let's be consistent)
        } catch (NullPointerException e) {
            Log.e(TAG, "initialize: Could not initialize Static background!");
            e.printStackTrace();
        }
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
        this.preParsedBgColor = this.getActivity().getResources().getColor(unparsedColor);
    }
}
