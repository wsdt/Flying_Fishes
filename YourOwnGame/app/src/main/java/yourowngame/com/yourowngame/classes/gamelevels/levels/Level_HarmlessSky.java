package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.BomberEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RoboticEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.SpawnEnemy;
import yourowngame.com.yourowngame.classes.background.IBackground;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_staticBgImg;
import yourowngame.com.yourowngame.classes.background.layers.IBackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

/**
 * LEVEL ONE
 */


public class Level_HarmlessSky extends Level implements IBackground {
    private static final String TAG = "Lvl_HarmlessSky";

    @Override
    protected void determineBackgroundLayers() {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
        * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/
        this.getAllBackgroundLayers().add(new BackgroundLayer_staticBgImg(LevelManager.getBackgroundManager(), R.color.colorSkyBlue, "Sky", DEFAULT_BG_SPEED));
        this.getAllBackgroundLayers().add(new BackgroundLayer_Clouds(LevelManager.getBackgroundManager(), new int[]{R.drawable.bglayer_1_cloud_1,R.drawable.bglayer_1_cloud_2,R.drawable.bglayer_1_cloud_3}, "Heaven", DEFAULT_BG_SPEED));
        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() {
        //Set allEnemies Arraylist
        /** Initializing Robotic-Enemy */
        RoboticEnemy roboticEnemyManager = new RoboticEnemy();
        roboticEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        roboticEnemyManager.createRandomEnemies(5); //todo: should be static

        /**Initializing Super-Enemy */
        BomberEnemy bomberEnemyManager = new BomberEnemy();
        bomberEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        bomberEnemyManager.createRandomEnemies(10);

        /** Initializing Spawn-Enemies */
        SpawnEnemy spawnEnemyManager = new SpawnEnemy();
        spawnEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        spawnEnemyManager.createRandomEnemies(2);

        this.getAllEnemies().addAll(RoboticEnemy.getEnemyList());
        this.getAllEnemies().addAll(BomberEnemy.getEnemyList());
        this.getAllEnemies().addAll(SpawnEnemy.getEnemyList());
        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_HarmlessSky.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
