package yourowngame.com.yourowngame.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

public class LevelHierarchyActivity extends AppCompatActivity {
    private static final String TAG = "LevelHierarchyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_hierarchy);

        /*List all levels, to show user sth. on this activity.*/
        listAllLevels();
    }

    private void listAllLevels() {
        try {
            GridLayout gridLayout = (GridLayout) findViewById(R.id.glLevelHierarchy);

            for (Level level : LevelManager.getInstance(this).getLevelList()) {
                listLevel(gridLayout, level);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "listAllLevels: Tried to inflate LevelView or/and LevelHierarchyView, but it seems not to be a Relativelayout resp. GridLayout!");
            e.printStackTrace();
        }
        Log.d(TAG, "listAllLevels: Tried to list all available levels.");
    }

    private void listLevel(@NonNull GridLayout parentView, @NonNull Level level) throws ClassCastException {
        RelativeLayout inflatedLevelView = (RelativeLayout) getLayoutInflater().inflate(R.layout.levelhierarchyactivity_level_info, parentView, false); //give parentView so layoutparams are set (attachToRoot so simultaneously added)

        /*Put level specific params into inflated view*/
        ((TextView) inflatedLevelView.findViewById(R.id.lvlName)).setText(getResources().getString(level.getLevelNameResId()));

        parentView.addView(inflatedLevelView);
    }
}
