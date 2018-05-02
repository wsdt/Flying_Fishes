package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

@Enhance(message = {"Maybe replace isCollected/isOutOfBound etc. with Zustandsmuster",
    "Bitmap/Drawable int array consistency!"})
public abstract class Fruit extends GameObject implements IHighscore_RewardableObj, IFruit {
    private boolean isCollected  = false;
    private boolean isOutOfBound = false;
    private int spawnTime = 0;

    public Fruit(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree,name);

        this.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
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
