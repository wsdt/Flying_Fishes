package yourowngame.com.yourowngame.classes.gamelevels.interfaces;

import android.support.annotation.Nullable;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.annotations.Enhance;

@Enhance (message = "You were right, we need a more universal updateInterface (because this one only suits the player) OR" +
        "we find a better way!")
//TODO: Also use in future for backgrounds to be consistent! But remove as much method params as possible to use it generically!
public interface IUpdateAble {
    /**
     * method which its only use is to update the objects x and y axis!
     *
     * @param goUp check if object goes up
     * @param goForward check if object goes down
     */
    void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward);
}
