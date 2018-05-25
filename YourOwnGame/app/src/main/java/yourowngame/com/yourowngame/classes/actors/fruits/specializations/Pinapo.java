package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;


public class Pinapo extends Fruit implements IFruit.PINAPOS_FRUIT_PROPERTIES {
        private final String TAG = "Meloon";

        private static Bitmap[] images;

        public Pinapo(@NonNull Context context, double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
            super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
        }

        /**Creates random fruit*/
        public Pinapo(@NonNull Context context) {
            super(context); //also call super constr! (initializing)

            this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + (int) OFF_TIME));
            this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT));
            this.setSpeedX(SPEED_X);
            this.setSpeedY(SPEED_Y);
            this.setRotationDegree(DEFAULT_ROTATION);
            this.setName("Meloon");

            this.setCurrentBitmap(Avoci.getImages()[0]);
        }


        /*************************************** UPDATE / DRAW *************************************************/
        @Override
        public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
            this.setPosX(this.getPosX() - this.getSpeedX());
        }

        @Override
        public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {
            this.setCurrentBitmap(getImages()[((int) loopCount % this.getImg().length)]);
            canvas.drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
        }
        /*************************************** UPDATE / DRAW *************************************************/


        @Override
        public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
            this.setImg(IMAGE_FRAMES);

            try {
                if (!isInitialized) {
                    setImages(new Bitmap[this.getImg().length]);

                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                        getImages()[imgFrame] = this.getCraftedDynamicBitmap(imgFrame, (int) DEFAULT_ROTATION, null, null);
                    }
                    Log.d(TAG, "Meloon-Fruit: Successfully initialized!");
                    isInitialized = true;
                }
            } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
                Log.d(TAG, "Meloon-Fruit: Initialize Failure!");
                e.printStackTrace();
            }
            return isInitialized;
        }


        /** reset positions, return true*/
        @Override
        public boolean cleanup() {
            resetPos();
            return true;
        }

        /** Fruits need to differ in here */
        @Override
        public void resetPos() {
            this.setPosX(RandomMgr.getRandomFloat(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + OFF_TIME));
            this.setPosY(RandomMgr.getRandomFloat(IFruit.DEFAULT_FRUIT_PROPERTIES.Y_UPLIFT, GameViewActivity.GAME_HEIGHT - Y_UPLIFT));
        }

        /** Get reward method for highscore */
        @Override
        public int getReward() {
            return IFruit.MELOON_FRUIT_PROPERTIES.HIGHSCORE_REWARD;
        }

        /******************Getter & Setter ******************/
        public static Bitmap[] getImages() {
            return images;
        }
        public static void setImages(Bitmap[] images) {
            Pinapo.images = images;
        }
    }