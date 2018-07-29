package yourowngame.com.yourowngame.activities;

import android.os.Bundle;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.gameEngine.surfaces.WorldView;

public class WorldActivity extends DrawableSurfaceActivity {
    private WorldView worldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        this.setWorldView((WorldView) findViewById(R.id.worldActivity_worldView));
        this.getWorldView().startWorldAnimations(this);
    }

    /** Stop animations when activity isn't shown. */
    @Override
    protected void onStop() { //not onPause, bc. on exiting activity and onStart also onPause is called!
        super.onStop();
        this.getWorldView().getThread().pauseThread();
    } //do not make onResume(), bc. dialog should be shown and game should only resume, when resume is clicked and not automatically.

    //GETTER/SETTER +++++++++++++++++++++++++++++++++++++
    public WorldView getWorldView() {
        return worldView;
    }

    public void setWorldView(WorldView worldView) {
        this.worldView = worldView;
    }
}
