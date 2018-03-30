package yourowngame.com.yourowngame.classes.background.layers;


public interface IBackgroundLayer_Clouds {
    int AMOUNT_CLOUDS = 10;
    float CLOUD_RANDOM_Y_PLACEMENT_IN_PERCENTAGE = 0.40f; //top 40% where clouds can appear
    float CLOUD_RANDOM_SPEED_MAX = 5f;
    float CLOUD_RANDOM_SPEED_MIN = 1f; //do not place 0!
}
