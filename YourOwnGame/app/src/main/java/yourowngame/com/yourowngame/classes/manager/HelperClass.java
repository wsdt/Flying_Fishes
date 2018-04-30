package yourowngame.com.yourowngame.classes.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.R;

/** This class provides (mostly static) methods/params which might be used in a huge amount
 * of different classes. So only put here methods where you surely know you will need it more often,
 * and only when it does not make any sense to put the method to other classes (cohesion). */
public class HelperClass {
    /** @param versionName: If true then method will use the param versionName, if false then the
     * versionCode will be returned (see build.gradle). */
    public static String getAppVersion(@NonNull Context context, boolean versionName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);

            if (versionName) {
                return packageInfo.versionName;
            } else {
                return String.valueOf(packageInfo.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getResources().getString(R.string.helperClass_getAppVersion_error);
        }
    }

}
