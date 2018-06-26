package yourowngame.com.yourowngame.classes.gamedesign.levels;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.actors.player.specializations.Player_Hugo;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.levelassignments.LA_AchievePoints;

public class Level_BonusLevel01 extends Level {

    public final String TAG = "BonusLevel_01";

    public Level_BonusLevel01(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_bonusLevel01);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player_Hugo(this.getActivity(), 100, Resources.getSystem().getDisplayMetrics().heightPixels / 4, 5, 2));
    }

    @Override
    protected void determineBackgroundLayers() {
        /*This.getAllBackgroundLayers can be directly used with add without additional declaration, because object is initialized implicitly
         * - Add layers acc. to the desired order (first add() is the lowest layer etc.)*/
        this.getAllBackgroundLayers().add(new BL_SingleColor(this.getActivity(), R.color.colorAccent));
        this.getAllBackgroundLayers().add(new BL_FlyingElements(this.getActivity(), new int[]{R.drawable.bglayer_1_cloud_2}, 15));

        Log.d(TAG, "determineBackgroundLayers: Have set layers.");
        //no setAllBackgroundLayers necessary (reference)
    }

    @Override
    protected void determineAllEnemies() {
        //empty, cause bonus level
    }


    @Override
    protected void determineAllFruits() {
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getActivity(), this, Fruit_Meloon.class, 5));
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getActivity(), this, Fruit_Avoci.class, 5));
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getActivity(), this, Fruit_Pinapo.class, 5));

        for (Fruit f : this.getAllFruits()){
            f.removeFruitPowers(this);
        }
    }

    @Override
    protected void playBackgroundMusic() {

    }

    @Override
    protected void determineLevelAssigments() {
        getAllLevelAssignments().add(new LA_AchievePoints(10_000, getCurrentLevelHighscore()));
    }
}
