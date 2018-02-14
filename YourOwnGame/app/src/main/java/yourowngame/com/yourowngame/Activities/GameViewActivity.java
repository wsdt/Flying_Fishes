package yourowngame.com.yourowngame.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import yourowngame.com.yourowngame.GameView.GameView;
import yourowngame.com.yourowngame.R;

public class GameViewActivity extends AppCompatActivity {


    private GameView gamingViewPanel;
    private RelativeLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        gamingViewPanel = new GameView(this);
        gameLayout = (RelativeLayout) findViewById(R.id.gameViewLayout);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 750);
        gamingViewPanel.setLayoutParams(params);

        gamingViewPanel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        gameLayout.addView(gamingViewPanel);
    }
}


