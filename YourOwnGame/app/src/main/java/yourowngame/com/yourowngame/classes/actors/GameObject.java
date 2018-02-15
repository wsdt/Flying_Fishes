package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 *  Superclass for other GameObjects
 *
 *  Some obj's do not move, so speedX/Y is at standard 0.
 *
 */

public abstract class GameObject /*extends Mapper*/ {

    protected double posX, posY, speedX, speedY;
    protected String name;
    //add, add, add


    public GameObject(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, @Nullable String name){
        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
        this.setName(name);

        //no default declaration for speed necessary, because no constructor for GameObject without speedX/Y available
    }


    protected abstract void update(boolean UP, boolean DOWN);

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
