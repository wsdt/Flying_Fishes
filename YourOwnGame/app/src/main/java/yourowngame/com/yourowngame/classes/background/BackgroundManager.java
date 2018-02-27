package yourowngame.com.yourowngame.classes.background;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_staticBgImg;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * Created on 18.02.2018.
 * Provides a BackgroundManager
 * Singleton, always just one BackgroundManager
 *
 * TODO: So we've got two options now how to show our GUI Content
 *
 * Option 1:
 *
 * We somehow manage to create an animation that draws one image after another
 * (fe -> cloud_1 has reached end of screen, cloud_2 will be drawn exactly
 * after cloud_1 has left the screen on the right side!)
 *
 * Option 2:
 *
 * We'll make the background blue (for heaven, red for hell and so on...) and build clouds (for heaven)
 * that will appear randomly! (which also means, that we're creating
 * a kind of unique-UI, because the clouds will never appear
 * in the same way, they'll always be somehow unique!
 * main question here is, will it look as good as if we draw it with Photoshop?
 *
 * My Opinion:
 *
 * number one will surely be more interesting, because the ability of photoshop
 * will take us further than creating it on our own.
 * BUT -> creating unique backgrounds is kinda surprising & will surely
 * convey something to the user. So every start on level One, will show
 * 'em other clouds on other positions.
 *
 * I'll leave it to you!
 *
 */

public class BackgroundManager {

    private static final String TAG = "BackgroundManager";
    private ArrayList<Background> backgroundLayers = new ArrayList<Background>();
    private GameView gameView;
    private LevelManager levelManager;


    //Singleton
    private static BackgroundManager INSTANCE;

    public static BackgroundManager getInstance(@NonNull GameView gameView) {
        return INSTANCE == null ? INSTANCE = new BackgroundManager(gameView) : INSTANCE;
    }

    //create backgrounds here
    private BackgroundManager(@NonNull GameView gameView) {
        this.setGameView(gameView);
        this.setLevelManager(LevelManager.getInstance(this));

        //Not really necessary now, because we have the levelManager as member which contains all levels (inkl. backgroundLayers)
        this.setBackgroundLayers(this.getLevelManager().getCurrentLevelObj().getBackgroundLayers());
    }

    public void updateAllBackgroundLayers() {
        for (Background backgroundLayer : this.getBackgroundLayers()) {
            backgroundLayer.updateBackground();
        }
    }

    public void drawAllBackgroundLayers(@NonNull Canvas canvas) {
        for (Background backgroundLayer : this.getBackgroundLayers()) {
            backgroundLayer.drawBackground(canvas);
        }
    }

    public ArrayList<Background> getBackgroundLayers() {
        return backgroundLayers;
    }

    public void setBackgroundLayers(ArrayList<Background> backgroundLayers) {
        this.backgroundLayers = backgroundLayers;
    }

    /**
     * backgrounds (all levels)
     */
    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(@NonNull GameView gameView) {
        this.gameView = gameView;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }
}


    /*private void loadPhoneDisplaySize() {
        Log.d(TAG, "loadPhoneDisplaySize: Loading max x/y!");
        Display phoneDisplay = ((Activity) gameView.getContext()).getWindowManager().getDefaultDisplay(); //because of this casting we have in GameView constructor Activity and not Context!
        Point size = new Point();
        phoneDisplay.getSize(size); //calculate size of display
        screenWidth = size.x; //no setter for screenwidth (at least no public/protected one)
        screenHeight = size.y; //same as width
    }
}

    */