package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Reward extends GameObject {

    public Reward(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, int img, @Nullable String name) {
        super(activity, posX, posY, speedX, speedY, img, name);

    }

}