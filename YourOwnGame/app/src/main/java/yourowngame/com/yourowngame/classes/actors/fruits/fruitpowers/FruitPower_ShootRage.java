package yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers;

import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitPower;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.ProjectileMgr;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * Adds additional projectiles (= amount) and sets for all an arbitrary PosY to simulate
 * an amazing shoot.
 */
public class FruitPower_ShootRage extends FruitPower {
    private static final String TAG = "RemoveEnemies";
    private Player currPlayer;

    /**
     * @param amount: How many projectiles to shoot (if enough ammo).
     *                Duration not needed for this fruitpower bc. projectiles get rvm automatically.
     */
    public FruitPower_ShootRage(int amount, @NonNull Player currPlayer) {
        super(amount, 20); //dummy value bc. startStop never called.
        this.setCurrPlayer(currPlayer);
        this.setResString(R.string.fruitPower_shootRage_effect);
    }


    @Override
    public void execute() {
        Log.d(TAG, "execute: Started method.");

        /* Shoot desired amount of projectiles (projectiles only shot if munition not exhausted). */
        for (int i = 0; i < this.getAmount(); i++) {
            Projectile p = ProjectileMgr.shoot(this.getCurrPlayer(), true);
            if (p != null) {
                //also set random Y
                p.setPosY(RandomMgr.getRandomFloat(0, DrawableSurfaces.getDrawHeight())); //try to shoot regardless whether we have munition at that time
            }
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
