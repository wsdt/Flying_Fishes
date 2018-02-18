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
        interface GameObject {}
        interface Barrier {}
        interface Enemy {}
        interface Player {
            int defaultSpeedX = 5;
            int defaultSpeedY = 0;
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
        }
    }

    interface Background {
        int maxSizeBackground = 2150; //-10 for eq
    }
}
