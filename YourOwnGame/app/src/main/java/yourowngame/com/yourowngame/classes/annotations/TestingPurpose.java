package yourowngame.com.yourowngame.classes.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.GregorianCalendar;

/** Used more testmethods/classes for marking code for testing
 * purposes. You can also set several parameters to make the testing
 * reason more clear. */

@Retention(RetentionPolicy.RUNTIME)
public @interface TestingPurpose {
    GregorianCalendar now = new GregorianCalendar();

    enum Priority {
        LOW, MEDIUM, HIGH
    }

    /** Why is this tested with an own method/class etc.*/
    String testpurposeDescription() default "No description provided.";

    /** Set default priority */
    Priority priority() default Priority.MEDIUM;

    String createdBy() default "Unknown";
    String lastModified() default "dd.MM.JJJJ hh:mm";

    /** Further categorization (arbitrary strings) */
    String[] tags() default "default";

    /** Delete method/class if unused/no reference to it.
     * But keep it for e.g. further use if false. */
    boolean deleteWhenUnused() default true;
}
