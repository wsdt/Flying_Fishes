package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.text.NumberFormat;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.activities.WorldActivity;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.manager.WorldMgr;
import yourowngame.com.yourowngame.gameEngine.CanvasDrawThread;
import yourowngame.com.yourowngame.gameEngine.surfaces.WorldView;

/**
 * Creates a simple Dialog which holds the information for the chosen level
 * -> available fruits
 * -> level-Number
 * -> current best-highscore
 */
public class LevelInformationDialog {
    private static final String TAG = "LevelInformationDialog";
    private static int levelIndex;

    public static void show(@NonNull final WorldView worldView, int lvlindex){
        final Activity activity = worldView.getDrawableSurfaceActivity();
        levelIndex = lvlindex;

        if(!activity.isFinishing()) {
            //inflate for imageView visibility
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater == null) {
                Log.w(TAG, "show: Could not show levelInfo dialog. Starting lvl instead.");

            } else {
                View view = inflater.inflate(R.layout.dialog_level_information, null);

                final LovelyCustomDialog infoDialog = new LovelyCustomDialog(activity);
                infoDialog.setView(view);
                infoDialog.setListener(R.id.startLevelButton, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startLvl(activity,infoDialog);
                    }
                });
                infoDialog.setListener(R.id.cancelLevelButton, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //activity.startActivity(new Intent(activity, WorldActivity.class));
                        infoDialog.dismiss();
                        worldView.getThread().resumeThread(); //resume world animations
                    }
                });

                //initiate dialog-data
                initiate(view, activity);

                //show the dialog
                infoDialog.show();
            }
        }else{
            Log.d(TAG, "show: Whups, couldnt show dialog!");
        }
    }

    private static void startLvl(@NonNull Activity activity, @NonNull LovelyCustomDialog infoDialog) {
        WorldMgr.setCurr_lvl_index(levelIndex);
        infoDialog.dismiss();
        activity.startActivity(new Intent(activity, GameViewActivity.class));
    }

    //init dialog data
    private static void initiate(View view, Activity activity){
        ImageView meloon = (ImageView)  view.findViewById(R.id.meloonAvailable);
        ImageView avoci  = (ImageView)  view.findViewById(R.id.avociAvailable);
        ImageView pinapo = (ImageView)  view.findViewById(R.id.pinapoAvailable);


        Level lvlobj = WorldMgr.getCurrLvlObj(activity);

        //unhappy with that solution!
        for (int i = 0; i < lvlobj.getAllFruits().size(); i++){
            if(lvlobj.getAllFruits().get(i) instanceof Fruit_Meloon){
                meloon.setVisibility(View.VISIBLE);
            }else if(lvlobj.getAllFruits().get(i) instanceof Fruit_Avoci){
                avoci.setVisibility(View.VISIBLE);
            }else if(lvlobj.getAllFruits().get(i) instanceof Fruit_Pinapo){
                pinapo.setVisibility(View.VISIBLE);
            }
        }

        //we could also display the enemies, which will be in that level.

        //level number
        TextView levelNumber = view.findViewById(R.id.levelName);
        levelNumber.setText(activity.getResources().getString(R.string.level_name, levelIndex+1));

        //Points to achieve this level
        TextView pointsToAchieve = view.findViewById(R.id.pointsToAchieve);
        //using local-specific separator
        String formattedAmount = NumberFormat.getIntegerInstance().format(lvlobj.getAllLevelAssignments().get(0).getAmount());
        pointsToAchieve.setText(activity.getResources().getString(R.string.points_to_achieve, formattedAmount));


    }
}
