package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the target class correct defined.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckClass {
  /**
   * Required superclass/interface. Error happens when the target class can't assign to one of the
   * class.
   */
  Class<?>[] implement() default {};

  /**
   * Check the class's modifier.
   */
  CheckModifier modifier() default @CheckModifier;

  /**
   * Check the class's annotation
   */
  CheckAnnotation annotation() default @CheckAnnotation;
}