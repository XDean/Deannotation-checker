package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.lang.model.element.Modifier;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the target element's modifier.
 *
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckModifier {
  /**
   * Required Modifiers. Error happens when one of the annotation absent.
   */
  Modifier[] require() default {};

  /**
   * Forbidden Modifiers. Error happens when one of the annotation present.
   */
  Modifier[] forbid() default {};

  /**
   * Custom error message for this check
   */
  String message() default "";
}
