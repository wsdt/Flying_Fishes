package yourowngame.com.yourowngame.classes.background;

/**
 * Created on 18.02.2018.
 *
 * Class for creating Backgrounds, only data!
 *
 * TODO exception handling
 */

public class Background {

    private int[] img;
    private String name;
    private int speedX = 0;
    private double x, y;
    private int activeDrawable = 0; /** image from the int array which is visible*/

    public Background(int[] img, String name){
        this.img = img;
    }

    //returns how many backgrounds are defined
    public int getLengthOfBackground(){
        return img.length;
    }

    public void setBackgroundSpeed(int speedX){
        this.speedX = speedX;
    }

    public double getX() {
        return x;
    }

    public void setX(double x){
        this.x=x;
    }

    public void setActiveDrawable(int pos){
        activeDrawable = pos;
    }

    public int getActiveDrawable(){
        return activeDrawable;
    }

    public double getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getDisplay(int pos){
        return img[pos];
    }


}
