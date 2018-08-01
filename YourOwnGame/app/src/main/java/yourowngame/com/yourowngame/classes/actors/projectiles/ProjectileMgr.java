package yourowngame.com.yourowngame.classes.actors.projectiles;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.projectiles.specializations.Projectile_Iron;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.annotations.Delete;
import yourowngame.com.yourowngame.classes.annotations.Testing;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

public class ProjectileMgr {
    private static final String TAG = "ProjectileMgr";
    /**
     * All bullets which are currently moving on screen.
     */
    private static ArrayList<Projectile> shotProjectiles = new ArrayList<>();
    /**
     * All bullets left (which a user can shoot by pressing on the left screen)
     */
    private static ArrayList<Projectile> readyForShotProjectiles = new ArrayList<>();

    private ProjectileMgr() {
    } //no instance allowed

    @Testing(message = "Currently just for testing, in future remove this and set those configs dynamically",
            priority = Testing.Priority.MEDIUM, byDeveloper = Constants.Developers.WSDT)
    @Delete(lastModified = "01.08.2018 12:25", createdBy = Constants.Developers.WSDT)
    public static void runDefaultConfiguration(@NonNull Activity activity) {
        //by default set 20 bullets for shoot before reload necessary (bullets leaving screen [currently])
        for (int i = 0; i < 5; i++) {
            Projectile p = new Projectile_Iron(activity, 0, 0, 10, 0);
            p.initialize();
            getReadyForShotProjectiles().add(p);
        }
    }

    @Bug(problem = "No bullets left not correct? Why? --> curr only 5 bullets should be on screen at maximum")
    public static void shoot(@NonNull Player player) {
        if (getReadyForShotProjectiles().size() > 0) {
            Projectile p = getReadyForShotProjectiles().get(0);

            //use player loopcount as bullets are not updated when not shot
            if (player.getLoopCount() % p.getShortFrequency() == 0) {
                p.setPosX(player.getPosX() + player.getWidthOfBitmap() / 2);
                p.setPosY(player.getPosY() + player.getHeightOfBitmap() / 2);

                getReadyForShotProjectiles().remove(p);
                getShotProjectiles().add(p); //if out of screen the bullet gets added back to the getready list
            } else {
                Log.d(TAG, "shoot: Frequency capping, this bullet cannot be fired that often -> "+p.getLoopCount()+" / "+p.getShortFrequency());
            }
        } else {
            Log.d(TAG, "shoot: No bullets left.");
        }
    }

    public static void drawProjectiles(@NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (Projectile p : getShotProjectiles()) {
            p.setLoopCount(loopCount);
            p.setCanvas(canvas); //todo: not necessary to set in every loop
            p.draw();
        }
    }

    public static void updateProjectiles() {
            //Using iterator directly to prevent concurrentmodification exception
            for (Iterator<Projectile> it = getShotProjectiles().iterator(); it.hasNext();) {
                Projectile p = it.next();
                p.update();

                if (p.getPosX() > GameViewActivity.GAME_WIDTH - 50) {
                    Log.e(TAG, "updateProjectiles: Bullet out of screen!");
                    p.resetPos(); //reset pos

                    it.remove(); //remove from shot projectiles
                    getReadyForShotProjectiles().add(p);//add to shootable again
                }
            }
    }


    //GETTER/SETTER ----------------------------------------------------------
    public static ArrayList<Projectile> getShotProjectiles() {
        return shotProjectiles;
    }

    public static void setShotProjectiles(ArrayList<Projectile> shotProjectiles) {
        ProjectileMgr.shotProjectiles = shotProjectiles;
    }

    public static ArrayList<Projectile> getReadyForShotProjectiles() {
        return readyForShotProjectiles;
    }

    public static void setReadyForShotProjectiles(ArrayList<Projectile> readyForShotProjectiles) {
        ProjectileMgr.readyForShotProjectiles = readyForShotProjectiles;
    }
}
