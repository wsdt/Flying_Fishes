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

    @Override
    protected void onPause() {
        super.onPause();

    }

    //GETTER/SETTER +++++++++++++++++++++++++++++++++++++
    public WorldView getWorldView() {
        return worldView;
    }

    public void setWorldView(WorldView worldView) {
        this.worldView = worldView;
    }
}
