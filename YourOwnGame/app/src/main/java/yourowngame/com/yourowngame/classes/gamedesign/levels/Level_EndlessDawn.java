package yourowngame.com.yourowngame.classes.gamedesign.levels;

import android.app.Activity;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Happen;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Rocketfish;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.player.specializations.Player_Hugo;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_FullscreenImage;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.LevelAssignment;
import yourowngame.com.yourowngame.classes.gamedesign.levelassignments.LA_AchievePoints;


/**
 * LEVEL TWO
 */


public class Level_EndlessDawn extends Level {
    private static final String TAG = "Lvl_EndlessDawn";

    public Level_EndlessDawn(@NonNull Activity activity, @NonNull Point worldMapPosition) {
        super(activity, worldMapPosition);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_endlessDawn);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player_Hugo(this.getActivity()));
    }

    @Override
    protected void determineBackgroundLayers() {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
         * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/

        ArrayList<Background> allBgs = new ArrayList<>();
        allBgs.add(new BL_FullscreenImage(this.getActivity(), R.drawable.bg_layer_fullscreenimage_mountains_1));
        allBgs.add(new BL_FlyingElements(this.getActivity(), new int[]{R.drawable.bg_layer_flying_elements_clouds_2}, 9));
        this.setAllBackgroundLayers(allBgs);

        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() { //Only exception (initialize() here instead of in obj constr, because of createRandomEnemies())
        //Set allEnemies Arraylist
        ArrayList<Enemy> allEnemies = new ArrayList<>();

        /* Initializing Bomber-Enemy */
        allEnemies.addAll(EnemyMgr.createRandomEnemies(this.getActivity(), Enemy_Happen.class, 6));

        /*Initializing Rocket-Enemy */
        allEnemies.addAll(EnemyMgr.createRandomEnemies(this.getActivity(), Enemy_Rocketfish.class, 3)); //damit die Leute derweil wirklich was zum Spielen haben haha, haha so geil

        this.setAllEnemies(allEnemies);
        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    protected void determineAllFruits() {
        /* ***************************
         *  FRUIT INITIALIZING AREA *
         ****************************/

        ArrayList<Fruit> allFruits = new ArrayList<>();
        allFruits.addAll(FruitMgr.createRandomFruits(this.getActivity(),this, Fruit_Meloon.class, 1));
        this.setAllFruits(allFruits);

        Log.d(TAG, "determineAllFruits: Have set global level-dependent fruits.");
    }

    @Override
    protected void determineLevelAssigments() {
        ArrayList<LevelAssignment> allLevelAssignments = new ArrayList<>();
        allLevelAssignments.add(new LA_AchievePoints(10_000, getLevelHighscore()));
        this.setAllLevelAssignments(allLevelAssignments);
    }

    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_SummerSky.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
