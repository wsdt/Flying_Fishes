package yourowngame.com.yourowngame.classes.configuration;

/**
 * Constants interfaces are referencing NOT translateable (global) values [e.g. numbers, auth-tokens, arbitrary strings etc.].
 * If a string can be translated (sensefully) then place it into Strings.xml Resource file!
 *
 * Please group constants within superior Interface 'Constants' by additional interfaces [e.g. Constants > GameObject > {values}]
 */

public interface Constants {
    interface Configuration {}

    interface Ads {
        String admobAppId = "ca-app-pub-8160960481527784~7542998003";
        boolean useTestAds = true;
        interface TestAds {
            String bannerAdUnit = "ca-app-pub-3940256099942544/6300978111";
            String interstitialAdUnit = "ca-app-pub-3940256099942544/1033173712";
            String rewardedVideoAdUnit = "ca-app-pub-3940256099942544/5224354917";
            //Native Advanced and native express could be also used! (see AdMob)
        }
        interface RealAds { //Important: Real and TestAds interface should have the SAME members!
            String bannerAdUnit = "ca-app-pub-8160960481527784/3864920525";
            String interstitialAdUnit = "ca-app-pub-8160960481527784/1753283579";
            String rewardedVideoAdUnit = TestAds.rewardedVideoAdUnit; //TODO: For rewarded ad we need to now the reward to create it! (used test ad temporary)
        }
    }

    interface Actors {
        interface GameObject {
            int MOVE_UP_MULTIPLIER = 2;     //Jump Speed multiplied by MOVE_UP_MULTIPLIER
        }
        interface Barrier {}
        interface Enemy {
            int MOVING_SPEED = 10;
            float defaultRotation = 0;
            float rotationFlyingUp = 10;
            float rotationFlyingDown = -10;
        }
        interface Player {
            float defaultRotation = 0;
            float rotationFlyingUp = 5;
            float rotationFlyingDown = -5;
        }
        interface Reward {}
    }

    interface GameLogic {
        interface GameLoopThread {
            int maxFramesSkipable = 5;
            int maxFPS = 50;
        }
        interface GameView {
            int playerEffectTiltDegreePositive = 5; //this value gets multiplikated with (-1) all x milliseconds
            float playerEffectTiltDegreeChangeRate = 0.05f;
            float widthInPercentage = 0.35f;
            float heightInPercentage = 0.35f;
        }
    }

    interface Background {
        float defaultBgSpeed = 0.0001f; //the lower the no. the slower the skyElements (e.g. layer1: Clouds)
        interface layer1_clouds {
            int anzahlClouds = 15;
            float randomYplacementInPercentageCloud = 0.40f; //top 40% where clouds can appear
            float randomCloudSpeedMax = 1f;
            float randomCloudSpeedMin = 0.01f; //do not place 0!
        }
    }
}
