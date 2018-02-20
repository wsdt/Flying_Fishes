package yourowngame.com.yourowngame.classes.background;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * Created on 18.02.2018.
 *
 * Provides a BackgroundManager
 *
 * Singleton, always just one BackgroundManager
 *
 * TODO: exception handling, improving performance,
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

        this.getBackgroundLayers().add(new Background(gameView, new int[] {R.drawable.bglayer1_clouds_1, R.drawable.bglayer1_clouds_2, R.drawable.bglayer1_clouds_3}, "Heaven", Constants.Background.defaultBgSpeed));
        //TODO: PS: Geile Idee in Schichten zu unterteilen! (also Wolkenschicht, Landschaft usw. zu separieren)
        //TODO: Wenn du ein Levelsystem gemeint hast (für Hintergrundwechsel ab Punkteanzahl z.B., dann können wir später ja mit extends Background eigene Layerschichten oder so denk ich realisieren
            //TODO --> so auch dann levelbezogene Methoden möglich
    }

    public void updateAllBackgroundLayers() {
        for (Background backgroundLayer : this.getBackgroundLayers()) {
            backgroundLayer.updateBackground(null); //todo: maybe give custom speed (maybe also by method itself or save it into background instance [= best I would say]
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
