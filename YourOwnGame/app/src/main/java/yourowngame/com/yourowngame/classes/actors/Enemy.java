package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class Enemy extends GameObject {

    public Enemy(@NonNull Activity context, double posX, double posY, double speedX, double speedY, Image img, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, name);
    }

    @Override
    public void update(boolean UP, boolean DOWN){
        if(UP) posY =- speedY;
        if(DOWN) posY += speedY;
    }
}
