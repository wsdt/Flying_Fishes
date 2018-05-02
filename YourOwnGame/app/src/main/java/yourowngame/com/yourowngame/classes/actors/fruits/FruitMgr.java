package yourowngame.com.yourowngame.classes.actors.fruits;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/** Used for generating or/and managing fruits.*/
//TODO: Also do this for Enemy! :D
public class FruitMgr {
    private static final String TAG = "FruitMgr";

    public static Meloon createMeloon() {
        Meloon meloon = new Meloon((RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 100)),
                RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT + 100),
                Meloon.SPEED_X,    //Speed x -> should be constant, so all fruits come in at the same time!
                Meloon.SPEED_Y,    //Speed y
                new int[] {R.drawable.meloon}, (int) Meloon.DEFAULT_ROTATION, "Meloon");

        //TODO: Looks terrible (maybe into constructor or make it implicit)
        meloon.setCurrentBitmap(Meloon.getImages()[0]);

        return meloon;
    }
}
