package yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Boba;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Happen;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Rocketfish;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.actors.player.specializations.Player_Hugo;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.LevelAssignment;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levelassignments.LA_AchievePoints;


/**
 * LEVEL ONE
 */

public class Level_LucifersGameRoom extends Level {
    private static final String TAG = "Lvl_LucifersGameRoom";

    public Level_LucifersGameRoom(@NonNull DrawableSurfaceActivity drawableSurfaceActivity, @NonNull Point worldMapPosition) {
        super(drawableSurfaceActivity, worldMapPosition);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_lucifersGameRoom);
    }

    @Override
    protected void determinePlayer(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        this.setPlayer(new Player_Hugo(drawableSurfaceActivity));
    }

    @Override
    protected void determineBackgroundLayers(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
         * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/
        ArrayList<Background> allBgs = new ArrayList<>();
        allBgs.add(new BL_SingleColor(drawableSurfaceActivity,R.color.colorDarkRed));
        allBgs.add(new BL_FlyingElements(drawableSurfaceActivity, new int[]{R.drawable.bg_layer_flying_elements_clouds_1, R.drawable.bg_layer_flying_elements_clouds_2, R.drawable.bg_layer_flying_elements_clouds_3}, 20));
        this.setBgLayers(allBgs);

        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) { //Only exception (initialize() here instead of in obj constr, because of createRandomEnemies())
        //Set allEnemies Arraylist
        ArrayList<Enemy> allEnemies = new ArrayList<>();

        /* Initializing Bomber-Enemy */
        allEnemies.addAll(EnemyMgr.createRandomEnemies(drawableSurfaceActivity, Enemy_Happen.class, 20));

        /*Initializing Rocket-Enemy */
        allEnemies.addAll(EnemyMgr.createRandomEnemies(drawableSurfaceActivity, Enemy_Rocketfish.class, 1));

        /* Initializing Spawn-Enemies */
        allEnemies.addAll(EnemyMgr.createRandomEnemies(drawableSurfaceActivity, Enemy_Boba.class, 5));

        this.setEnemies(allEnemies);
        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }

    @Override
    protected void determineAllFruits(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        ArrayList<Fruit> allFruits = new ArrayList<>();
        allFruits.addAll(FruitMgr.createRandomFruits(drawableSurfaceActivity,this, Fruit_Pinapo.class, 5));
        this.setFruits(allFruits);

        Log.d(TAG, "determineAllFruits: Have set global level-dependent fruits.");
    }

    @Override
    protected void determineLevelAssigments() {
        ArrayList<LevelAssignment> allLevelAssignments = new ArrayList<>();
        allLevelAssignments.add(new LA_AchievePoints(200000, getLevelHighscore()));
        this.setAllLevelAssignments(allLevelAssignments);
    }


    @Override
    protected void playBackgroundMusic() {
        //TODO: play bg sound [search resource] --> Level_SummerSky.soundMgr.play(LevelManager.getBackgroundManager().getGameView().getActivityContext(),R.raw.bgMusicLvl1,true);
    }
}
