package yourowngame.com.yourowngame.classes.game_modes.mode_survival.level_hardener.interfaces;

import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.classes.game_modes.mode_survival.Level_Survival;

public interface ISurvival_Hardener_Base {
    void execute(@NonNull DrawableSurfaceActivity drawableSurfaceActivity, @NonNull Level_Survival levelSurvival);
}
