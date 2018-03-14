package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.RoboticEnemy;
import yourowngame.com.yourowngame.classes.actors.SuperEnemy;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.background.layers.BackgroundLayer_staticBgImg;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

public class Level_HarmlessSky extends Level{
    private static final String TAG = "Lvl_HarmlessSky";

    @Override
    protected void determineBackgroundLayers() {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
        * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/
        this.getAllBackgroundLayers().add(new BackgroundLayer_staticBgImg(LevelManager.getBackgroundManager(), R.color.colorSkyBlue, "Sky", Constants.Background.defaultBgSpeed));
        this.getAllBackgroundLayers().add(new BackgroundLayer_Clouds(LevelManager.getBackgroundManager(), new int[]{R.drawable.bglayer_1_cloud_1,R.drawable.bglayer_1_cloud_2,R.drawable.bglayer_1_cloud_3}, "Heaven", Constants.Background.defaultBgSpeed));
        Log.d(TAG, "determineBackgroundLayers: Have set layers.");

        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() {
        //Set allEnemies Arraylist
        /** Initializing Robotic-Enemy */
        RoboticEnemy roboticEnemyManager = new RoboticEnemy();
        roboticEnemyManager.createRandomEnemies(5);
        roboticEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());

        /**Initializing Super-Enemy */
        SuperEnemy superEnemyManager = new SuperEnemy();
        superEnemyManager.createRandomEnemies(10);
        superEnemyManager.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());

        this.getAllEnemies().addAll(RoboticEnemy.getEnemyList());
        this.getAllEnemies().addAll(SuperEnemy.getEnemyList());
        Log.d(TAG, "determineAllEnemies: Have set global level-dependent enemylist.");
    }
}
