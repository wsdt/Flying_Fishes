package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.commercial.AdManager;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.classes.manager.interfaces.ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

/** Shown when user dies before completing the level. */
public class GameOverDialog {
    private static final String TAG = "GameOverDialog";

    public static void show(@NonNull final GameView gameView) {
        final Activity activity = gameView.getDrawableSurfaceActivity();

        //To prevent badTokenExceptions
        if (!activity.isFinishing()) {
            new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                    .setTopColorRes(R.color.colorPrimaryDark)
                    .setIcon(Constants.APP_ICON)
                    .setTitle(R.string.dialog_gameover_title)
                    .setMessage(R.string.dialog_gameover_description)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_gameover_btn_positive, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AdManager(gameView.getDrawableSurfaceActivity()).loadRewardedVideoInRewardActivity(
                                    gameView.getDrawableSurfaceActivity(), new ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation() {
                                        @Override
                                        public void success_is_true() {
                                            //TODO: put here revive method/procedure (e.g. put all positions a few seconds back!)
                                        }

                                        @Override
                                        public void failure_is_false() {
                                            //don't revive so just do nothing, because rewarded ad does this for us (but to restart game do success from outer interface)
                                            gameView.exitNow(); //sozusagen doch kein revive
                                        }
                                    }, null //don't change activity, because user want to be revived!
                            );
                        }
                    })
                    .setNegativeButton(R.string.dialog_gameover_btn_negative, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gameView.exitNow();
                        }
                    })
                    .show();
        } else {
            Log.e(TAG, "show: Could not show dialog.");
        }
    }

}
