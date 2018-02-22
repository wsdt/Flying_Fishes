package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class Barrier extends GameObject {
    public Barrier(double posX, double posY, double speedX, double speedY, int[] img, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, name);
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }

    @Override
    public  boolean collision(View view, GameObject obj) {
        return false;
    }


}
