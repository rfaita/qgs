package com.qgs.ui.broadcast;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * @author rafael
 */
@Qualifier
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
public @interface OriginalSender {
}
