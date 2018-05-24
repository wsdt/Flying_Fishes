package yourowngame.com.yourowngame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.annotations.Idea;
import yourowngame.com.yourowngame.classes.commercial.AdManager;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelAssignment;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

@Enhance (message = "All methods here are terrible, I just implemented them like that so we have a simple" +
        "levelHierarchy that works.")
public class LevelHierarchyActivity extends AppCompatActivity {
    private static final String TAG = "LevelHierarchyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_hierarchy);
        getGameDimens();

        //Load Banner Ad (declared as a member of class, so we could easily display more)
        new AdManager(this).loadBannerAd((RelativeLayout) findViewById(R.id.levelHierarchyActivity_RL));

        /*List all levels, to show user sth. on this activity.*/
        listAllLevels();
    }

    public void getGameDimens() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GameViewActivity.GAME_WIDTH = displayMetrics.widthPixels;
        GameViewActivity.GAME_HEIGHT = displayMetrics.heightPixels;
        Log.d(TAG, "HEIGHT = " + GameViewActivity.GAME_HEIGHT + "WIDTH = " + GameViewActivity.GAME_WIDTH);
    }

    private void listAllLevels() {
        try {
            GridLayout gridLayout = (GridLayout) findViewById(R.id.glLevelHierarchy);

            int lvlId = 0; //TODO: should be only temporary (maybe we just skip this after we have introduced our multiple world semantics)
            for (Level level : LevelManager.getInstance(this).getLevelList()) {
                listLevel(gridLayout, level,lvlId++);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "listAllLevels: Tried to inflate LevelView or/and LevelHierarchyView, but it seems not to be a Relativelayout resp. GridLayout!");
            e.printStackTrace();
        }
        Log.d(TAG, "listAllLevels: Tried to list all available levels.");
    }

    private void listLevel(@NonNull GridLayout parentView, @NonNull Level level, final int lvlId) throws ClassCastException {
        RelativeLayout inflatedLevelView = (RelativeLayout) getLayoutInflater().inflate(R.layout.levelhierarchyactivity_level_info, parentView, false); //give parentView so layoutparams are set (attachToRoot so simultaneously added)
        inflatedLevelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Bad practice
                LevelManager.getInstance(LevelHierarchyActivity.this).setCurrentLevel(lvlId);
                startActivity(new Intent(LevelHierarchyActivity.this,GameViewActivity.class));
            }
        });


        /*Put level specific params into inflated view*/
        ((TextView) inflatedLevelView.findViewById(R.id.lvlName)).setText(getResources().getString(level.getLevelNameResId()));
        ((TextView) inflatedLevelView.findViewById(R.id.lvlId)).setText(String.valueOf(lvlId));

        //Also print levelAssignments
        listLevelAssignments(inflatedLevelView, level);

        parentView.addView(inflatedLevelView);
        Log.d(TAG, "listLevel: Tried to add level to levelhierarchy.");
    }

    @Idea (priority = Idea.Priority.LOW,
            idea = "What if levelAssignments can be obligatory OR free-to-do (to earn additional fruits etc.)")
    private void listLevelAssignments(@NonNull RelativeLayout inflatedLevelView, @NonNull Level level) {
        //Currently only settting text of a textview (in future make layoutInflater and custom element for assignments)
        //maybe also mark them as optional or obligatory

        StringBuilder sb = new StringBuilder();
        for (LevelAssignment levelAssignment : level.getAllLevelAssignments()) {
            sb.append(levelAssignment.getFormattedAssignment(this));
        }

        ((TextView) inflatedLevelView.findViewById(R.id.lvlAssignments)).setText(sb);
    }
}
