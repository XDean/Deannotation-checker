package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the target element's annotation
 *
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckAnnotation {
  /**
   * Required annotations. Error happens when one of the annotation absent.
   */
  Class<? extends Annotation>[] require() default {};

  /**
   * Forbidden annotations. Error happens when one of the annotation present.
   */
  Class<? extends Annotation>[] forbid() default {};

  /**
   * Custom error message for this check
   */
  String message() default "";
}
