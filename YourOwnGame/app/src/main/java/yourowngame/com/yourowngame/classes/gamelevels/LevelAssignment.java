package yourowngame.com.yourowngame.classes.gamelevels;

import android.content.Context;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.Feature;

/** Extend from this class to create levelAssigments for levels.
 *
 * To set all values please use a simple constructor (if possible). Also avoid Context as class
 * member if possible. This could keep things more simple. E.g.:
 *
 * public AchievePoints(long amount) {
 *     this.setAmount(1500);
 *     this.setResString_whatToDo(R.string.levelassignments_achievePoints_whattodo);
 *     this.setResDrawable(R.drawable.levelassigments_achievePoints_smallIcon);
 * }
 *
 * If you need e.g. also the highscore obj just put it also into the constructor and add the Highscore
 * obj in the subclass as member (see AchievePoints).
 * */
public abstract class LevelAssignment extends Feature {
    public LevelAssignment(long amount) {
        super(amount);
    }

    /** Use this method to determine whether user has achieved level assignment. If you need e.g.
     * a highscore obj for evaluating then just create a constructor and set the highscore obj
     * as member of that subclass.*/
    public abstract boolean isLevelAssignmentAchieved();
}
