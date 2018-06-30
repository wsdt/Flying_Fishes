package yourowngame.com.yourowngame.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import yourowngame.com.yourowngame.classes.annotations.Enhance;

/** Dummy activity without layout file for DrawableSurfaces.class */
public abstract class DrawableSurfaceActivity extends AppCompatActivity {
    private static final String TAG = "DrawableSurfaceActivity";
    public static int GAME_HEIGHT;
    public static int GAME_WIDTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGameDimens();
    }

    @Deprecated
    @Enhance(byDeveloper = "Solution",
            message = "I wanna keep it that way, the GameViewActivity should provide the metrics," +
                    "so the levelHierarchy will deliver it! See @LevelHierarchyActivity.java",
            priority = Enhance.Priority.LOW)
    public void getGameDimens() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GAME_WIDTH = displayMetrics.widthPixels;
        GAME_HEIGHT = displayMetrics.heightPixels;
        Log.d(TAG, "HEIGHT = " + GAME_HEIGHT + "\nWIDTH = " + GAME_WIDTH);
    }
}
