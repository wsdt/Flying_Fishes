package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.BobaEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.HappenEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RocketFishEnemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.interfaces.IBackground;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_staticBgImg;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;


/**
 * LEVEL ONE
 */


public class Level_NightRider extends Level {
    private static final String TAG = "Lvl_HarmlessSky";

    public Level_NightRider (@NonNull Context context) {
        super(context);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_nightRider);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player(this.getContext(), 100, Resources.getSystem().getDisplayMetrics().heightPixels / 4, 5, 2, new int[]{
                R.drawable.player_hugo}, IPlayer.DEFAULT_ROTATION, "Hugo"));
    }

    @Override
    protected void determineBackgroundLayers() {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
        * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/
        this.getAllBackgroundLayers().add(new BackgroundLayer_staticBgImg(this.getContext(), R.color.colorPrimaryDark, "DarkSky", IBackground.DEFAULT_BG_SPEED));
        this.getAllBackgroundLayers().add(new BackgroundLayer_Clouds(this.getContext(), new int[]{R.drawable.bglayer_1_cloud_2}, "Gewitter", IBackground.DEFAULT_BG_SPEED));

        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() { //Only exception (initialize() here instead of in obj constr, because of createRandomEnemies())
        //Set allEnemies Arraylist
        /** Initializing Bomber-Enemy */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), HappenEnemy.class,1));

        /**Initializing Rocket-Enemy */
       this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), RocketFishEnemy.class, 12)); //damit die Leute derweil wirklich was zum Spielen haben haha

        /** Initializing Spawn-Enemies */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), BobaEnemy.class, 1));

        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    protected void determineAllFruits() {
        /****************************
         *  FRUIT INITIALIZING AREA *
         ****************************/

        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getContext(), Meloon.class,2));

        Log.d(TAG, "determineAllFruits: Have set global level-dependent fruits.");
    }

    @Override
    public boolean areLevelAssignmentsAchieved() {
        //just let it endless until we have a third lvl etc. (so last level should always return false)
        return false;
    }

    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_HarmlessSky.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
