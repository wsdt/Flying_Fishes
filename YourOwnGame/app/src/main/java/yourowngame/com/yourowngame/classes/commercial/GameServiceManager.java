package yourowngame.com.yourowngame.classes.commercial;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.annotations.Test;

@Test (message = "THIS CLASS IS ONLY FOR TESTING PURPOSES NOW")
public class GameServiceManager {
    private static final String TAG = "GameServiceManager";
    private Context context;
    private FirebaseAnalytics firebaseAnalytics;

    public GameServiceManager(@NonNull Context context) {
        this.setContext(context);
        this.setFirebaseAnalytics(FirebaseAnalytics.getInstance(context));
    }

    //TODO: We need to implement  listeners or similar (something constent to evaluate when an achievement is achieved)
    @Deprecated
    @Enhance(message = "This is seemingly only logging online. Real achievements seem to be unlocked by REST Api." +
            "https://developers.google.com/games/services/web/api/achievements/unlock")
    public void unlockAchievement(@NonNull String achievementId) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ACHIEVEMENT_ID,achievementId);
        this.getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.UNLOCK_ACHIEVEMENT,bundle);
        Log.d(TAG, "unlockAchievement: Achievement unlocked with id->"+achievementId);
    }

    //GETTER/SETTER --------------------------------
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        return firebaseAnalytics;
    }

    public void setFirebaseAnalytics(FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }
}
