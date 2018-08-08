package yourowngame.com.yourowngame.classes.background.layers;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntRange;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * BL_FlyingElements is used for showing drawables, which are spawning at a specific height randomly.
 * This can be used e.g for clouds. But we could be creative here (showing different clouds, etc.)
 **/
public class BL_FlyingElements extends Background {
    private static final String TAG = "BL_FlyingElements";
    private int[] resDrawables;
    private int amountOfFlyingElements;
    private ArrayList<FlyingElement> craftedFlyingELements = new ArrayList<>();

    /**
     * Flying Elements constants +++++++++++++++++++++++++++
     */
    private static final float FLYINGELEMENT_RANDOM_Y_PLACEMENT_IN_PERCENTAGE = 0.50f; //top 40% where e.g. clouds can appear
    private static final float FLYINGELEMENT_RANDOM_SPEED_MAX = 5f;
    private static final float FLYINGELEMENT_RANDOM_SPEED_MIN = 1f; //do not place 0!

    /**
     * @param resDrawables:           Provide int-array with all possible drawable resource ints.
     * @param amountOfFlyingElements: How many of those drawables should be drawn on display?
     */
    public BL_FlyingElements(@NonNull Activity activity, @IntegerRes int[] resDrawables, @IntRange(from = 1, to = 250) int amountOfFlyingElements) {
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
            this.posX = getRandomPosX();
            this.posY = getRandomPosY();
            Log.d(TAG, "Width and Height of GVA " + DrawableSurfaces.getDrawHeight() + " " + DrawableSurfaces.getDrawWidth());

            this.flyingElementBitmap = flyingElementBitmap;
            this.randomSpeed = RandomMgr.getRandomFloat(FLYINGELEMENT_RANDOM_SPEED_MIN, FLYINGELEMENT_RANDOM_SPEED_MAX);
        }

        //this methods updates the flying element (e.g. cloud)
        private void updateFlyingElement(float speed) {
            this.posX -= (speed);
            if (this.posX < -100) { //-100 is on every screen outside of visible area
                //When outside of screen craft new positions
                posX = getRandomPosX();
                posY = getRandomPosY();
            }
        }

        private float getRandomPosX() {
            return RandomMgr.getRandomFloat(DrawableSurfaces.getDrawWidth(), DrawableSurfaces.getDrawWidth() + 1500);
        }

        private float getRandomPosY() {
            return RandomMgr.getRandomFloat(0, (int) (DrawableSurfaces.getDrawHeight() * FLYINGELEMENT_RANDOM_Y_PLACEMENT_IN_PERCENTAGE));
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
            flyingElement.updateFlyingElement(flyingElement.randomSpeed);
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
    private void craftClouds() {
        Log.d(TAG, "craftClouds: Trying to craft clouds.");
        for (int i = 0; i < this.getAmountOfFlyingElements(); i++) {

            this.getCraftedFlyingElements().add(new FlyingElement(BitmapFactory.decodeResource(this.getActivity().getResources(), getResDrawables()[RandomMgr.getRandomInt(0, getResDrawables().length - 1)])));
            Log.d(TAG, "GameView Height = ");
            Log.d(TAG, "craftClouds: Added cloud no. " + i);
        }
    }

    /**
     * allObjs == NULL/no param to provide
     */
    @Override
    public void initialize() {
        if (!isInitialized()) {
            this.craftClouds(); //also sets simultaneously
            this.setInitialized(true);
        } else {
            Log.w(TAG, "initialize: Already initialized.");
        }
    }

    @Override
    public boolean cleanup() {
        return true;
    }


    //GETTER/SETTER ---------------------------------------------------------------------
    public int[] getResDrawables() {
        return resDrawables;
    }

    public void setResDrawables(@IntegerRes int[] resDrawables) {
        this.resDrawables = resDrawables;
    }

    public int getAmountOfFlyingElements() {
        return amountOfFlyingElements;
    }

    public void setAmountOfFlyingElements(@IntRange(from = 1, to = 250) int amountOfFlyingElements) {
        this.amountOfFlyingElements = amountOfFlyingElements;
    }

    /**
     * Returns the craftedFlyingELements
     */
    public ArrayList<FlyingElement> getCraftedFlyingElements() {
        return craftedFlyingELements;
    }
}