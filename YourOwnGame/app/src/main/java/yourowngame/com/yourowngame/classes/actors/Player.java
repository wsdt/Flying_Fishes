package yourowngame.com.yourowngame.classes.actors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;


public class Player extends GameObject {
    private static final String TAG = "Player";


    public Player(double posX, double posY, double speedX, double speedY, int img[],float rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img,rotationDegree, name);

        /*Standard-Speed --> Why eig.? Immerhin übergeben wir SpeedX/SpeedY, dort können wir über unten angegebene Konstanten auch Standard Speed festlegen
        this.setBackgroundSpeed(Constants.Actors.Player.defaultSpeedX);
        this.setSpeedY(Constants.Actors.Player.defaultSpeedY);*/

    }

    /**
     * @param obj currently not used!
     * @param goUp check if go up
     * @param goForward check if go forward
     */
    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable  Boolean goForward) {

        if (goForward == null && goUp == null) {
            Log.i(TAG, "update: Called update-method without a valid Boolean param!");
        } else {
            // Update Y
            // replaced x/y game starts at 0|0 which is the top left corner of the view
            // if player "jumps" the y value decreases (cause y grows towards the bottom of the view)
            if (goUp != null) {
                if (goUp) {
                    this.setPosY(this.getPosY() - this.getSpeedY() * Constants.Actors.GameObject.MOVE_UP_MULTIPLIER);
                    this.setRotationDegree(Constants.Actors.Player.rotationFlyingUp);
                } else {
                    this.setPosY(this.getPosY() + this.getSpeedY());
                    this.setRotationDegree(Constants.Actors.Player.rotationFlyingDown);
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

    @Override
    public boolean collision(View view, GameObject obj) {
        /** The only problem we've got here is, that we need to cut the helicopter images to the best!
         the current PNG has a margin from about 10-20 pixel, which leads to a collision earlier!
         So we'll need to cut all helicopte images to the maximum! then this method will work just fine*/
        //get the current player and View
        Player playerOne = (Player) obj;
        GameView currentView = (GameView) view;

        //Gets the original height of the players image
        Drawable bd = (Drawable) currentView.getResources().getDrawable(playerOne.getImg()[0]);
        int height = bd.getIntrinsicHeight();   //gets orginal height

        //Gets the scaled-size of the current player image
        float playerPosYWithImage = (float) playerOne.getPosY() + (height * Constants.GameLogic.GameView.widthInPercentage);
        float playerPosYWithoutImage = (float) playerOne.getPosY();

        //compares, if player hits ground or top
        if(playerPosYWithImage > currentView.getLayout().getHeight() || playerPosYWithoutImage < 0)
            return true;
        else
            return false;
    }
}

