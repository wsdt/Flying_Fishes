package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class Reward extends GameObject {

    public Reward(double posX, double posY, double speedX, double speedY, int[] img,float rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree,name);
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }


    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {

    }

}
