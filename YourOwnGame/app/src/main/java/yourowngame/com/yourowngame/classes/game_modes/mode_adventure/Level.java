package yourowngame.com.yourowngame.classes.game_modes.mode_adventure;


import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.manager.dialog.LevelAchievedDialog;
import yourowngame.com.yourowngame.classes.observer.interfaces.IHighscore_Observer;

public abstract class Level extends DrawableLevel {
    private static final String TAG = "Level";
    private int levelNameResId; //Level name (maybe to show to user [e.g. Die dunkle Gruft, usw.] als Strings.xml res id for multilinguality!
    /** Where on the superior world map is the level located? (x,y) */
    private Point worldMapPosition;
    private ArrayList<LevelAssignment> allLevelAssignments;

    //Do not make more constructors
    public Level(@NonNull DrawableSurfaceActivity activity, @NonNull Point worldMapPosition) {
        super(activity);

        Log.d(TAG, "Level: ###################### STARTING LOADING LEVEL ###############################");
        this.setDrawableSurfaceActivity(activity);
        this.setWorldMapPosition(worldMapPosition);

        //TODO: Maybe get rid of this method, but surely make lvlInformation statically accessible.
        this.determineMetaData();


        /* IMPORTANT: DO NOT ADD HERE DETERMINE_OBJ METHODS, WE ONLY APPEND ENEMIES ETC.
        * WHEN NEEDED AND NOT AT LVLOBJ_CREATION. THIS HAS THE ADVANTAGE THAT THE LVL_OBJ
        * AND SUPERIOR WORLD_OBJS REMAIN SMALL UNLESS WE NEED A SPECIFIC LVL OBJECT (WHEN
        * WE PLAY IT). */
        Log.d(TAG, "Level: ###################### ENDED LOADING LEVEL ##################################");
    }

    @Override
    public void initiate() {
        super.initiate();
        getLevelHighscore().addListener(new IHighscore_Observer() {
            @Override
            public void onHighscoreChanged() {
                /*Evaluate whether user achieved level or not. */
                if (Level.this.areLevelAssignmentsAchieved()) {
                    ((GameViewActivity) Level.this.getDrawableSurfaceActivity()).getGameView().getThread().setRunning(false);
                    Level.this.getDrawableSurfaceActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LevelAchievedDialog.show(((GameViewActivity) Level.this.getDrawableSurfaceActivity()).getGameView());
                        }
                    });
                }
            }
        });
    }

    /** Regardless of shop system keep this level dependent, so user can decide on lvl start which
     * player to use. */
    protected abstract void determinePlayer();
    protected abstract void determineBackgroundLayers();
    protected abstract void determineAllEnemies();
    protected abstract void determineAllFruits();
    protected abstract void playBackgroundMusic();
    /** Put all wanted LevelAssigment Objs in there. These will be accessed and evaluated in areLevelAssigmentsAchieved().*/
    protected abstract void determineLevelAssigments();
    /** Defines default data (normally this method does not contain any logic* operations). E.g. setting the levelName by getting it from the strings.xml*/
    protected abstract void determineMetaData();

    /** Check whether the assignments are achieved, or not. Every Level implements their assignments itself! */
    public boolean areLevelAssignmentsAchieved() {
        boolean isLevelAchieved = false;
        for (LevelAssignment levelAssignment : this.getAllLevelAssignments()) {
            if (!levelAssignment.isLevelAssignmentAchieved()) {
                return false;
            } else {
                /*if assignment not false put it to true, as long as no false one is found the method will return true
                 So method also returns false, if no levelAssigments were specified.*/
                isLevelAchieved = true;
            }
        }
        return isLevelAchieved;
    }

    //GETTER/SETTER +++++++++++++++++++++++++++++++++++++++++++++++++

    public String getLevelName(){
        return getDrawableSurfaceActivity().getResources().getString(getLevelNameResId());
    }

    @Override
    public ArrayList<Background> getBgLayers() {
        if (super.getBgLayers() == null || super.getBgLayers().size() <= 0) {
            this.determineBackgroundLayers();
            for (Background background : super.getBgLayers()) {background.initialize();}
        }
        return super.getBgLayers();
    }

    public int getLevelNameResId() {
        return levelNameResId;
    }
    public void setLevelNameResId(@StringRes int levelNameResId) {
        this.levelNameResId = levelNameResId;
    }
    @Override
    public ArrayList<Enemy> getEnemies() {
        if (super.getEnemies() == null || super.getEnemies().size() <= 0) {
            this.determineAllEnemies();
            for (Enemy enemy : super.getEnemies()) {enemy.initialize();}
        }
        return super.getEnemies();
    }
    @Override
    public Player getPlayer() {
        if (super.getPlayer() == null) {
            this.determinePlayer();
            this.getPlayer().initialize();
        }
        return super.getPlayer();
    }

    @Override
    public ArrayList<Fruit> getFruits() {
        if (super.getFruits() == null || super.getFruits().size() <= 0) {
            this.determineAllFruits();
            for (Fruit fruit : super.getFruits()) {fruit.initialize();}
        }
        return super.getFruits();
    }

    public ArrayList<LevelAssignment> getAllLevelAssignments() {
        if (allLevelAssignments == null || allLevelAssignments.size() <= 0) {
            this.determineLevelAssigments();
        }
        return allLevelAssignments;
    }

    public void setAllLevelAssignments(ArrayList<LevelAssignment> allLevelAssignments) {
        this.allLevelAssignments = allLevelAssignments;
    }

    public Point getWorldMapPosition() {
        return worldMapPosition;
    }

    public void setWorldMapPosition(Point worldMapPosition) {
        this.worldMapPosition = worldMapPosition;
    }


}
