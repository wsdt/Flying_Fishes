package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.classes.manager.interfaces.ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation;

/** Shown when user dies before completing the level. */
public class GameOverDialog {
    private static final String TAG = "GameOverDialog";

    public static void show(@NonNull Activity activity, @NonNull View.OnClickListener onReviveBtn, @NonNull View.OnClickListener onExitBtn) {
        //To prevent badTokenExceptions
        if (!activity.isFinishing()) {
            new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                    .setTopColorRes(R.color.colorPrimaryDark)
                    .setIcon(Constants.APP_ICON)
                    .setTitle(R.string.dialog_gameover_title)
                    .setMessage(R.string.dialog_gameover_description)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_gameover_btn_positive, onReviveBtn)
                    .setNegativeButton(R.string.dialog_gameover_btn_negative, onExitBtn)
                    .show();
        } else {
            Log.e(TAG, "show: Could not show dialog.");
        }
    }

}
