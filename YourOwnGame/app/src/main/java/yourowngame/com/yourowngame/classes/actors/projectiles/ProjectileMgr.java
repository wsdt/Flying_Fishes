package yourowngame.com.yourowngame.classes.actors.projectiles;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.projectiles.interfaces.IProjectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.specializations.Projectile_Iron;
import yourowngame.com.yourowngame.classes.annotations.Delete;
import yourowngame.com.yourowngame.classes.annotations.Testing;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

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
    private static boolean alreadyExecuted = false;
    public static void runDefaultConfiguration(@NonNull Activity activity) {
        //by default set 20 bullets for shoot before reload necessary (bullets leaving screen [currently])
        if (!alreadyExecuted) {
            for (int i = 0; i < 5; i++) {
                Projectile p = new Projectile_Iron(activity, 10, 0);
                p.initialize();
                getReadyForShotProjectiles().add(p);
                Log.d(TAG, "runDefaultConfiguration: Added bullet no. " + (i + 1));
            }
            alreadyExecuted = true;
        }
    }

    /** @param forceShoot: If true then frequency capping is IGNORED (which means bullets can be shot
     * simultaneously). If false the shooting frequency of the bullet is used. NOTE: If the user has
     * no munition at that time also the forceShoot-param won't change anything on this.
     * @return Projectile: Returns shot projectile. WARNING: If no bullet has been shot this method
     * returns NULL! */
    public static Projectile shoot(@NonNull Player player, boolean forceShoot) {
        if (getReadyForShotProjectiles().size() > 0) {
            Projectile p = getReadyForShotProjectiles().get(0);

            //use player loopcount as bullets are not updated when not shot
            if (player.getLoopCount() % p.getShootFrequency() == 0 || forceShoot) {
                p.setPosX(player.getPosX() + player.getWidthOfBitmap() / 2);
                p.setPosY(player.getPosY() + player.getHeightOfBitmap() / 2);

                getReadyForShotProjectiles().remove(p);
                getShotProjectiles().add(p); //if out of screen the bullet gets added back to the getready list
                Log.d(TAG, "shoot: Fired bullet.");
                return p;
            } else {
                Log.d(TAG, "shoot: Frequency capping, this bullet cannot be fired that often -> "+p.getLoopCount()+" / "+p.getShootFrequency());
            }
        } else {
            Log.d(TAG, "shoot: No bullets left.");
        }
        return null;
    }

    public static void drawProjectiles(@NonNull Canvas canvas, long loopCount) {
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

                Log.d(TAG, "updateProjectiles: "+p.getPosX()+" > "+DrawableSurfaces.getDrawWidth() +"+"+ IProjectile.PROPERTIES.DEFAULT.ADDITIONAL_GAME_WIDTH);
                if (p.getPosX() > DrawableSurfaces.getDrawWidth() + IProjectile.PROPERTIES.DEFAULT.ADDITIONAL_GAME_WIDTH) {
                    Log.e(TAG, "updateProjectiles: Bullet out of screen!");
                    reuseBullet(p,it);
                }
            }
    }

    /** @param projectileIterator: Iterator obj (for avoiding concurrentModificationException)
     *          Iterator should point to the same obj as 'p'!! (no next-call)
     *          Currently this method is only used in loops so the param is @NonNull
     *
     * @param p: iterator.next()-call would be perfectly. I used this approach so we can use the p-obj in
     * in the meantime for evaluation purposes etc. (just look e.g. at updateProjectiles()). */
    public static void reuseBullet(@NonNull Projectile p, @NonNull Iterator projectileIterator) {
        p.resetPos();
        getReadyForShotProjectiles().add(p); //add to shootable again
        projectileIterator.remove();
    }

    /** Call after level ends. */
    public static void cleanUp() {
        for (Iterator<Projectile> it = getShotProjectiles().iterator(); it.hasNext();) {
            reuseBullet(it.next(), it);
        }
        Log.d(TAG, "cleanUp: Cleaned up shot bullets.");
    }


    //GETTER/SETTER ----------------------------------------------------------
    public static ArrayList<Projectile> getShotProjectiles() {
        return shotProjectiles;
    }

    public static void setShotProjectiles(@NonNull ArrayList<Projectile> shotProjectiles) {
        ProjectileMgr.shotProjectiles = shotProjectiles;
    }

    public static ArrayList<Projectile> getReadyForShotProjectiles() {
        return readyForShotProjectiles;
    }

    public static void setReadyForShotProjectiles(@NonNull ArrayList<Projectile> readyForShotProjectiles) {
        ProjectileMgr.readyForShotProjectiles = readyForShotProjectiles;
    }
}
