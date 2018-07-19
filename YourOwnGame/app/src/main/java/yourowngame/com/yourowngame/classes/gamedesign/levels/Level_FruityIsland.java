package yourowngame.com.yourowngame.classes.gamedesign.levels;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Boba;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Rocketfish;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.actors.player.specializations.Player_Hugo;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.LevelAssignment;
import yourowngame.com.yourowngame.classes.gamedesign.levelassignments.LA_AchievePoints;

public class Level_FruityIsland extends Level {

    public Level_FruityIsland(@NonNull Activity activity, @NonNull Point worldMapPosition) {
        super(activity, worldMapPosition);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player_Hugo(this.getActivity(), 100, Resources.getSystem().getDisplayMetrics().heightPixels / 4, 5, 2));
    }

    @Override
    protected void determineBackgroundLayers() {
        ArrayList<Background> allBgs = new ArrayList<>();
        allBgs.add(new BL_SingleColor(this.getActivity(), R.color.colorShadow));
        allBgs.add(new BL_FlyingElements(this.getActivity(), new int[]{R.drawable.bglayer_1_cloud_3}, 10));
        this.setAllBackgroundLayers(allBgs);
    }

    @Override
    protected void determineAllEnemies() {
        ArrayList<Enemy> allEnemies = new ArrayList<>();
        allEnemies.addAll(EnemyMgr.createRandomEnemies(getActivity(), Enemy_Rocketfish.class, 5));
        allEnemies.addAll(EnemyMgr.createRandomEnemies(getActivity(), Enemy_Boba.class, 5));
        this.setAllEnemies(allEnemies);
    }

    @Override
    protected void determineAllFruits() {
        ArrayList<Fruit> allFruits = new ArrayList<>();
        allFruits.addAll(FruitMgr.createRandomFruits(this.getActivity(), this, Fruit_Meloon.class, 2));
        allFruits.addAll(FruitMgr.createRandomFruits(this.getActivity(), this, Fruit_Avoci.class, 2));
        allFruits.addAll(FruitMgr.createRandomFruits(this.getActivity(), this, Fruit_Pinapo.class, 2));
        this.setAllFruits(allFruits);
    }

    @Override
    protected void playBackgroundMusic() {

    }

    @Override
    protected void determineLevelAssigments() {
        ArrayList<LevelAssignment> allLevelAssignments = new ArrayList<>();
        allLevelAssignments.add(new LA_AchievePoints(17_500, getCurrentLevelHighscore()));
        this.setAllLevelAssignments(allLevelAssignments);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_darkDescent);
    }
}