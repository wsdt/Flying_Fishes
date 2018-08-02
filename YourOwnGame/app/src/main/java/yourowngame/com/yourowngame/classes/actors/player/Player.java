package yourowngame.com.yourowngame.classes.actors.player;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.ProjectileMgr;
import yourowngame.com.yourowngame.classes.actors.projectiles.interfaces.IProjectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.specializations.Projectile_Iron;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;


public abstract class Player extends GameObject implements IPlayer.PROPERTIES.DEFAULT {
    private static final String TAG = "Player";
    /**
     * Use wrapperClasses to determine whether they are set or not.
     */
    private boolean goUp = false;
    private boolean goForward = false;

    /*-- Preloaded --*/
    private int intrinsicHeightOfPlayer;
    private Bitmap currentBitmap;


    public Player(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
        
    }

    /**
     * Block here creating simplified playerInstances, bc. we don't need random players.
     */
    private Player(@NonNull Activity activity) {
        super(activity);
    }


    /** Here as long as all players are cleaned up the same way. */
    @Override
    public boolean cleanup() {
        resetPos();
        return true;
    }

    @Override
    public void resetPos() {
        this.setPosY(Resources.getSystem().getDisplayMetrics().heightPixels / 4); //reset y when hitting ground
    }

    /*************************
     *  GETTER & SETTER      *
     *************************/

    public int getIntrinsicHeightOfPlayer() {
        return intrinsicHeightOfPlayer;
    }

    public void setIntrinsicHeightOfPlayer(int intrinsicHeightOfPlayer) {
        this.intrinsicHeightOfPlayer = intrinsicHeightOfPlayer;
    }

    public float getWidthOfPlayer() {
        return (float) this.getPosY() + (this.getIntrinsicHeightOfPlayer() * Constants.GameLogic.GameView.widthInPercentage);
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    /**
     * Here to determine whether obj should goUp/Down or Forward/or standing still [no backwards
     * movement allowed] on next drawCycle. Here and not in DrawableObj bc. Background cannot move
     * as a whole. Additionally only in Player class, bc. enemies and fruits need no commands to move.
     */
    public boolean isGoUp() {
        return goUp;
    }

    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    public boolean isGoForward() {
        return goForward;
    }

    public void setGoForward(boolean goForward) {
        this.goForward = goForward;
    }
}

