package yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels;

import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Rocketfish;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.player.specializations.Player_Hugo;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_FullscreenImage;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.LevelAssignment;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levelassignments.LA_AchievePoints;

public class Level_DarkDescent extends Level {

    public Level_DarkDescent(@NonNull DrawableSurfaceActivity drawableSurfaceActivity, @NonNull Point worldMapPosition) {
        super(drawableSurfaceActivity, worldMapPosition);
    }

    @Override
    protected void determinePlayer(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        this.setPlayer(new Player_Hugo(drawableSurfaceActivity));
    }

    @Override
    protected void determineBackgroundLayers(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        ArrayList<Background> allBgs = new ArrayList<>();
        allBgs.add(new BL_FullscreenImage(drawableSurfaceActivity, R.drawable.bg_layer_fullscreenimage_mountains_2));
        allBgs.add(new BL_FlyingElements(drawableSurfaceActivity, new int[]{R.drawable.bg_layer_flying_elements_clouds_3}, 6));
        this.setBgLayers(allBgs);
    }

    @Override
    protected void determineAllEnemies(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        ArrayList<Enemy> allEnemies = new ArrayList<>();
        allEnemies.addAll(EnemyMgr.createRandomEnemies(drawableSurfaceActivity, Enemy_Rocketfish.class, 15));
        this.setEnemies(allEnemies);
    }

    @Override
    protected void determineAllFruits(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        ArrayList<Fruit> allFruits = new ArrayList<>();
        allFruits.addAll(FruitMgr.createRandomFruits(drawableSurfaceActivity, this, Fruit_Meloon.class, 4));
        this.setFruits(allFruits);
    }

    @Override
    protected void playBackgroundMusic() {

    }

    @Override
    protected void determineLevelAssigments() {
        ArrayList<LevelAssignment> allLevelAssignments = new ArrayList<>();
        allLevelAssignments.add(new LA_AchievePoints(12_500, getLevelHighscore())); // open end currently
        this.setAllLevelAssignments(allLevelAssignments);
    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_fruityIsland);
    }

}
