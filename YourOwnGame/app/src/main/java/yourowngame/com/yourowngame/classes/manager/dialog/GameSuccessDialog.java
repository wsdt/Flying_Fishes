package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
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

    /** Only package private (= NO zugriffsmodifier (neq protected)) constructor so it is
     * only called from dialogMgr. */
    GameSuccessDialog(@NonNull Activity activity) {
        super(activity);
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
            titleText.setText(getContext().getResources().getString(R.string.level_achieve_txt, LevelManager.getInstance((Activity) getContext()).getCurrentLevel()));

        /** highscore*/
        TextView yourScore = (TextView) findViewById(R.id.yourScoreTxtView);
            yourScore.setText(getContext().getResources().getString(R.string.yourScore, LevelManager.getInstance((Activity) getContext()).getCurrentLevelObj().getCurrentLevelHighscore().getValue()));

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
                dismiss();

                //remove old level
                LevelManager.getInstance((Activity) this.getContext()).getCurrentLevelObj().cleanUpLevelProperties();

                //TODO: Maybe just start directly new level (before redirecting back to levelhierarchy [usability])
                getContext().startActivity(new Intent(getContext(), LevelHierarchyActivity.class));
                break;
            case R.id.dialogHighscoreBtn:
                Log.d(TAG, "User wants to view the scores");
                dismiss();

                //remove old level
                LevelManager.getInstance((Activity) this.getContext()).getCurrentLevelObj().cleanUpLevelProperties();

                getContext().startActivity(new Intent(getContext(), HighscoreActivity.class));
                break;
            case R.id.dialogRepeatLevelBtn:
                // and here, the level will just start again...

                //remove old level
                LevelManager.getInstance((Activity) this.getContext()).getCurrentLevelObj().cleanUpLevelProperties();

                break;
            //TODO: maybe also add a revive btn (watching an ad, or paying some fruits or similar) [here we would skip the cleanUpLevelProperties()]
            default:
                dismiss();
                getContext().startActivity(new Intent(getContext(), LevelHierarchyActivity.class));
        }
    }
}
