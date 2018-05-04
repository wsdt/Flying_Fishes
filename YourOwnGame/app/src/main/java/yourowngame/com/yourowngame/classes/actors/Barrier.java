package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Barrier extends GameObject {
    public Barrier(@NonNull Context context, double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }


    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {}


    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        return false;
    }

    @Override
    public boolean cleanup() {
        return true;
    }
}
