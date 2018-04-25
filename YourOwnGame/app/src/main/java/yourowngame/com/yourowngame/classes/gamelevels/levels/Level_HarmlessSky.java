package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.BobaEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.HappenEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RocketFish;
import yourowngame.com.yourowngame.classes.actors.player.IPlayer;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.IBackground;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_staticBgImg;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;


/**
 * LEVEL ONE
 */


public class Level_HarmlessSky extends Level implements IBackground {
    private static final String TAG = "Lvl_HarmlessSky";

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player(100, LevelManager.getBackgroundManager().getGameView().getRootView().getHeight() / 4, 5, 2, new int[]{
                R.drawable.hugo_64, R.drawable.hugo_64, R.drawable.hugo_64, R.drawable.hugo_64,
                R.drawable.hugo_64, R.drawable.hugo_64}, IPlayer.DEFAULT_ROTATION, " "));
    }

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
    protected void determineAllEnemies() { //Only exception (initialize() here instead of in obj constr, because of createRandomEnemies())
        //Set allEnemies Arraylist
        /** Initializing Bomber-Enemy */
        HappenEnemy happenEnemyManager = new HappenEnemy();
        happenEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        happenEnemyManager.createRandomEnemies(2); //todo: should be static

        /**Initializing Rocket-Enemy */
        RocketFish rocketEnemyManager = new RocketFish();
        rocketEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        rocketEnemyManager.createRandomEnemies(10);

        /** Initializing Spawn-Enemies */
        BobaEnemy bobaManager = new BobaEnemy();
        bobaManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        bobaManager.createRandomEnemies(2);

        this.getAllEnemies().addAll(HappenEnemy.getEnemyList());
        this.getAllEnemies().addAll(RocketFish.getEnemyList());
        this.getAllEnemies().addAll(BobaEnemy.getEnemyList());
        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    public void cleanUpLevelProperties() {
        //CleanUp Player
        this.getPlayer().cleanup();

        //CleanUp Enemies
        for (Enemy enemy : this.getAllEnemies()) {
            enemy.cleanup();
        }

        //CleanUp Bglayers
        for (Background background : this.getAllBackgroundLayers()) {
            background.cleanup();
        }

        Log.d(TAG, "cleanUpLevelProperties: Clean up all level properties.");
    }

    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_HarmlessSky.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
