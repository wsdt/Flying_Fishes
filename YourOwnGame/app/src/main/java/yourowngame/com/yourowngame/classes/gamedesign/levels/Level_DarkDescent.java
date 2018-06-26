package yourowngame.com.yourowngame.classes.gamedesign.levels;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Rocketfish;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.player.specializations.Player_Hugo;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.levelassignments.LA_AchievePoints;

public class Level_DarkDescent extends Level {

    public Level_DarkDescent(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player_Hugo(this.getActivity(), 100, Resources.getSystem().getDisplayMetrics().heightPixels / 4, 5, 2));
    }

    @Override
    protected void determineBackgroundLayers() {
        this.getAllBackgroundLayers().add(new BL_SingleColor(this.getActivity(), R.color.colorBlack));
        this.getAllBackgroundLayers().add(new BL_FlyingElements(this.getActivity(), new int[]{R.drawable.bglayer_1_cloud_3}, 10));
    }

    @Override
    protected void determineAllEnemies() {
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(getActivity(), Enemy_Rocketfish.class, 15));
    }

    @Override
    protected void determineAllFruits() {
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getActivity(), this, Fruit_Meloon.class, 4));

    }

    @Override
    protected void playBackgroundMusic() {

    }

    @Override
    protected void determineLevelAssigments() {
        getAllLevelAssignments().add(new LA_AchievePoints(15_000, getCurrentLevelHighscore()));
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_darkDescent);
    }

}
