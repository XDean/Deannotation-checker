package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the target parameter correct defined.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckParam {
  /**
   * Check the parameter's type.
   */
  CheckType type() default @CheckType;

  /**
   * Check the parameter's annotation.
   */
  CheckAnnotation annotation() default @CheckAnnotation;
}
