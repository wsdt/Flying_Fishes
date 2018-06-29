package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameLevelHorizontalActivity;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.gamedesign.LevelManager;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

public class LevelAchievedDialog {
    private static final String TAG = "LevelAchievedDialog";

    @Enhance(byDeveloper = "Solution49", message = "Guess we should make a short summary after every level & what he has achieved!")
    public static void show(@NonNull final GameView gameView) {
        final Activity activity = gameView.getDrawableSurfaceActivity();

        //To prevent badTokenExceptions
        if (!activity.isFinishing()) {
            final LevelManager lm = new LevelManager(activity);

            LovelyStandardDialog ld = new LovelyStandardDialog(activity)
                    .setTopColorRes(R.color.colorPrimaryDark)
                    .setIcon(Constants.APP_ICON)
                    .setTitle(R.string.dialog_levelachieved_title)
                    .setMessage(R.string.dialog_levelachieved_description)
                    .setPositiveButton(R.string.dialog_levelachieved_btn_positive, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (lm.getLevelList().size() > (lm.getCurrentLevel() + 1)) {
                                lm.setCurrentLevel(lm.getCurrentLevel() + 1);
                            } else {
                                //user achieved last level
                                Log.e(TAG, "show: This should not happen. User got to last level and was able to click on nextLevel.");
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_levelachieved_btn_negative, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.startActivity(new Intent(activity, GameLevelHorizontalActivity.class));
                        }
                    })
                    .setCancelable(false);

            //Should we show positive btn (is this the last level?)
            if (lm.getLevelList().size() > (lm.getCurrentLevel() + 1)) {
                ld.setPositiveButton(R.string.dialog_levelachieved_btn_positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lm.setCurrentLevel(lm.getCurrentLevel() + 1);
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                });
            } //otherwise don't add it

            ld.show();
        } else {
            Log.e(TAG, "show: Could not show dialog.");
        }
    }
}
