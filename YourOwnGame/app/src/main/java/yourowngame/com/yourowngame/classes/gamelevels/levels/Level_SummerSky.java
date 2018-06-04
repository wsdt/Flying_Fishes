package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.BobaEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.HappenEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RocketFishEnemy;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Pinapo;
import yourowngame.com.yourowngame.classes.actors.player.specializations.Hugo;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.levelassignments.LA_AchievePoints;


/**
 * LEVEL ONE
 */

public class Level_SummerSky extends Level {
    private static final String TAG = "Lvl_HarmlessSky";

    public Level_SummerSky(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_harmlessSky);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Hugo(this.getActivity(), 100, Resources.getSystem().getDisplayMetrics().heightPixels / 4, 5, 2));
    }

    @Override
    protected void determineBackgroundLayers() {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
         * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/
        this.getAllBackgroundLayers().add(new BL_SingleColor(this.getActivity(), R.color.colorSkyBlue));
        this.getAllBackgroundLayers().add(new BL_FlyingElements(this.getActivity(), new int[]{R.drawable.bglayer_1_cloud_1, R.drawable.bglayer_1_cloud_2, R.drawable.bglayer_1_cloud_3}, 10));

        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() { //Only exception (initialize() here instead of in obj constr, because of createRandomEnemies())
        //Set allEnemies Arraylist
        /** Initializing Bomber-Enemy */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getActivity(), HappenEnemy.class, 2));

        /**Initializing Rocket-Enemy */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getActivity(), RocketFishEnemy.class, 2));

        /** Initializing Spawn-Enemies */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getActivity(), BobaEnemy.class, 2));

        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    protected void determineAllFruits() {
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getActivity(),this, Meloon.class, 1));
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getActivity(),this, Avoci.class, 1));
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getActivity(),this, Pinapo.class, 1));

        Log.d(TAG, "determineAllFruits: Have set global level-dependent fruits.");
    }

    @Override
    protected void determineLevelAssigments() {
        this.getAllLevelAssignments().add(new LA_AchievePoints(1500, this.getCurrentLevelHighscore()));
    }


    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_SummerSky.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
