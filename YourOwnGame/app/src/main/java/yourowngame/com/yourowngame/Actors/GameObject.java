package yourowngame.com.yourowngame.Actors;

/**
 * Created by Solution on 14.02.2018.
 *
 *  Superclass for other GameObjects
 *
 *  Some obj's do not move, so speedX/Y is at standard 0.
 *
 */

public abstract class GameObject {

    protected double posX, posY, speedX, speedY;
    protected String name;
    //add, add, add

    public GameObject(double posX, double posY, double speedX, double speedY){
        this.posX = posX;
        this.posY = posY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.name = name;

        speedX = speedY = 0;

    }

    protected abstract void update();

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
