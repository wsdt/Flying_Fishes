package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;


public abstract class Fruit extends GameObject implements IHighscore_RewardableObj, IFruit {

    public boolean isCollected  = false;
    public boolean isOutOfBound = false;
    public int spawnTime = 0;

    public Fruit(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree,name);

        this.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
    }

    public Fruit(){}

    @Override
    public abstract void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward);

    @Override
    public abstract void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount);

    @Override
    public abstract boolean cleanup();

    public abstract void isGone();

    public abstract void collected();

}
