package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.LevelHierarchyActivity;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

public class LevelAchievedDialog {
    private static final String TAG = "LevelAchievedDialog";

    public static void show(@NonNull final Activity activity) {
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
                            if (lm.getLevelList().size() > (lm.getCurrentLevel()+1)) {
                                lm.setCurrentLevel(lm.getCurrentLevel() + 1);
                            } else {
                                //user achieved last level
                                Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_levelachieved_btn_negative, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.startActivity(new Intent(activity, LevelHierarchyActivity.class));
                        }
                    })
                    .setCancelable(false);

            //Should we show positive btn (is this the last level?)
            if (lm.getLevelList().size() > (lm.getCurrentLevel()+1)) {
                ld.setPositiveButton(R.string.dialog_levelachieved_btn_positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lm.setCurrentLevel(lm.getCurrentLevel()+1);
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