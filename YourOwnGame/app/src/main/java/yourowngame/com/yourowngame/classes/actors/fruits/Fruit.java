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

    public abstract void resetPositions();


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
