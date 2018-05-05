package yourowngame.com.yourowngame.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.commercial.AdManager;
import yourowngame.com.yourowngame.classes.manager.HelperClass;

/**
 *
 *
 * Welcomes the user
 *
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load Banner Ad (declared as a member of class, so we could easily display more)
        //new AdManager(this).loadBannerAd((RelativeLayout) findViewById(R.id.mainActivity_RL));


        //Hide actionbar if it is not already hidden
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //Show current app version to user
        ((TextView) findViewById(R.id.mainActivity_appVersion)).setText(HelperClass.getAppVersion(this,true));
    }

    //GAME BUTTONS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void startAdventureMode(View view) {
        startActivity(new Intent(this, LevelHierarchyActivity.class));
    }

    @Deprecated
    //TODO: Make for each mode an own method
    public void startGame(View view) {
        startActivity(new Intent(this, GameViewActivity.class));
    }

    public void showHighscore(View view) {
        startActivity(new Intent(this, HighscoreActivity.class));
    }
}
