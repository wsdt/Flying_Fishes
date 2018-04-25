package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;

public class Avocis extends Fruit {

    public Avocis(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {

    }

    @Override
    public boolean cleanup() {
        return false;
    }
}
