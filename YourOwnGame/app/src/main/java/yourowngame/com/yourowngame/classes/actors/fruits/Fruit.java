package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

@Enhance(message = {"Maybe replace isCollected/isOutOfBound etc. with Zustandsmuster",
    "Bitmap/Drawable int array consistency!"})

public abstract class Fruit extends GameObject implements IHighscore_RewardableObj, IFruit.DEFAULT_FRUIT_PROPERTIES {
    private boolean isCollected  = false;
    private boolean isOutOfBound = false;
    private int spawnTime = 0;

    public Fruit(@NonNull Context context, double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    /**Creates random fruit*/
    public Fruit(@NonNull Context context) {
        super(context);
    }


    @Override
    public abstract void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward);

    @Override
    public abstract void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount);

    @Override
    public abstract boolean cleanup();

    //FRUIT specific methods
    public abstract void isGone();
    public abstract void collected();


    //GETTER/SETTERS
    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public boolean isOutOfBound() {
        return isOutOfBound;
    }

    public void setOutOfBound(boolean outOfBound) {
        isOutOfBound = outOfBound;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }
}
