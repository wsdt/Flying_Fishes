package yourowngame.com.yourowngame.classes.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.GregorianCalendar;

/** Used to indicate that method or other members should be deleted if all
 * developers took notice of change, agreed with it and after member not used anymore. */

@Retention(RetentionPolicy.SOURCE)
public @interface Delete {
    /** Why is this tested with an own method/class etc.*/
    String description() default "No description provided.";

    String createdBy() default "Unknown";
    String lastModified() default "dd.MM.JJJJ hh:mm";
}
