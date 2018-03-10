/**
 * 
 */
package vgl.core.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(CLASS)
@Target({
        TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, TYPE_PARAMETER
})
/**
 * 
 * 
 * 
 * @author VGL
 *
 */
public @interface VGLInternal {

}
