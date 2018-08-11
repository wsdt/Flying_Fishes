package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.activities.WorldActivity;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.LevelAssignment;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.classes.manager.WorldMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;
import yourowngame.com.yourowngame.gameEngine.surfaces.WorldView;

/**
 * Creates a simple Dialog which holds the information for the chosen level
 * -> available fruits
 * -> level-Number
 * -> current best-highscore
 */
public class LevelInformationDialog {
    private static final String TAG = "LevelInformationDialog";

    private LevelInformationDialog() {
    }

    //Only worldView!
    public static void show(@NonNull final WorldView worldView, final int lvlIndex) {
        final DrawableSurfaceActivity activity = worldView.getDrawableSurfaceActivity();

        if (!activity.isFinishing()) {
            //Pause/Stop thread
            worldView.getThread().pauseThread();

            //inflate for imageView visibility
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater == null) {
                Log.w(TAG, "show: Could not show levelInfo dialog. Starting lvl instead.");
                startLvl(activity, null, lvlIndex);
            } else {
                RelativeLayout inflatedDialog = (RelativeLayout) inflater.inflate(R.layout.dialog_level_information, null);

                final LovelyCustomDialog infoDialog = new LovelyCustomDialog(activity)
                        .setTopTitle(R.string.dialog_levelinformation_toptitle)
                        .setTopColorRes(R.color.colorPrimaryDark)
                        .setTopTitleColor(activity.getResources().getColor(R.color.colorWhite))
                        .setView(inflatedDialog);

                infoDialog.setListener(R.id.dialogLvlInformationBtnStart, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startLvl(activity, infoDialog, lvlIndex);
                    }
                });
                infoDialog.setListener(R.id.dialogLvlInformationBtnCancel, new View.OnClickListener() {
                    @Bug(byDeveloper = Constants.Developers.SOLUTION,
                            message = "The dialog stops our thread and our animation in the background," +
                                    "our thread is getting resumed, but somehow effects are dead.",
                            possibleSolution = "(1) restart Activity, but that is the worst approach, bc our background would never run fluently," +
                                    "(2) the thread needs to be resumed, but does not take effect.")
                    @Override
                    public void onClick(View view) {
                        infoDialog.dismiss();
                        activity.startActivity(new Intent(activity, WorldActivity.class));
                    }
                });

                //loadDataToDialog dialog-data
                loadDataToDialog(activity, inflatedDialog, lvlIndex);

                //show the dialog
                infoDialog.show();
            }
        } else {
            Log.d(TAG, "show: Whups, couldnt show dialog!");
        }
    }

    private static void startLvl(@NonNull Activity activity, @Nullable LovelyCustomDialog infoDialog, int lvlIndex) {
        WorldMgr.setCurr_lvl_index(lvlIndex);
        if (infoDialog != null) {
            infoDialog.dismiss();
        }
        activity.startActivity(new Intent(activity, GameViewActivity.class));
    }

    /**
     * Load lvl data into dialog.
     */
    @Enhance(message = "Improve performance and legibility", byDeveloper = Constants.Developers.WSDT,
            priority = Enhance.Priority.MEDIUM)
    private static void loadDataToDialog(@NonNull DrawableSurfaceActivity activity, @NonNull RelativeLayout dialogParent, int lvlIndex) {
        Resources r = dialogParent.getResources();
        WorldMgr.setCurr_lvl_index(lvlIndex);
        DrawableLevel currLvl = WorldMgr.getCurrLvl(activity,true); //force bc. we changed no.
        Log.d(TAG, "loadDataToDialog: Loaded lvl -> "+currLvl);

        //Set title
        ((TextView) dialogParent.findViewById(R.id.dialogLvlInformationTitle)).setText(
                r.getString(R.string.dialog_levelinformation_title, lvlIndex,
                        (currLvl instanceof Level) ? ((Level) currLvl).getLevelName() : R.string.appName));

        //Set fruits
        HashSet<Class> differentFruits = new HashSet<>();
        for (Fruit f : currLvl.getFruits()) {
            differentFruits.add(f.getClass()); //simply add (hashset only accepts distinct values)
        }

        //TODO: Stop initializing fruits as this is the source of errors
        GridLayout gridLayout = dialogParent.findViewById(R.id.dialogLvlInformationFruits);
        for (Class f : differentFruits) {
            ImageView iv = new ImageView(activity);

            Fruit fruit = FruitMgr.createRandomFruit(activity, currLvl, f);
            if (fruit != null) {
                fruit.initialize(); //for bitmap
                Bitmap loadedBitmap = fruit.getCurrentBitmap();

                iv.setImageBitmap(Bitmap.createScaledBitmap(loadedBitmap, loadedBitmap.getWidth() / 2, loadedBitmap.getHeight() / 2, true));
                gridLayout.addView(iv);
                Log.d(TAG, "loadDataToDialog:SetFruits: Loaded fruit->" + f);
            } else {
                Log.e(TAG, "loadDataToDialog:SetFruits: Could not generate fruit -> " + f);
            }
        }


        //Set assigments
        if (currLvl instanceof Level) {
            TextView laView = dialogParent.findViewById(R.id.dialogLvlInformationAssignments);
            for (LevelAssignment la : ((Level) currLvl).getAllLevelAssignments()) {
                laView.append(la.getFormatted(activity) + "\n");
            }
        } else {
            Log.w(TAG, "loadDataToDialog: Didn't list levelAssignments as we have no LevelObj here.");
        }
    }
}
