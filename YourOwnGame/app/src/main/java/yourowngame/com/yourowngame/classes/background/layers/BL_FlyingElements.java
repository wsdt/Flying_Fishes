package yourowngame.com.yourowngame.classes.background.layers;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.interfaces.IBL_FlyingElements;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/** BL_FlyingElements is used for showing drawables, which are spawning at a specific height randomly.
 * This can be used e.g for clouds. But we could be creative here (showing different clouds, etc.)
 **/
public class BL_FlyingElements extends Background implements IBL_FlyingElements {
    private int[] resDrawables;
    private int amountOfFlyingElements;
    private ArrayList<FlyingElement> craftedFlyingELements = new ArrayList<>();
    private static final String TAG = "BL_FlyingElements";

    /** @param resDrawables: Provide int-array with all possible drawable resource ints.
     * @param amountOfFlyingElements: How many of those drawables should be drawn on display?*/
    public BL_FlyingElements(@NonNull Activity activity, int[] resDrawables, int amountOfFlyingElements) {
        super(activity);
        this.setResDrawables(resDrawables);
        this.setAmountOfFlyingElements(amountOfFlyingElements);
    }

    /**
     * inner class (the movable-Object on the screen)
     * E.g. for showing a cloud
     */
    public class FlyingElement {
        private Bitmap flyingElementBitmap;
        protected float posX;
        protected float posY;
        private float randomSpeed;

        private FlyingElement(Bitmap flyingElementBitmap) {
            this.posX = RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 1500);
            this.posY = RandomMgr.getRandomFloat(0, (int) (GameViewActivity.GAME_HEIGHT * FLYINGELEMENT_RANDOM_Y_PLACEMENT_IN_PERCENTAGE));
            Log.d(TAG, "Width and Height of GVA " +GameViewActivity.GAME_HEIGHT + " " + GameViewActivity.GAME_WIDTH);

            this.flyingElementBitmap = flyingElementBitmap;
            this.randomSpeed = RandomMgr.getRandomFloat(FLYINGELEMENT_RANDOM_SPEED_MIN, FLYINGELEMENT_RANDOM_SPEED_MAX);
        }

        //this methods updates the flying element (e.g. cloud)
        private void updateFlyingElement(float speed) {
            this.posX -= (speed);
            if (this.posX < -100) //-100 is on every screen outside of visible area
                posX = GameViewActivity.GAME_WIDTH + 100;
            Log.d(TAG, "Position of Cloud = " + this.posY);
        }
    }

    /**
     * calls the update() method from the Cloud.class
     * <p>
     * i removed some thing because it seems we're not using the methods for the right way
     * XXX -> update()         should only move the cloud
     * XXX -> drawBackground() should only draw it
     */
    @Override
    public void update() {
        for (FlyingElement flyingElement : this.getCraftedFlyingElements()) {
            flyingElement.updateFlyingElement(flyingElement.randomSpeed); //Where is the speed defined? couldnt find it!
        }
    }

    /**
     * draws the clouds
     */
    @Override
    public void draw() {
        for (FlyingElement flyingElement : this.getCraftedFlyingElements()) {
            this.getCanvas().drawBitmap(flyingElement.flyingElementBitmap, flyingElement.posX, flyingElement.posY, null);
        }
    }

    /**
     * Crafts FlyingElements (e.g. clouds)
     */
    @Bug(byDeveloper = "SOLUTION",
    message = "Metrics of Display are vanished!",
    possibleSolution = "Game Metrics are not working anymore, they need to be set at every level!")
    private void craftClouds() {
        Log.d(TAG, "craftClouds: Trying to craft clouds.");
        for (int i = 0; i < this.getAmountOfFlyingElements(); i++) {

            this.getCraftedFlyingElements().add(new FlyingElement(BitmapFactory.decodeResource(this.getActivity().getResources(), getResDrawables()[RandomMgr.getRandomInt(0, getResDrawables().length - 1)])));
            Log.d(TAG, "GameView Height = ");
            Log.d(TAG, "craftClouds: Added cloud no. "+i);
        }
    }

    /** allObjs == NULL/no param to provide */
    @Override
    public void initialize() {
        //TODO: Maybe make amount clouds configurable by constr or similar?
        this.craftClouds(); //also sets simultaneously
    }

    @Override
    public boolean cleanup() {
        return true;
    }


    //GETTER/SETTER ---------------------------------------------------------------------
    public int[] getResDrawables() {
        return resDrawables;
    }

    public void setResDrawables(int[] resDrawables) {
        this.resDrawables = resDrawables;
    }

    public int getAmountOfFlyingElements() {
        return amountOfFlyingElements;
    }

    public void setAmountOfFlyingElements(int amountOfFlyingElements) {
        this.amountOfFlyingElements = amountOfFlyingElements;
    }

    /**
     * Returns the craftedFlyingELements
     */
    public ArrayList<FlyingElement> getCraftedFlyingElements() {
        return craftedFlyingELements;
    }
}