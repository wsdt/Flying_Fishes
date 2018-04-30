package yourowngame.com.yourowngame.classes.commercial.interfaces;

import yourowngame.com.yourowngame.BuildConfig;

/** Extracted from Constants interface (better separation of concern)
 * --> By extracting it to an separate interface again and not simply
 * making it to class members we could add here several methods etc. */

public interface IAdManager {
    String ADMOB_ID = "ca-app-pub-8160960481527784~7542998003";
    boolean USE_TEST_ADS = BuildConfig.BUILD_TYPE.equals("debug"); //so the build type decides whether test ads are shown or not.

    interface TEST_ADS {
        String BANNER_AD_UNIT = "ca-app-pub-3940256099942544/6300978111";
        String INTERSTITIAL_AD_UNIT = "ca-app-pub-3940256099942544/1033173712";
        String REWARDED_AD_UNIT = "ca-app-pub-3940256099942544/5224354917";
        //Native Advanced and native express could be also used! (see AdMob)
    }

    interface REAL_ADS { //Important: Real and TestAds interface should have the SAME members!
        String BANNER_AD_UNIT = "ca-app-pub-8160960481527784/3864920525";
        String INTERSTITIAL_AD_UNIT = "ca-app-pub-8160960481527784/1753283579";
        String REWARDED_AD_UNIT = TEST_ADS.REWARDED_AD_UNIT; //TODO: For rewarded ad we need to now the reward to create it! (used test ad temporary)
    }
}
