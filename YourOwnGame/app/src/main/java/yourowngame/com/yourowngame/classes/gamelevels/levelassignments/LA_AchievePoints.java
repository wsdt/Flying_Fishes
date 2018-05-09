package yourowngame.com.yourowngame.classes.gamelevels.levelassignments;

import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.counters.Highscore;
import yourowngame.com.yourowngame.classes.gamelevels.LevelAssignment;

/** To achieve this levelAssignment the user has to reach a specific
 * amount of points (determined in "amount"-var).*/
public class LA_AchievePoints extends LevelAssignment {
    private Highscore highscore;

    public LA_AchievePoints(long amount, @NonNull Highscore highscore) {
        this.setAmount(amount);
        this.setResDrawable_smallIcon(R.drawable.app_icon_gameboy); //todo: change
        this.setResString_whatToDo(R.string.levelAssignments_achievePoints_whatToDo);
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
