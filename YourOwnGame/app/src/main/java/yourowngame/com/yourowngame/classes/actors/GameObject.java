package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 *  Superclass for other GameObjects
 *
 *  Some obj's do not move, so speedX/Y is at standard 0.
 *
 */

public abstract class GameObject extends View {

    protected double posX, posY, speedX, speedY;
    protected String name;
    //add, add, add

    public GameObject(Activity activity) {
        super(activity);
    }

    public GameObject(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, @Nullable String name){
        super(activity);
        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
        this.setName(name);

        speedX = speedY = 0; //all sprites should be at 0 when getting created
    }

    //Moves the positions, when true

    public abstract void update(boolean UP, boolean DOWN);

    public double getPosX() { return posX; }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
