package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.configuration.Constants;


public class Player extends GameObject {
    private static final String TAG = "Player";


    public Player(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, int img, @Nullable String name) {
        super(activity, posX, posY, speedX, speedY, img, name);

        //yep! denkfehler, clear!
        /*Standard-Speed --> Why eig.? Immerhin übergeben wir SpeedX/SpeedY, dort können wir über unten angegebene Konstanten auch Standard Speed festlegen
        this.setSpeedX(Constants.Actors.Player.defaultSpeedX);
        this.setSpeedY(Constants.Actors.Player.defaultSpeedY);*/

    }

    @Override
    public void update(@Nullable Boolean goUp, @Nullable Boolean goForward) {
        super.update(goUp, goForward);
    }

}

