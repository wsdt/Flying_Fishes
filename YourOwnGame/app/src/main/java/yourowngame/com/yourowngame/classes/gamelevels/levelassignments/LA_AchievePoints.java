package yourowngame.com.yourowngame.classes.gamelevels.levelassignments;

import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.counters.Highscore;
import yourowngame.com.yourowngame.classes.gamelevels.LevelAssignment;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

/** To achieve this levelAssignment the user has to reach a specific
 * amount of points (determined in "amount"-var).*/
public class LA_AchievePoints extends LevelAssignment {
    private Highscore highscore;

    public LA_AchievePoints(long amount, @NonNull Highscore highscore) {
        super(amount);
        this.setResDrawable_smallIcon(Constants.APP_ICON); //todo: change
        this.setResString(R.string.levelAssignments_achievePoints_whatToDo);
        this.setHighscore(highscore);
    }

    @Override
    public boolean isLevelAssignmentAchieved() {
        return (getHighscore().getValue() >= getAmount());
    }

    //GETTER/SETTER ------------------------------------------------------------
    public Highscore getHighscore() {
        return highscore;
    }

    public void setHighscore(Highscore highscore) {
        this.highscore = highscore;
    }
}
