package yourowngame.com.yourowngame.classes.gamelevels.interfaces;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

//TODO: Also use in future for backgrounds to be consistent! But remove as much method params as possible to use it generically!
public interface IDrawAble {
    /** @param loopCount: Loop count from GameLoopThread (given in redraw() method), with this we can create loop-dependent animations :)*/
    void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception;

}
