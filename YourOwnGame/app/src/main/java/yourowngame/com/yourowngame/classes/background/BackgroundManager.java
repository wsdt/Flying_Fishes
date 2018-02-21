package yourowngame.com.yourowngame.classes.background;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.background.layers.BgLayerClouds1;
import yourowngame.com.yourowngame.classes.configuration.Constants;
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
    private ArrayList<Background> backgroundLayers;
    private GameView gameView;


    //Singleton
    private static BackgroundManager INSTANCE;
    public static BackgroundManager getInstance(@NonNull GameView gameView){
        return INSTANCE == null ? INSTANCE = new BackgroundManager(gameView) : INSTANCE;
    }

    //create backgrounds here
    private BackgroundManager(@NonNull GameView gameView){
        //Firstly initialize arraylist (nullpointer)
        this.setBackgroundLayers(new ArrayList<Background>());
        this.setGameView(gameView);

        this.getBackgroundLayers().add(new BgLayerClouds1(gameView, new int[] {R.drawable.background/*, R.drawable.bglayer1_clouds_2, R.drawable.bglayer1_clouds_3*/}, "Heaven", Constants.Background.defaultBgSpeed));
        //TODO: PS: Geile Idee in Schichten zu unterteilen! (also Wolkenschicht, Landschaft usw. zu separieren)
        //TODO: Wenn du ein Levelsystem gemeint hast (für Hintergrundwechsel ab Punkteanzahl z.B., dann können wir später ja mit extends Background eigene Layerschichten oder so denk ich realisieren
            //TODO --> so auch dann levelbezogene Methoden möglich
    }

    public void updateAllBackgroundLayers() {
        for (Background backgroundLayer : this.getBackgroundLayers()) {
            backgroundLayer.updateBackground(10); //todo: maybe give custom speed (maybe also by method itself or save it into background instance [= best I would say]
        }
    }

    public ArrayList<Background> getBackgroundLayers() {
        return backgroundLayers;
    }

    public void setBackgroundLayers(ArrayList<Background> backgroundLayers) {
        this.backgroundLayers = backgroundLayers;
    }

    /** backgrounds (all levels) */
    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
