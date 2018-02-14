package yourowngame.com.yourowngame.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import yourowngame.com.yourowngame.R;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = "LoadingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //this activity has no actionbar (see android manifest)

        //Create blinking effect
        final ImageView appIcon = ((ImageView) findViewById(R.id.appIcon));
        ObjectAnimator animator = ObjectAnimator.ofFloat(appIcon, "alpha", 0.25f);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();

        //TODO:dismiss Progressbar from XML after leaving

        Class loadThatActivityAfterAdShown = MainActivity.class;
        /* ++++++++++++++++ Use following commented block when you want to make target activity dynamically ++++++++++++++++
        try {
            if (getIntent().getIntExtra(Constants.CUSTOMNOTIFICATION.IDENTIFIER_COUNTDOWN_ID, -1) >= 0) {
                loadThatActivityAfterAdShown = CountdownActivity.class; //show countdown activity because from notification or similar called
                Log.d(TAG, "onCreate: Will call CountdownActivity instead of MainActivity.");
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Presumably LoadingScreenActivity not called from a notification. So intent is null or similar.");
            e.printStackTrace();
        }*/

        Intent followingActivityIntent = new Intent(this, loadThatActivityAfterAdShown);
        /* +++++++++++++++++ Use following commented block when you want to redirect all intent extras (given from e.g. notification) to the next activity +++
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            Bundle receivedExtras = receivedIntent.getExtras();
            if (receivedExtras != null) {
                //Put all extras (e.g. from notification) to new intent so we can pass it to countdownactivity e.g.
                followingActivityIntent.putExtras(receivedExtras);
                Log.d(TAG, "onCreate: Passed all received extras to new intent.");
            }
        }*/

        //FullPageAd etc. (use when you want to display ads and after loaded ad activity gets loaded)
        /*AdManager adManager = new AdManager(this);
        adManager.initializeAdmob();
        adManager.loadFullPageAd(null, followingActivityIntent); //after ad is closed or failure happens the maiActivity starts.
        */

        //TODO - IMPORTANT: COMMENT following line if you want to display interestial ads!
        startActivity(followingActivityIntent);
    }
}
