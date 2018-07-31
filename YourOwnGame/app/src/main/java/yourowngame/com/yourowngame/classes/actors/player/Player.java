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

    //Projectiles
    private List<Projectile> projectileList = new ArrayList<>();

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
        for (Projectile projectile : this.getProjectiles()) {
            projectile.cleanup();
        }
        return true;
    }

    @Override
    public void resetPos() {
        this.setPosY(Resources.getSystem().getDisplayMetrics().heightPixels / 4); //reset y when hitting ground
    }

    /***********************************************
     *             PROJECTILES AREA                *
     ***********************************************/

    @Enhance (message = "Is called in draw-method!! Avoid object allocations, additionally initialize might be called (evaluate if inside)!?")
    public void addProjectiles() {
        Projectile projectile = new Projectile_Iron(this.getActivity(), this.getPosX() + this.getWidthOfBitmap() / 2, this.getPosY() + this.getHeightOfBitmap() / 2, 10, 0);
        projectile.initialize();
        this.getProjectiles().add(projectile);
    }

    public void drawProjectiles() throws NoDrawableInArrayFound_Exception {
        for (Projectile e : this.projectileList) {
                e.draw();
        }
    }

    //Here we need to access the array backwards, otherwise we will remove an index, that will be progressed, but isn't there anymore!
    public void updateProjectiles() {
        Log.d(TAG, "updateProjectiles: Projectile Size = " + this.getProjectiles().size());
        if (!this.getProjectiles().isEmpty()) {
            for (int i = this.getProjectiles().size() - 1; i > -1; i--) {
                this.getProjectiles().get(i).update();

                if (this.getProjectiles().get(i).getPosX() > GameViewActivity.GAME_WIDTH - 50) {
                    Log.e(TAG, "updateProjectiles: Bullet removed!");
                    this.getProjectiles().remove(this.getProjectiles().get(i));
                }
            }
        }
    }

    /*************************
     *  GETTER & SETTER      *
     *************************/

    public List<Projectile> getProjectiles() {
        return projectileList;
    }

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

