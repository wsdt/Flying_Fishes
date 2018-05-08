package yourowngame.com.yourowngame.classes.manager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.activities.HighscoreActivity;
import yourowngame.com.yourowngame.activities.LevelHierarchyActivity;
import yourowngame.com.yourowngame.classes.counters.FruitCounter;
import yourowngame.com.yourowngame.classes.counters.Highscore;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

import static android.support.constraint.Constraints.TAG;

/**
 *
 * So basically we're doing the following:
 *
 * Player succeed, now needs to decide whether GO ON, view the score or repeat the Level
 *
 * (1) wants to play next level
 * if he wants to play the next level, he will be send to the level hierarchy, the level  hierarchy
 * UNLOCKS (by animation) the next level and he can press the new level button
 *
 * (2) wants to view the highscore
 * if he wants to view the score, he will click on the highscore button, the highscore will be shown and he will
 * end up in the highscore activity
 *
 * (3) repeat the level
 * if he wants to repeat the Level, he will click the repeat button and the level will start again
 *
 * He also sees the highscore, and the fruits he collected, after the game ended, all fruits will be transfered to his
 * already collected fruits.
 *
 */


public class GameSuccessDialog extends Dialog implements View.OnClickListener {

    public final String TAG = "GameDialog";

    public GameSuccessDialog(@NonNull Context context, LevelManager manager) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.level_finish_dialog);

        /** player clicks next-Level-Btn */
        ImageButton nextLevelBtn   = (ImageButton) findViewById(R.id.dialogNextLevelBtn);
            nextLevelBtn.setOnClickListener(this);
        /** player clicks highScore-Btn */
        ImageButton highscoreBtn   = (ImageButton) findViewById(R.id.dialogHighscoreBtn);
            highscoreBtn.setOnClickListener(this);
        /** player clicks repeat-Level-btn */
        ImageButton repeatLevelBtn = (ImageButton) findViewById(R.id.dialogRepeatLevelBtn);
            repeatLevelBtn.setOnClickListener(this);

        /** title */
        TextView titleText = (TextView) findViewById(R.id.dialogSuccessTitle);
            titleText.setText(getContext().getResources().getString(R.string.level_achieve_txt, LevelManager.getInstance(getContext()).getCurrentLevel()));

        /** highscore*/
        TextView yourScore = (TextView) findViewById(R.id.yourScoreTxtView);
            yourScore.setText(getContext().getResources().getString(R.string.yourScore, LevelManager.getInstance(getContext()).getCurrentLevelObj().getCurrentLevelHighscore().getValue()));

        /** Amount of Meloons-Field */
        TextView amountMeloon = (TextView) findViewById(R.id.amountMeloonsTxtView);
            amountMeloon.setText(getContext().getResources().getString(R.string.equals, FruitCounter.getInstance().getLevelAmountMeloons()));
        /** Amount of Pinapos-Field */
        TextView amountPinapos = (TextView) findViewById(R.id.amountPinaposTxtView);
            amountPinapos.setText(getContext().getResources().getString(R.string.equals, FruitCounter.getInstance().getLevelAmountPinapos()));
        /** Amount of Avocis-Field */
        TextView amountAvocis = (TextView) findViewById(R.id.amountAvocisTxtView);
            amountAvocis.setText(getContext().getResources().getString(R.string.equals, FruitCounter.getInstance().getLevelAmountPinapos()));

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dialogNextLevelBtn:
                Log.d(TAG, "User wants to play the nextLevel");
                Intent toLevelActy = new Intent(getContext(), LevelHierarchyActivity.class);
                getContext().startActivity(toLevelActy);
                break;
            case R.id.dialogHighscoreBtn:
                Log.d(TAG, "User wants to view the scores");
                Intent toHighscoreActy = new Intent(getContext(), HighscoreActivity.class);
                getContext().startActivity(toHighscoreActy);
                break;
            case R.id.dialogRepeatLevelBtn:
                // and here, the level will just start again...
                break;
            default:
                Intent toLevelAct = new Intent(getContext(), LevelHierarchyActivity.class);
                getContext().startActivity(toLevelAct);
        }
    }
}
