package yourowngame.com.yourowngame.classes.commercial.interfaces;

import yourowngame.com.yourowngame.BuildConfig;

/** COPY (not simply rename) this file AND 
			AFTER THAT RENAME the copied file from e.g. IAdManager_TEMPLATE (1).java to IAdManager.java
*/


/** Extracted from Constants interface (better separation of concern)
 * --> By extracting it to an separate interface again and not simply
 * making it to class members we could add here several methods etc. */

public interface IAdManager {
    String ADMOB_ID = "YOUR_ADMOB_ID";
    boolean USE_TEST_ADS = true; // true, so REAL_ADS doesn't need to be filled. 
	/* UNCOMMENT and comment line above to use test ads automatically acc. to buildtype
	boolean USE_TEST_ADS = BuildConfig.BUILD_TYPE.equals("debug"); //so the build type decides whether test ads are shown or not.*/

    interface TEST_ADS {
        String BANNER_AD_UNIT = "ca-app-pub-3940256099942544/6300978111";
        String INTERSTITIAL_AD_UNIT = "ca-app-pub-3940256099942544/1033173712";
        String REWARDED_AD_UNIT = "ca-app-pub-3940256099942544/5224354917";
        //Native Advanced and native express could be also used! (see AdMob)
    }

    interface REAL_ADS { //Important: Real and TestAds interface should have the SAME members!
        String BANNER_AD_UNIT = "AD_ID";
        String INTERSTITIAL_AD_UNIT = "AD_ID";
        String REWARDED_AD_UNIT = "AD_ID";
    }
}