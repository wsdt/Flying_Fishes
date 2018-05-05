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
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.background.interfaces.IBackground;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_staticBgImg;
import yourowngame.com.yourowngame.classes.gamelevels.Level;


/**
 * LEVEL ONE
 */


public class Level_01 extends Level {
    private static final String TAG = "Lvl_HarmlessSky";

    public Level_01(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_harmlessSky);
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
        this.getAllBackgroundLayers().add(new BackgroundLayer_staticBgImg(this.getContext(), R.color.colorSkyBlue, "Sky", IBackground.DEFAULT_BG_SPEED));
        this.getAllBackgroundLayers().add(new BackgroundLayer_Clouds(this.getContext(), new int[]{R.drawable.bglayer_1_cloud_1,R.drawable.bglayer_1_cloud_2,R.drawable.bglayer_1_cloud_3}, "Heaven", IBackground.DEFAULT_BG_SPEED));

        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() { //Only exception (initialize() here instead of in obj constr, because of createRandomEnemies())
        //Set allEnemies Arraylist
        /** Initializing Bomber-Enemy */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), HappenEnemy.class,3));

        /**Initializing Rocket-Enemy */
       this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), RocketFishEnemy.class, 3));

        /** Initializing Spawn-Enemies */
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(this.getContext(), BobaEnemy.class, 3));

        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    protected void determineAllFruits() {
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getContext(), Meloon.class,1));

        Log.d(TAG, "determineAllFruits: Have set global level-dependent fruits.");
    }

    @Override
    public boolean areLevelAssignmentsAchieved() {
        if (getCurrentLevelHighscore().getValue() > 1000) {
            return true;
        }
        return false;
    }

    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_01.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
