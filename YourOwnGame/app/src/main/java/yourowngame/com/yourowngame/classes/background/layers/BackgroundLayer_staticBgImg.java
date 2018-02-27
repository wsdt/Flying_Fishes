package yourowngame.com.yourowngame.classes.background.layers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;


public class BackgroundLayer_staticBgImg extends Background {
    private static final String TAG = "BackgroundLayer_static";
    private int preParsedBgColor; //small performance enhancement

    /** @param bgColor: By only allowing one integer value we ensure that at least one color has to be given and not more than one color is provided (because we would ignore it)*/
    public BackgroundLayer_staticBgImg(@NonNull BackgroundManager backgroundManager, int bgColor, String name, float backgroundSpeed) {
        super(backgroundManager, new int[] {bgColor}, name, backgroundSpeed);

        //After all, preparse color resource, so we do not have to do it in drawBg()
        this.setPreParsedBgColor(bgColor); //now at least one
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
}
