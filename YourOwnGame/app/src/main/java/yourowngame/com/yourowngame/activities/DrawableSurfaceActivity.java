package yourowngame.com.yourowngame.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/** Dummy activity without layout file for DrawableSurfaces.class */
public abstract class DrawableSurfaceActivity extends AppCompatActivity {
    private static final String TAG = "DrawableSurfaceActivity";
    /** To access drawable surface among several xmls. */
    public static int DRAWABLE_SURFACE_ID = R.id.drawableSurfaceActivity_surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setInitialGameDim();
    }

    public void setInitialGameDim() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        DrawableSurfaces.setDrawWidth(displayMetrics.widthPixels);
        DrawableSurfaces.setDrawHeight(displayMetrics.heightPixels);

        Log.d(TAG, "Initial height = " + DrawableSurfaces.getDrawHeight() + "\nInitial width = " + DrawableSurfaces.getDrawWidth());
    }

}
