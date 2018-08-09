package yourowngame.com.yourowngame.classes.manager.dialog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.activities.WorldActivity;
import yourowngame.com.yourowngame.classes.annotations.Enhance;

import yourowngame.com.yourowngame.classes.manager.WorldMgr;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

public class LevelAchievedDialog {
    private static final String TAG = "LevelAchievedDialog";

    private LevelAchievedDialog() {}

    @Enhance(byDeveloper = "Solution49", message = "Guess we should make a short summary after every level & what he has achieved!")
    public static void show(@NonNull final GameView gameView) {
        final GameViewActivity activity = (GameViewActivity) gameView.getDrawableSurfaceActivity();

        //To prevent badTokenExceptions
        if (!activity.isFinishing()) {
            LovelyStandardDialog ld = new LovelyStandardDialog(activity)
                    .setTopColorRes(R.color.colorPrimaryDark)
                    .setIcon(Constants.DEFAULT_ICON)
                    .setTitle(R.string.dialog_levelachieved_title)
                    .setMessage(R.string.dialog_levelachieved_description)
                    .setNegativeButton(R.string.dialog_levelachieved_btn_negative, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.startActivity(new Intent(activity, WorldActivity.class));
                        }
                    })
                    .setCancelable(false);

            //Should we show positive btn (is this the last level?)
            if (WorldMgr.getCurrWorldObj(activity).getAllLevels().size() > (WorldMgr.getCurr_lvl_index() + 1)) {
                ld.setPositiveButton(R.string.dialog_levelachieved_btn_positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WorldMgr.setNextLvl(activity);
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
