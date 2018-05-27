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
    private int spawnTime = 0;

    public Fruit(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, int[] img) {
        super(activity, posX, posY, speedX, speedY, img);
    }

    /**Creates random fruit*/
    public Fruit(@NonNull Activity activity) {
        super(activity);
    }

    public boolean hasLeftScreen(){
        return this.getPosX() < 0;
    }

    //GETTER/SETTERS
    public int getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }
}
