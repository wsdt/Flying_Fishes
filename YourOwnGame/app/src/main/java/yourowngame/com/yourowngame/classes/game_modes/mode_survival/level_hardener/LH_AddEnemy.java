package yourowngame.com.yourowngame.classes.game_modes.mode_survival.level_hardener;

import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.EnemyMgr;
import yourowngame.com.yourowngame.classes.game_modes.mode_survival.Level_Survival;
import yourowngame.com.yourowngame.classes.game_modes.mode_survival.level_hardener.interfaces.ISurvival_Hardener_Base;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

public class LH_AddEnemy implements ISurvival_Hardener_Base {
    private static final String TAG = "LH_AddEnemy";

    @Override
    public void execute(@NonNull Level_Survival levelSurvival) {
        if (Level_Survival.SUPPORTED_ENEMIES != null && Level_Survival.SUPPORTED_ENEMIES.length > 0) {
            Enemy e = EnemyMgr.createRandomEnemy(levelSurvival.getActivity(),
                    Level_Survival.SUPPORTED_ENEMIES[RandomMgr.getRandomInt(0, Level_Survival.SUPPORTED_ENEMIES.length - 1)]);
            if (e != null) {
                e.initialize();
                levelSurvival.getCurrentEnemies().add(e);
            }
        } else {
            Log.e(TAG, "execute: Could not add enemy.");
        }
    }
}
