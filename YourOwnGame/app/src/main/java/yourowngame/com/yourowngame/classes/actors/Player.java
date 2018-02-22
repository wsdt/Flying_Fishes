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
import yourowngame.com.yourowngame.gameEngine.GameView;


public class Player extends GameObject {
    private static final String TAG = "Player";


    public Player(double posX, double posY, double speedX, double speedY, int img[], @Nullable String name) {
        super(posX, posY, speedX, speedY, img, name);

        /*Standard-Speed --> Why eig.? Immerhin übergeben wir SpeedX/SpeedY, dort können wir über unten angegebene Konstanten auch Standard Speed festlegen
        this.setBackgroundSpeed(Constants.Actors.Player.defaultSpeedX);
        this.setSpeedY(Constants.Actors.Player.defaultSpeedY);*/

    }

    @Override
    public void update(@Nullable Boolean goUp, @Nullable Boolean goForward) {
        super.update(goUp, goForward);

        //do not add code here, which is specialised for any subclass, every subclass needs to handle that itself
        if (goForward == null && goUp == null) {
            Log.i(TAG, "update: Called update-method without a valid Boolean param!");
        } else {
            // Update Y
            // replaced x/y game starts at 0|0 which is the top left corner of the view
            // if player "jumps" the y value decreases (cause y grows towards the bottom of the view)
            if (goUp != null) {
                if (goUp) {
                    this.setPosY(this.getPosY() - this.getSpeedY() * Constants.Actors.GameObject.MOVE_UP_MULTIPLIER);
                } else {
                    this.setPosY(this.getPosY() + this.getSpeedY());
                } //if false go down
            } else {
                Log.i(TAG, "updateY: Ignoring goUp. Because parameter null.");
            }

            //Update X
            if (goForward != null) {
                if (goForward) {
                    this.setPosX(this.getPosX() + this.getSpeedX()); //if true go forward
                } else {
                    // should not go back, only if bonus of getting forward is no longer active
                    //  this.setPosX(this.getPosX() - this.getSpeedX());
                } //if false go back
            } else {
                Log.i(TAG, "updateX: Ignoring goForward. Because parameter null.");
            }
        }


    }


}

