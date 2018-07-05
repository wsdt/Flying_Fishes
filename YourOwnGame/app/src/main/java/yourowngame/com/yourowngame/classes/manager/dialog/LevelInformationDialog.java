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
import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Fruit_Pinapo;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.WorldManager;
import yourowngame.com.yourowngame.gameEngine.surfaces.WorldView;

/**
 * Creates a simple Dialog which holds the information for the choosen level
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
            View view = inflater.inflate(R.layout.dialog_level_information, null);

            final LovelyCustomDialog infoDialog = new LovelyCustomDialog(activity);
            infoDialog.setView(view);
            infoDialog.setListener(R.id.startLevelButton, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WorldManager.setCurr_lvl_index(levelIndex);
                    activity.startActivity(new Intent(activity, GameViewActivity.class));
                }
            });
            infoDialog.setListener(R.id.cancelLevelButton, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    infoDialog.dismiss();
                }
            });

            //initiate dialog-data
            initiate(view, activity);

            //show the dialog
            infoDialog.show();
        }else{
            Log.d(TAG, "Whups, couldnt show dialog!");
        }
    }

    //init dialog data
    public static void initiate(View view, Activity activity){
        ImageView meloon = (ImageView)  view.findViewById(R.id.meloonAvailable);
        ImageView avoci  = (ImageView)  view.findViewById(R.id.avociAvailable);
        ImageView pinapo = (ImageView)  view.findViewById(R.id.pinapoAvailable);

        Level lvlobj = WorldManager.getCurrLvlObj(activity);

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

        //level number
        TextView levelNumber = view.findViewById(R.id.levelName);
        levelNumber.setText(activity.getResources().getString(R.string.level_name, levelIndex +1));

        //level score (later on -> best score could be shown!)
        TextView highscore = view.findViewById(R.id.YourScore);
        highscore.setText(activity.getResources().getString(R.string.your_highscore, "N/V"));
    }
}
