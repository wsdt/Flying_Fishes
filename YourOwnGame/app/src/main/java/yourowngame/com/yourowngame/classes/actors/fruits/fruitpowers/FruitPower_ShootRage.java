package yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitPower;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.interfaces.IProjectile;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/** Adds additional projectiles (= amount) and sets for all an arbitrary PosY to simulate
 * an amazing shoot. */
public class FruitPower_ShootRage extends FruitPower {
    private static final String TAG = "RemoveEnemies";
    private Player currPlayer;

    /** @param amount: How many enemies to remove. Here exceptionally a int instead of double so
     * we can cast it without any problems later.
     *
     * Duration not needed for this fruitpower bc. projectiles get rvm automatically.*/
    public FruitPower_ShootRage(int amount, @NonNull Player currPlayer) {
        super(amount, 0);
        this.setCurrPlayer(currPlayer);
    }


    @Override
    public void execute() {
        Log.d(TAG, "execute: Started method.");

        //Add desired amount of projectiles additionally
        for (int i = 0;i<this.getAmount();i++) {
            this.getCurrPlayer().addProjectiles();
        }

        for (Projectile projectile : this.getCurrPlayer().getProjectiles()) {
            projectile.setPosY(RandomMgr.getRandomFloat(0, GameViewActivity.GAME_HEIGHT));
        }

        //Show removed enemies after duration
        //startStopTimer(); --> NOT REQUIRED, bc. projectiles go away.
    }

    @Override
    public void stop() { /*This fruit power does not require a stop() method. */}

    //GETTER/SETTER ----------------------------------------------------
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }
}
