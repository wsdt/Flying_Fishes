package yourowngame.com.yourowngame.classes.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,
        ElementType.FIELD,ElementType.PARAMETER,
        ElementType.LOCAL_VARIABLE,ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.PACKAGE})


public @interface Idea {
    enum Priority {
        NOT_DETERMINED, LOW, HIGH
    }

    Priority priority() default Priority.NOT_DETERMINED;
    String[] idea() default "No idea defined";
    String[] text() default "Nothing provided";


}
