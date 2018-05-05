package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.annotations.Enhance;

@Enhance(message = "do not change this, until the meloon-class works perfectly")
public class Avoci extends Fruit implements IFruit.AVOCI_FRUIT_PROPERTIES {
    private Bitmap[] images;

    public Avoci(@NonNull Context context, double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    /**Create random fruit*/
    public Avoci(@NonNull Context context) {
        super(context); //also call super constr! (initializing)
        //TODO: see Meloon
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        this.setPosX(this.getPosX() - this.getSpeedX()); //just move them from right to left
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {
        canvas.drawBitmap(images[0], (int) this.getPosX(), (int) this.getPosY(), null);
    }

    @Override
    public <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        return false;
    }

    @Override
    public boolean cleanup() {
        return true;
    }

    @Override
    public void resetPositions() {

    }


    /** Get reward method for highscore */
    @Override
    public int getReward() {
        return IFruit.AVOCI_FRUIT_PROPERTIES.HIGHSCORE_REWARD;
    }
}
