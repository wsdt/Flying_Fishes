package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Reward extends GameObject {

    public Reward(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, @Nullable String name) {
        super(activity, posX, posY, speedX, speedY, name);

    }

    @Override
    public void update(boolean UP, boolean DOWN){
        if(UP) posY =- speedY;
        if(DOWN) posY += speedY;
    }
}
