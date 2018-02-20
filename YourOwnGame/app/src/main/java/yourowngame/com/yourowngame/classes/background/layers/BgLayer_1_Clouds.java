package yourowngame.com.yourowngame.classes.background.layers;


import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.gameEngine.GameView;

public class BgLayer_1_Clouds extends Background {
    /**
     * image from the int array which is visible
     *
     * @param gameView
     * @param img
     * @param name
     * @param backgroundSpeed
     */
    public BgLayer_1_Clouds(GameView gameView, int[] img, String name, int backgroundSpeed) {
        super(gameView, img, name, backgroundSpeed);
    }

    @Override
    public void updateBackground(int backgroundSpeed) {
        //TODO: Calculate here position for every cloud seperately
        //if I understood it right we can replace imageCounter here with activeDrawable

        //configure speed [now in constructor]
        //this.setBackgroundSpeed((backgroundSpeed==null) ? Constants.Background.defaultBgSpeed : backgroundSpeed); //if given use it, otherwise default value

        //sets the activeDrawable to position 0 (int array) --> already 0 at instantiation
        //this.setActiveDrawable(this.getDisplay(this.getActiveDrawable()));

        //TODO: Now we have getWidth etc. by Gamview.getViewHeight(), etc.
        this.setX(this.getX() - this.getSpeedX());

        System.out.println("Position of BackgroundX is " + this.getX());

        /**TODO
         * if currentBackground image is over getWidth(), load the next Image
         * @getDisplay() returns the active drawable (in the first case, bglayer1_clouds_1)
         *
         * */
        /**TODO */
        if(this.getX() < -4000){
            this.setActiveDrawable(this.getActiveDrawable()+1);
            this.setX(100); /** <- thats the shithole, here we should draw the next image, x should be again at 0, but just draw the next image */
        }

        /** If imageCounter equals the size of the Background its int-array (number of images the obj holds), the counter will start at 0 again
         * eq -> the image bglayer1_clouds_1 will appear again. */
        if(this.getLengthOfBackground() == this.getActiveDrawable()){
            this.setActiveDrawable(0);
        }
    }


}
