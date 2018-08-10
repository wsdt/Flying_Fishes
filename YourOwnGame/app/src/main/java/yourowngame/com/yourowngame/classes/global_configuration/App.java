package yourowngame.com.yourowngame.classes.global_configuration;

import android.app.Application;
import android.content.Context;

import yourowngame.com.yourowngame.classes.manager.AdManager;

/** custom application class */
public class App extends Application {
    /** Called before any activity etc. and only once. */
    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize Admob one time.
        AdManager.initializeAdmob(getApplicationContext());
    }
}
