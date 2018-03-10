package yourowngame.com.yourowngame.classes.configuration;

/**
 * Constants interfaces are referencing NOT translateable (global) values [e.g. numbers, auth-tokens, arbitrary strings etc.].
 * If a string can be translated (sensefully) then place it into Strings.xml Resource file!
 *
 * Please group constants within superior Interface 'Constants' by additional interfaces [e.g. Constants > GameObject > {values}]
 *
 * Constants always in UPPER_CASE separated by dash
 *
 * Im a slightly unhappy about the Constants Interface, because Interfaces should be used for
 * inheritance and if this game gets further, the most classes depend on that special "interface"
 * which is basically just a placeholder for constants...
 *
 * Cohesion of other classes will suffer!
 *
 */

public interface Constants {
    interface Developers {
        String WSDT = "Kevin Riedl (WSDT)";
        String SOLUTION = "Christof Jori (SOLUTION)";
    }

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
            //max/min values so enemies can be created dynamically
            float speedXmin = 0.1f;
            float speedXmax = 1; //could be also level dependent :) this will surely be level dependent :)
            float speedYmin = 0.1f;
            float speedYmax = 1;


            int defaultRotation = 0;
            int rotationFlyingUp = 10;
            int rotationFlyingDown = -10;
            float widthPercentage = 0.35f;
            float heightPercentage = 0.35f;
        }
        interface Player {
            int defaultRotation = 0;
            int rotationFlyingUp = 5;
            int rotationFlyingDown = -5;
            float widthPercentage = 0.35f;
            float heightPercentage = 0.35f;
        }
        interface Reward {}
    }

    interface GameLogic {
        interface GameLoopThread {
            int maxFramesSkipable = 5;
            int maxFPS = 50;
        }
        interface GameView {
            float widthInPercentage = 0.35f;
            float heightInPercentage = 0.35f;
        }
    }

    interface Background {
        float defaultBgSpeed = 0.01f; //the lower the no. the slower the skyElements (e.g. layer1: Clouds)
        interface layer1_clouds {
            int anzahlClouds = 10;
            float randomYplacementInPercentageCloud = 0.40f; //top 40% where clouds can appear
            float randomCloudSpeedMax = 5f;
            float randomCloudSpeedMin = 1f; //do not place 0!
        }
    }
}
