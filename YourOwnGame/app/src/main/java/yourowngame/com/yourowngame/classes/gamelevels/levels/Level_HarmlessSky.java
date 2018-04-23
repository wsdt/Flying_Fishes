package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RocketEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.BomberEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.SpawnEnemy;
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
                R.drawable.rezy_spritesheet02, R.drawable.rezy_spritesheet02, R.drawable.rezy_spritesheet03, R.drawable.rezy_spritesheet04,
                R.drawable.rezy_spritesheet02, R.drawable.rezy_spritesheet02}, IPlayer.DEFAULT_ROTATION, "Rezy"));
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
        BomberEnemy bomberEnemyManager = new BomberEnemy();
        bomberEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        bomberEnemyManager.createRandomEnemies(2); //todo: should be static

        /**Initializing Super-Enemy */
        RocketEnemy rocketEnemyManager = new RocketEnemy();
        rocketEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        rocketEnemyManager.createRandomEnemies(5);

        /** Initializing Spawn-Enemies */
        SpawnEnemy spawnEnemyManager = new SpawnEnemy();
        spawnEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
        spawnEnemyManager.createRandomEnemies(2);

        this.getAllEnemies().addAll(BomberEnemy.getEnemyList());
        this.getAllEnemies().addAll(RocketEnemy.getEnemyList());
        this.getAllEnemies().addAll(SpawnEnemy.getEnemyList());
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
