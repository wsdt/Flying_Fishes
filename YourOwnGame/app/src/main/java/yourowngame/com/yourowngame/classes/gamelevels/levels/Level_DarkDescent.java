package yourowngame.com.yourowngame.classes.gamelevels.levels;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RocketFishEnemy;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamelevels.Level;

public class Level_DarkDescent extends Level {
    public Level_DarkDescent(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player(this.getContext(), 100, Resources.getSystem().getDisplayMetrics().heightPixels/4, 5, 2,
                new int[]{R.drawable.player_albert}, IPlayer.DEFAULT_ROTATION, "Albert"));
    }

    @Override
    protected void determineBackgroundLayers() {
        this.getAllBackgroundLayers().add(new BL_SingleColor(this.getContext(), R.color.colorBlack, "Darkness"));
        this.getAllBackgroundLayers().add(new BL_FlyingElements(this.getContext(), "Wolken", new int[]{R.drawable.bglayer_1_cloud_3},10));
    }

    @Override
    protected void determineAllEnemies() {
        this.getAllEnemies().addAll(EnemyMgr.createRandomEnemies(getContext(), RocketFishEnemy.class, 15));
    }

    @Override
    protected void determineAllFruits() {
        this.getAllFruits().addAll(FruitMgr.createRandomFruits(this.getContext(), Meloon.class,4));
    }

    @Override
    protected void playBackgroundMusic() {

    }

    @Override
    protected void determineLevelAssigments() {

    }

    @Override
    protected void determineMetaData() {
        this.setLevelNameResId(R.string.level_levelName_darkDescent);
    }
}
