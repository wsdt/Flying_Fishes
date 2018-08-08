package yourowngame.com.yourowngame.classes.actors.player;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;


public abstract class Player extends GameObject {
    private static final String TAG = "Player";
    /**
     * Use wrapperClasses to determine whether they are set or not.
     */
    private boolean goUp = false;
    private boolean goForward = false;

    /* Default Player constants ++++++++++++++++++++++++++++++++++*/
    /**
     * Jump Speed multiplied by MOVE_UP_MULTIPLIER
     */
    protected static final int DEFAULT_POS_X = 100;
    protected static final int DEFAULT_SPEED_X = 5; //should be overwritten by loadConfiguration() from Db
    protected static final int DEFAULT_SPEED_Y = 2; //should be overwritten by loadConfiguration() from Db

    /** Constructor blocked to prevent unconfigured players. Just use simplified constr. and set all
     * params after defaultConfig() when you want a custom player. */
    private Player(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**
     * Simplified constr. which loads it's params from DB (shop logic).
     */
    public Player(@NonNull Activity activity) {
        super(activity);
        this.loadConfiguration();
    }

    /** What has user bought, what can player do etc. (load from Db)
     *
     * As every Player can get modified and levelled up separately we make this abstract and
     * implement it in base classes. */
    protected abstract void loadConfiguration();

    @Override
    public void resetPos() {
        this.setPosY(DrawableSurfaces.getDrawHeight() / 4); //reset y when hitting ground
        this.setPosX(DEFAULT_POS_X);
    }

    @Override
    public void resetSpeed() {
        this.setSpeedY(DEFAULT_SPEED_Y);
        this.setSpeedX(DEFAULT_SPEED_X);
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

