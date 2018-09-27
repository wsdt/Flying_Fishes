package yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers;

import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitPower;
import yourowngame.com.yourowngame.classes.actors.player.Player;

/** Makes player e.g. 50% (= amount=1.5) faster  | e.g. 25% slower (= amount=0.75) in going up and down. */
public class FruitPower_PlayerSpeed extends FruitPower {
    private static final String TAG = "PlayerSpeed";
    private Player currPlayer;

    public FruitPower_PlayerSpeed(double amount, long durationMilliSeconds, @NonNull Player currPlayer) {
        super(amount, durationMilliSeconds);
        this.setCurrPlayer(currPlayer);
        this.setResString(R.string.fruitPower_playerSpeed_effect);
    }

    @Override
    public void execute() {
        Log.d(TAG, "execute: Started method.");
        this.getCurrPlayer().setSpeedY(this.getCurrPlayer().getSpeedY()*this.getAmount());

        //Stop also after duration expires
        startStopTimer();
    }


    @Override
    public void stop() {
        this.getCurrPlayer().setSpeedY(this.getCurrPlayer().getSpeedY()/this.getAmount());
    }

    //GETTER/SETTER -------------------------------------
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }
}
