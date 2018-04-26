package yourowngame.com.yourowngame.classes.commercial;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.commercial.interfaces.IAdManager;

public class AdManager implements IAdManager {
    private Activity context;
    private static final String TAG = "AdManager";


    //TODO: für internet permission prüfen und verlangen usw.
    public AdManager(Activity context) {
        this.setContext(context);
        this.initializeAdmob();
    }

    private void initializeAdmob() {
        MobileAds.initialize(this.getContext(), ADMOB_ID);
        Log.d(TAG, "initializeAdMob: Tried to initialize Admob.");
    }


    /*public RewardedVideoAd loadRewardedVideoInRewardActivity(@NonNull final Activity activityContext, @Nullable RewardedVideoAdListener adListener, @Nullable final Intent goToActivityAfterShown) {
        final String REWARDED_VIDEO_ID = USE_TEST_ADS ? TEST_ADS.REWARDED_AD_UNIT : REAL_ADS.REWARDED_AD_UNIT;

        final RewardedVideoAd rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activityContext);
        rewardedVideoAd.setRewardedVideoAdListener((adListener == null) ? new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                //just in case validate whether app is loaded
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show(); //show ad if loaded
                    Log.d(TAG, "onRewardedVideoAdLoaded: Tried to show rewarded video ad.");
                } else {
                    Log.e(TAG, "onRewardedVideoAdLoaded: This error should not happen.");
                }
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.d(TAG, "onRewardedVideoAdOpened: Opened Rewarded video ad and have informed user.");
                Toast.makeText(activityContext, R.string.adManager_msg_rewardAd_whatIsMyReward, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                //if ad closed go to gotoActivity
                Log.d(TAG, "onRewardedVideoAdClosed: Rewarded video ad got closed and new activity got started.");
                if (goToActivityAfterShown != null) {
                    Log.d(TAG, "onRewardedVideoAdClosed: gotoActivity is not null.");
                    getContext().startActivity(goToActivityAfterShown);
                } else {
                    //IMPORTANT: In this case (because this ad has its own activity) we finish the activity if no target activity is specified.
                    Log.d(TAG, "onRewardedVideoAdClosed: Tried to finish activity.");
                    activityContext.finish();
                }
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                //Maybe other rewardMessage?
                Toast.makeText(activityContext, R.string.adManager_success_rewardAd_rewardReceived, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int errorcode) {
                Toast.makeText(getContext(), R.string.adManager_error_couldNotLoadAd, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onAdFailedToLoad: Could not load interstitial ad. Errorcode: " + errorcode);

                if (goToActivityAfterShown != null) {
                    Log.d(TAG, "onAdClosed: gotoActivity is not null.");
                    activityContext.startActivity(goToActivityAfterShown); //does app not prevent from being executed without internet
                } else {
                    //IMPORTANT: In this case (because this ad has its own activity) we finish the activity if no target activity is specified.
                    Log.d(TAG, "onRewardedVideoAdFailedToLoad: Tried to finish activity.");
                    activityContext.finish();
                }
            }
        } : adListener); //add given listener or create default one
        Log.d(TAG, "loadRewardedVideoInRewardActivity: Created reward video instance etc.");

        rewardedVideoAd.loadAd(REWARDED_VIDEO_ID, new AdRequest.Builder().build());
        Log.d(TAG, "loadRewardedVideoInRewardActivity: Tried to load rewarded video.");

        return rewardedVideoAd;
    }*/


    public void loadFullPageAd(@Nullable final AdListener adListener, @Nullable final Intent goToActivityAfterShown) {
        //IMPORTANT: ADMOB-GUIDELINE only place interestials between activities with contents and not too much!! Showing Fullpage Ad only allowed if loadingActivity shows BEFORE ad! (see: https://support.google.com/admob/answer/6201362?hl=de&ref_topic=2745287)
        final String FULLPAGE_ID = USE_TEST_ADS ? TEST_ADS.INTERSTITIAL_AD_UNIT : REAL_ADS.INTERSTITIAL_AD_UNIT;

        final InterstitialAd fullpageAd = new InterstitialAd(getContext());
        fullpageAd.setAdUnitId(FULLPAGE_ID);
        fullpageAd.loadAd(new AdRequest.Builder().build());
        fullpageAd.setAdListener((adListener == null) ? new AdListener() {
            @Override
            public void onAdLoaded() {
                //just in case validate whether app is loaded
                if (fullpageAd.isLoaded()) {
                    fullpageAd.show(); //show ad if loaded
                    Log.d(TAG, "onAdLoaded: Tried to show fullpage ad.");
                } else {
                    Log.e(TAG, "onAdLoaded: This error should not happen.");
                }
            }

            @Override
            public void onAdClosed() {
                //if ad closed go to gotoActivity
                Log.d(TAG, "onAdClosed: Interstitial ad got closed and new activity got started.");
                if (goToActivityAfterShown != null) {
                    Log.d(TAG, "onAdClosed: gotoActivity is not null.");
                    getContext().startActivity(goToActivityAfterShown);
                }
            }

            @Override
            public void onAdFailedToLoad(int errorcode) {
                Toast.makeText(getContext(), R.string.adManager_error_couldNotLoadAd, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onAdFailedToLoad: Could not load interstitial ad. Errorcode: " + errorcode);

                if (goToActivityAfterShown != null) {
                    Log.d(TAG, "onAdClosed: gotoActivity is not null.");
                    getContext().startActivity(goToActivityAfterShown); //does app not prevent from being executed without internet
                }
            }
        } : adListener); //IMPORTANT: add given adListener, if null create default one
    }

    public void loadBannerAd(final RelativeLayout viewGroup) {
        final String BANNER_ID = USE_TEST_ADS ? TEST_ADS.BANNER_AD_UNIT : REAL_ADS.BANNER_AD_UNIT;

        final AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.SMART_BANNER); //IMPORTANT: adsize and adunit should be added in the same manner! (programmatically | xml)
        adView.setAdUnitId(BANNER_ID);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adView.setLayoutParams(lp);

        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorcode) {
                Toast.makeText(getContext(), R.string.adManager_error_couldNotLoadAd, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onAdFailedToLoad (loadBannerAd): Banner could not be loaded.");
            }

            @Override
            public void onAdLoaded() {
                viewGroup.removeView(adView);
                viewGroup.addView(adView); //add to layout if loaded
                Log.d(TAG, "onAdLoaded (loadBannerAd): Banner successfully loaded.");
            }
        });
    }


    // GETTER / SETTER ---------------------------
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}
