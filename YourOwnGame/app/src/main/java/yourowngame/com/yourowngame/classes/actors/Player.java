package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class Player extends GameObject {


    public Player(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, Image img, @Nullable String name) {
        super(activity, posX, posY, speedX, speedY, img,  name);

        //Standard-Speed
        this.speedX = this.speedY = 5;
    }

    @Override
    public void update(boolean UP, boolean DOWN){
        if(UP) posY =- speedY;
        if(DOWN) posY += speedY;
    }
}
