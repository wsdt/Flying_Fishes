package yourowngame.com.yourowngame.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.handler.AdManager;

/**
 *
 *
 * Welcomes the user
 *
 */

public class MainActivity extends AppCompatActivity {
    private AdManager adManager;
    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load Banner Ad (declared as a member of class, so we could easily display more)
        this.setAdManager(new AdManager(this));
        this.getAdManager().loadBannerAd((RelativeLayout) findViewById(R.id.mainActivity_RL));

        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GameViewActivity.class));
            }
        });

    }


    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public AdManager getAdManager() {
        return this.adManager;
    }

    public void setAdManager(AdManager adManager) {
        this.adManager = adManager;
    }
}
