package yourowngame.com.yourowngame.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import yourowngame.com.yourowngame.R;
//import yourowngame.com.yourowngame.gameview.gameview;


public class GameViewActivity extends AppCompatActivity {
  //  private gameview gamingViewPanel;
    private RelativeLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

       // gamingViewPanel = new gameview(this);
        gameLayout = (RelativeLayout) findViewById(R.id.gameViewLayout);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 750);
        /*gamingViewPanel.setLayoutParams(params);

        gamingViewPanel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        gameLayout.addView(gamingViewPanel);*/
    }
}


