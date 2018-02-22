package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.classes.configuration.Constants;


public class Enemy extends GameObject {

    private static Enemy INSTANCE;
    private List<Enemy> enemyList = new ArrayList<>();
    private Player player;

    public static Enemy getInstance(){
        return INSTANCE == null ? INSTANCE = new Enemy() : INSTANCE;
    }

    public Enemy(double posX, double posY, double speedX, double speedY, int[] img, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, name);
    }

    public Enemy(){}

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }

    @Override
    public boolean collision(View view, GameObject obj) {
        return false;
    }


    //So we have all parameters we need quite compact in the GameView.class (by creation)
    public void createEnemys(int numberOfEnemys, double posX, double posY,
                             double speedX, double speedY, int[] img, @Nullable String name){
        //add enemy to list
        for(int i=0; i <= numberOfEnemys; i++) {
            enemyList.add(new Enemy(posX, posY, speedX, speedY, img, name));
        }
    }

    //Returns the enemyList, ready for drawing, rendering etc.
    public List<Enemy> getEnemys(){
        return enemyList;
    }

    /**
     * And thats what i meant, GameObjects-update() method should be available for all members
     * currently the method is only suitable for the player class.
     *
     * So we need to put the update() Logic from GameObject into the Player.class
     * and offer a method that suits ALL subclasses.
     *
     * (So this method will later be the update() Method from the Enemy.class)
     */

    public void aimToPlayer(Player player) {
        this.player = player; //not really necess

        // move closer to Player
        // 1 circle, run through all the enemys, see how they can optimate their position
        // current speed per circle (Constants.Actors.Enemy.MOVING_SPEED = 10)
        for (int i = 0; i < enemyList.size(); i++){

            if(player.getPosX() < enemyList.get(i).getPosX())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() - Constants.Actors.Enemy.MOVING_SPEED);
            else if(player.getPosX() > enemyList.get(i).getPosX())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() + Constants.Actors.Enemy.MOVING_SPEED);

            if(player.getPosY() < enemyList.get(i).getPosY())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() - Constants.Actors.Enemy.MOVING_SPEED);
            else if(player.getPosY() > enemyList.get(i).getPosY())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() + Constants.Actors.Enemy.MOVING_SPEED);
        }
    }
}
