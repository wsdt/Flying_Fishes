package yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels;

import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
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
import yourowngame.com.yourowngame.classes.background.layers.BL_FullscreenImage;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.LevelAssignment;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levelassignments.LA_AchievePoints;

public class Level_FruityIsland extends Level {

    public Level_FruityIsland(@NonNull DrawableSurfaceActivity activity, @NonNull Point worldMapPosition) {
        super(activity, worldMapPosition);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player_Hugo(this.getDrawableSurfaceActivity()));
    }

    @Override
    protected void determineBackgroundLayers() {
        ArrayList<Background> allBgs = new ArrayList<>();
        allBgs.add(new BL_FullscreenImage(this.getDrawableSurfaceActivity(), R.drawable.bg_layer_fullscreenimage_mountains_2));
        allBgs.add(new BL_FlyingElements(this.getDrawableSurfaceActivity(), new int[]{R.drawable.bg_layer_flying_elements_clouds_3}, 2));
        this.setBgLayers(allBgs);
    }

    @Override
    protected void determineAllEnemies() {
        ArrayList<Enemy> allEnemies = new ArrayList<>();
        allEnemies.addAll(EnemyMgr.createRandomEnemies(getDrawableSurfaceActivity(), Enemy_Rocketfish.class, 5));
        allEnemies.addAll(EnemyMgr.createRandomEnemies(getDrawableSurfaceActivity(), Enemy_Boba.class, 5));
        this.setEnemies(allEnemies);
    }

    @Override
    protected void determineAllFruits() {
        ArrayList<Fruit> allFruits = new ArrayList<>();
        allFruits.addAll(FruitMgr.createRandomFruits(this.getDrawableSurfaceActivity(), this, Fruit_Meloon.class, 2));
        allFruits.addAll(FruitMgr.createRandomFruits(this.getDrawableSurfaceActivity(), this, Fruit_Avoci.class, 2));
        allFruits.addAll(FruitMgr.createRandomFruits(this.getDrawableSurfaceActivity(), this, Fruit_Pinapo.class, 2));
        this.setFruits(allFruits);
    }

    @Override
    protected void playBackgroundMusic() {

    }

    @Override
    protected void determineLevelAssigments() {
        ArrayList<LevelAssignment> allLevelAssignments = new ArrayList<>();
        allLevelAssignments.add(new LA_AchievePoints(17_500, getLevelHighscore()));
        this.setAllLevelAssignments(allLevelAssignments);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_darkDescent);
    }
}
