package yourowngame.com.yourowngame.classes.background;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.configuration.Constants;

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
    private ArrayList<Background> backgroundLayers;  /** backgrounds (all levels) */

    //Singleton
    private static BackgroundManager INSTANCE = new BackgroundManager();
    public static BackgroundManager getInstance(){
        return INSTANCE;
    }

    //private access, due to singleton
    //create backgrounds here
    private BackgroundManager(){
        //Firstly initialize arraylist (nullpointer)
        this.setBackgroundLayers(new ArrayList<Background>());

        this.getBackgroundLayers().add(new Background(new int[] {R.drawable.clouds_1, R.drawable.clouds_2, R.drawable.clouds_3}, "Heaven", Constants.Background.defaultBgSpeed));
        //TODO: PS: Geile Idee in Schichten zu unterteilen! (also Wolkenschicht, Landschaft usw. zu separieren)
        //TODO: Wenn du ein Levelsystem gemeint hast (für Hintergrundwechsel ab Punkteanzahl z.B., dann können wir später ja mit extends Background eigene Layerschichten oder so denk ich realisieren
            //TODO --> so auch dann levelbezogene Methoden möglich
    }

    public void updateAllBackgroundLayers() {
        for (Background backgroundLayer : this.getBackgroundLayers()) {
            backgroundLayer.updateBackground(null); //todo: maybe give custom speed (maybe also by method itself or save it into background instance [= best I would say]
        }
    }



    // by creation, 1 = level 1, 2 = level 2 etc. --> not necessary anymore (just use: this.getBackgroundLayers().get(index)
    /*public Background setBackgroundLevel(int i){
        switch(i){
            case 1: currentBackground = backgroundLevelOne; break;
            case 2: //not implemented yet, more backgrounds comming later!
        }
    }*/


    public ArrayList<Background> getBackgroundLayers() {
        return backgroundLayers;
    }

    public void setBackgroundLayers(ArrayList<Background> backgroundLayers) {
        this.backgroundLayers = backgroundLayers;
    }
}
