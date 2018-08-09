package yourowngame.com.yourowngame.classes.mode_survival;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Boba;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Happen;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Rocketfish;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_FullscreenImage;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.classes.mode_survival.level_hardener.LH_AddEnemy;
import yourowngame.com.yourowngame.classes.mode_survival.level_hardener.interfaces.ISurvival_Hardener_Base;

/**
 * Kind of dynamic lvl (adapts it's difficulty dynamically with time).
 * There is NO NEED to create more survival lvls!
 */
public class Level_Survival {
    private static final String TAG = "Level";
    /**
     * Add here all supported fruits, enemies which should be used to make the game harder.
     */
    public static final Class[] SUPPORTED_FRUITS = {Fruit_Meloon.class, Fruit_Avoci.class, Fruit_Pinapo.class};
    public static final Class[] SUPPORTED_ENEMIES = {Enemy_Rocketfish.class, Enemy_Boba.class, Enemy_Happen.class};
    public static final Class[] ACTIVATED_HARDENERS = {LH_AddEnemy.class};

    /**
     * Var of this type has to be provided to adaptDifficultyDynamically()
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
    private Activity activity;
    private Player player;

    /**
     * Current means that they are dynamic
     */
    private ArrayList<Background> currentBgLayers = new ArrayList<>(); //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    private ArrayList<Enemy> currentEnemies = new ArrayList<>(); //MUST NOT BE STATIC (different levels, different enemies), All enemies on screen (will be spawned again if isGone) for specific level
    private ArrayList<Fruit> currentFruits = new ArrayList<>();

    public Level_Survival(@NonNull Activity activity, DIFFICULTY difficulty) {
        this.setActivity(activity);
        this.runStartConfiguration(difficulty);
    }

    private void runStartConfiguration(DIFFICULTY difficulty) {
        this.getCurrentBgLayers().add(new BL_FullscreenImage(this.getActivity(), R.drawable.bg_layer_fullscreenimage_mountains_1));
        this.getCurrentEnemies().addAll(EnemyMgr.createRandomEnemies(this.getActivity(), Enemy_Rocketfish.class, 1));

        adaptDifficultyDynamically(difficulty);
    }

    private void adaptDifficultyDynamically(final DIFFICULTY difficulty) {
        Level_Survival.setDifficultyThread(new Thread(new Runnable() {
            @Override
            public void run() {
                //needs to be closed externally
                while (true) {
                    try {
                        this.wait(difficulty.getMilliseconds());
                        Level_Survival.this.addDifficulty();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break; //also exit loop
                    }
                }
            }
        }));
    }

    private void addDifficulty() {
        if (ACTIVATED_HARDENERS != null && ACTIVATED_HARDENERS.length > 0) {
            try {
                ((ISurvival_Hardener_Base) ACTIVATED_HARDENERS[RandomMgr.getRandomInt(0, ACTIVATED_HARDENERS.length - 1)]
                        .newInstance()).execute(this);
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
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Background> getCurrentBgLayers() {
        return currentBgLayers;
    }

    public void setCurrentBgLayers(ArrayList<Background> currentBgLayers) {
        this.currentBgLayers = currentBgLayers;
    }

    public ArrayList<Enemy> getCurrentEnemies() {
        return currentEnemies;
    }

    public void setCurrentEnemies(ArrayList<Enemy> currentEnemies) {
        this.currentEnemies = currentEnemies;
    }

    public ArrayList<Fruit> getCurrentFruits() {
        return currentFruits;
    }

    public void setCurrentFruits(ArrayList<Fruit> currentFruits) {
        this.currentFruits = currentFruits;
    }

    public static Thread getDifficultyThread() {
        return difficultyThread;
    }

    public static void setDifficultyThread(Thread difficultyThread) {
        Level_Survival.difficultyThread = difficultyThread;
    }
}
