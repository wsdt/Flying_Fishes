package yourowngame.com.yourowngame.classes.gamelevels;

import android.content.Context;
import android.support.annotation.NonNull;

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
public abstract class LevelAssignment {
    /** See below. But e.g.:
     * - 1500 (e.g. for 1500 points to achieve)
     * - 2 (e.g. for 2 meloon)*/
    private long amount;

    /** Following int should contain a string resource id. Which refers to a string which points
     * out what the user needs to do to achieve this assignment.
     *
     * Examples:
     * - "Achieve 1500 points." --> but in strings.xml please use a placeholder for numbers (instead of 1500 -> e.g. %1$d)
     * - "Gather 2 Meloons." --> but in strings.xml please use a placeholder for numbers (instead of 1500 -> e.g. %1$d)
     *
     * So if you want both assignments above in your levelObj, just create two LevelAssigmentObjs
     * and put them into your level.
     *
     * As you hopefully had read above you should NOT include numbers in your resourceString, as we do
     * not want to create multiple levelAssignmentClass in following cases.
     * - "Achieve 1500 points."
     * - "Achieve 3500 points."
     *
     * To prevent here two LevelAssigment children we just craft the resource string with a placeholder,
     * so we can put the exact amount we want into the top variable "amount" which will be placed within
     * our string. So we only have ONE LevelAssigment Object for ALL "Achieve x points." assignments. :)
     *
     * So do not use this resource string without providing the placeholder values. E.g.
     * - Bad: getResources().getString(getResString_whatToDo());
     * - Good: String.format(getResources().getString(getResString_whatToDo()),getAmount());*/
    private int resString_whatToDo;

    /** Following int should contain a drawable resource id. Which refers to a drawable which will
     * show the user a small icon representing the assignment.
     *
     * Examples:
     * - "Meloon Icon for all Meloon gathering assignments."
     * - "Black square with two white numbers in it which could represent all highscore related assignments."*/
    private int resDrawable_smallIcon;

    /** Use this method to determine whether user has achieved level assignment. If you need e.g.
     * a highscore obj for evaluating then just create a constructor and set the highscore obj
     * as member of that subclass.*/
    public abstract boolean isLevelAssignmentAchieved();


    /** Returns the specified amount nested in the specified resource String. */
    public String getFormattedAssignment(@NonNull Context context) {
        return String.format(context.getResources().getString(this.getResString_whatToDo()),this.getAmount());
    }

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public int getResDrawable_smallIcon() {
        return resDrawable_smallIcon;
    }

    public void setResDrawable_smallIcon(int resDrawable_smallIcon) {
        this.resDrawable_smallIcon = resDrawable_smallIcon;
    }

    public int getResString_whatToDo() {
        return resString_whatToDo;
    }

    public void setResString_whatToDo(int resString_whatToDo) {
        this.resString_whatToDo = resString_whatToDo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
