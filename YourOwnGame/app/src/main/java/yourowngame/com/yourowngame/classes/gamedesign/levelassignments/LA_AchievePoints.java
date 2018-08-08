package yourowngame.com.yourowngame.classes.gamedesign.levelassignments;

import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.counters.HighScore;
import yourowngame.com.yourowngame.classes.gamedesign.LevelAssignment;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

/** To achieve this levelAssignment the user has to reach a specific
 * amount of points (determined in "amount"-var).*/
public class LA_AchievePoints extends LevelAssignment {
    private HighScore highScore;

    public LA_AchievePoints(long amount, @NonNull HighScore highScore) {
        super(amount);
        this.setResDrawable_smallIcon(Constants.DEFAULT_ICON); //todo: change
        this.setResString(R.string.levelAssignments_achievePoints_whatToDo);
        this.setHighScore(highScore);
    }

    @Override
    public boolean isLevelAssignmentAchieved() {
        return (getHighScore().getValue() >= getAmount());
    }

    //GETTER/SETTER ------------------------------------------------------------
    public HighScore getHighScore() {
        return highScore;
    }

    public void setHighScore(HighScore highScore) {
        this.highScore = highScore;
    }
}
