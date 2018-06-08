package yourowngame.com.yourowngame.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

/**
 * PLEASE READ THIS
 *
 * This is just a simple test & a simple show for our testers, to indicate what we're implementing.
 *
 * Hab leider unendlich wenig Zeit kapt, der eine Nachmittag heute vorm release halt haha
 * würds aber sehr gerne miteinpacken, mit der nachricht dass des a testsurface is, nicht mehr, nicht weniger
 * auch codetechnisch is es natürlich unterste schublade haha
 *
 * aber wenn mir später mit arrays arbeiten und in index zu beginn auf 1 setzen, dann entspräche
 * jeder index eines button dem entsprechenden level! so können ma easy durchiterieren beim listener-adden sowie beim levelstart.
 *
 * Auch die xml an sich is a haufen dogshit aber mei haha
 *
 */



public class GameLevelHorizontalActivity extends AppCompatActivity {

    private LevelManager levelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_level_horizontal);

        OnClickHandler handler = new OnClickHandler();
        levelManager = new LevelManager(this);


        ImageButton level_01 = findViewById(R.id.lvl01);
                    level_01.setOnClickListener(handler);
        ImageButton level_02 = findViewById(R.id.lvl02);
                    level_02.setOnClickListener(handler);
        ImageButton level_03 = findViewById(R.id.lvl03);
                    level_03.setOnClickListener(handler);
        ImageButton level_04 = findViewById(R.id.lvl04);
                    level_04.setOnClickListener(handler);
        ImageButton level_05 = findViewById(R.id.lvl05);
                    level_05.setOnClickListener(handler);

    }

    private class OnClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //TODO i know, this is also utterly shit :) but that whole World-shit will be our next huge chapter, needs to be perfect
            switch(v.getId()){
                case R.id.lvl01:
                    levelManager.setCurrentLevel(0);
                    break;
                case R.id.lvl02:
                    levelManager.setCurrentLevel(1);
                    break;
                case R.id.lvl03:
                    levelManager.setCurrentLevel(2);
                    break;
                case R.id.lvl04:
                    levelManager.setCurrentLevel(3);
                    break;
                case R.id.lvl05:
                    levelManager.setCurrentLevel(4);
                    break;
            }

            startActivity(new Intent(GameLevelHorizontalActivity.this,GameViewActivity.class));
        }
    }


}
