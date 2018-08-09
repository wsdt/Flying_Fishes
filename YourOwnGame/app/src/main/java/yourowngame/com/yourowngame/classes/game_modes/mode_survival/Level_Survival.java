package yourowngame.com.yourowngame.classes.game_modes.mode_survival;

import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Boba;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Happen;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Rocketfish;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.background.layers.BL_FullscreenImage;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.classes.game_modes.mode_survival.level_hardener.LH_AddEnemy;
import yourowngame.com.yourowngame.classes.game_modes.mode_survival.level_hardener.interfaces.ISurvival_Hardener_Base;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

/**
 * Kind of dynamic lvl (adapts it's difficulty dynamically with time).
 * There is NO NEED to create more survival lvls!
 */
public class Level_Survival extends DrawableLevel {
    private static final String TAG = "Level";
    /**
     * Add here all supported fruits, enemies which should be used to make the game harder.
     */
    public static final Class[] SUPPORTED_FRUITS = {Fruit_Meloon.class, Fruit_Avoci.class, Fruit_Pinapo.class};
    public static final Class[] SUPPORTED_ENEMIES = {Enemy_Rocketfish.class, Enemy_Boba.class, Enemy_Happen.class};
    public static final Class[] ACTIVATED_HARDENERS = {LH_AddEnemy.class};

    /**
     * Var of this type has to be provided to adaptDifficultyDynamically()
     * Every x milliseconds the game gets harder/modified.
     */
    public enum DIFFICULTY {
        EASY(250_000), MEDIUM(120_000), HARD(40_000), IMPOSSIBLE(15_000);
        private int milliseconds;

        DIFFICULTY(int milliseconds) {
            this.milliseconds = milliseconds;
        }

        public int getMilliseconds() {
            return this.milliseconds;
        }
    }

    /**
     * Thread which makes the game harder in selected cycles
     */
    private static Thread difficultyThread;

    public Level_Survival(@NonNull GameViewActivity gameViewActivity, DIFFICULTY difficulty) {
        super(gameViewActivity);
        this.runStartConfiguration(gameViewActivity, difficulty);
    }

    private void runStartConfiguration(@NonNull GameViewActivity gameViewActivity, DIFFICULTY difficulty) {
        this.getBgLayers().add(new BL_FullscreenImage(gameViewActivity, R.drawable.bg_layer_fullscreenimage_mountains_1));
        this.getEnemies().addAll(EnemyMgr.createRandomEnemies(gameViewActivity, Enemy_Rocketfish.class, 1));

        adaptDifficultyDynamically(gameViewActivity, difficulty);
    }

    private void adaptDifficultyDynamically(@NonNull final GameViewActivity gameViewActivity, final DIFFICULTY difficulty) {
        Level_Survival.setDifficultyThread(new Thread(new Runnable() {
            @Override
            public void run() {
                //needs to be closed externally
                while (true) {
                    try {
                        this.wait(difficulty.getMilliseconds());
                        Level_Survival.this.addDifficulty(gameViewActivity);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break; //also exit loop
                    }
                }
            }
        }));
    }

    private void addDifficulty(@NonNull GameViewActivity gameViewActivity) {
        if (ACTIVATED_HARDENERS != null && ACTIVATED_HARDENERS.length > 0) {
            try {
                ((ISurvival_Hardener_Base) ACTIVATED_HARDENERS[RandomMgr.getRandomInt(0, ACTIVATED_HARDENERS.length - 1)]
                        .newInstance()).execute(gameViewActivity,this);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "addDifficulty: Found no lvlHardeners!");
        }
    }


    //GETTER/SETTER +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static Thread getDifficultyThread() {
        return difficultyThread;
    }

    public static void setDifficultyThread(Thread difficultyThread) {
        Level_Survival.difficultyThread = difficultyThread;
    }
}
