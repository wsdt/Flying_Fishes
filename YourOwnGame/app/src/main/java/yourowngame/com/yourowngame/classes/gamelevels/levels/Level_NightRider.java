package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.content.Context;
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
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.background.interfaces.IBackground;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamelevels.Level;


/**
 * LEVEL TWO
 */


public class Level_NightRider extends Level {
    private static final String TAG = "Lvl_HarmlessSky";

    public Level_NightRider(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_nightRider);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player(this.getContext(), 100, Resources.getSystem().getDisplayMetrics().heightPixels / 4, 5, 2, new int[]{
                R.drawable.player_albert}, IPlayer.DEFAULT_ROTATION, "Hugo"));
    }

    @Override
    protected void determineBackgroundLayers() {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
        * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/
        this.getAllBackgroundLayers().add(new BL_SingleColor(this.getContext(), R.color.colorPrimaryDark, "DarkSky"));
        this.getAllBackgroundLayers().add(new BL_FlyingElements(this.getContext(), "Gewitter", new int[]{R.drawable.bglayer_1_cloud_2},8));

        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() { //Only exception (initialize() here instead of in obj constr, because of createRandomEnemies())
        //Set allEnemies Arraylist
        /** Initializing Bomber-Enemy */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), HappenEnemy.class,1));

        /**Initializing Rocket-Enemy */
       this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), RocketFishEnemy.class, 12)); //damit die Leute derweil wirklich was zum Spielen haben haha, haha so geil

        /** Initializing Spawn-Enemies */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), BobaEnemy.class, 1));

        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    protected void determineAllFruits() {
        /****************************
         *  FRUIT INITIALIZING AREA *
         ****************************/

        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getContext(), Meloon.class,1));
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getContext(), Avoci.class,1));
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getContext(), Pinapo.class,1));

        Log.d(TAG, "determineAllFruits: Have set global level-dependent fruits.");
    }

    @Override
    protected void determineLevelAssigments() {/*this level has no assignments yet, so user keeps playing forever*/}

    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_SummerSky.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
