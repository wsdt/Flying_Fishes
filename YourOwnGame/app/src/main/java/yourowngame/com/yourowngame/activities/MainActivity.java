package yourowngame.com.yourowngame.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.manager.AdManager;
import yourowngame.com.yourowngame.classes.manager.MetaDataMgr;

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
        AdManager.loadBannerAd(this,(RelativeLayout) findViewById(R.id.mainActivity_RL));


        //Show current app version to user
        ((TextView) findViewById(R.id.mainActivity_appVersion)).setText(MetaDataMgr.getAppVersion(this,true));
    }

    //GAME BUTTONS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void startAdventureMode(View view) {
        startActivity(new Intent(this, WorldActivity.class));
    }

    public void startSurvivalMode(View view) {
        startActivity(new Intent(this, GameViewActivity.class));
    }

    public void showHighscore(View view) {
        startActivity(new Intent(this, HighscoreActivity.class));
    }


}
