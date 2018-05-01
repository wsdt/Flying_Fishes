package yourowngame.com.yourowngame.classes.gamelevels.levels;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

public class Level_UnknownLand extends Level {
    private String TAG = "Lvl_UnknownLand";


    @Override
    protected void determinePlayer() {
        this.setPlayer(new Player(100, LevelManager.getBackgroundManager().getGameView().getRootView().getHeight() / 4, 5, 2, new int[]{
                R.drawable.player_heli_blue_1, R.drawable.player_heli_blue_2, R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_4,
                R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_2}, IPlayer.DEFAULT_ROTATION, "Rezy"));
    }

    @Override
    protected void determineBackgroundLayers() {

    }

    @Override
    protected void determineAllEnemies() {

    }

    @Override
    protected void determineFruits() {

    }

    @Override
    protected void playBackgroundMusic() {

    }

    @Override
    public void cleanUpLevelProperties() {

    }
}
