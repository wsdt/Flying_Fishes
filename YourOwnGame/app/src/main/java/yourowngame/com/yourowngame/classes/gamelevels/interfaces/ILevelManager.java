package yourowngame.com.yourowngame.classes.gamelevels.interfaces;

import yourowngame.com.yourowngame.classes.annotations.Enhance;

public interface ILevelManager {
    @Enhance (message = "Currently only for next second level implemented. But make it level-dependent in future!")
    long LEVEL_TWO = 1000;
}
