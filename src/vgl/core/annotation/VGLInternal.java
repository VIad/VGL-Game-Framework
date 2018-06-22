/**
 * 
 */
package vgl.core.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

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
