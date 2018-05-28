package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.LevelHierarchyActivity;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;

/** Shown when user presses pause btn during game. */
public class PauseGameDialog {
    private static final String TAG = "PauseGameDialog";

    public static void show(@NonNull final GameView gameView) {
        final Activity activity = gameView.getActivityContext();

        //To prevent badTokenExceptions
        if (!activity.isFinishing()) {
            new LovelyStandardDialog(activity)
                    .setTopColorRes(R.color.colorPrimaryDark)
                    .setIcon(Constants.APP_ICON)
                    .setTitle(R.string.dialog_pausegame_title)
                    .setMessage(R.string.dialog_pausegame_description)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_pausegame_btn_positive, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //New thread needed to get the outside dialog closed. 
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    gameView.getThread().resumeGame();
                                }
                            }).start();
                        }
                    })
                    .setNegativeButton(R.string.dialog_pausegame_btn_negative, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gameView.exitGameNow();
                            activity.startActivity(new Intent(activity, LevelHierarchyActivity.class));
                        }
                    })
                    .show();
        } else {
            Log.e(TAG, "show: Could not show dialog.");
        }
    }

}
