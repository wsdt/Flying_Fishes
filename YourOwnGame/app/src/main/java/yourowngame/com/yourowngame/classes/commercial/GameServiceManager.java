package yourowngame.com.yourowngame.classes.commercial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import yourowngame.com.yourowngame.classes.annotations.Testing;

@Testing(message = "THIS CLASS IS ONLY FOR TESTING PURPOSES NOW")
public class GameServiceManager {
    private static final String TAG = "GameServiceManager";
    private Context context;
    private FirebaseAnalytics firebaseAnalytics;

    public GameServiceManager(@NonNull Context context) {
        this.setContext(context);
        this.setFirebaseAnalytics(FirebaseAnalytics.getInstance(context));
    }

    public void unlockAchievement(@NonNull String achievementId) {
        //TODO: OAuth: https://developers.google.com/identity/protocols/OAuth2
        //TODO: RestApi: https://developers.google.com/games/services/web/api/achievements/unlock#try-it

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
