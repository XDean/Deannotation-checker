package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the target method correct defined.
 *
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckMethod {
  /**
   * Check the method's name
   */
  CheckName name() default @CheckName;

  /**
   * Check the method's annotation.
   */
  CheckAnnotation annotation() default @CheckAnnotation;

  /**
   * Check the method's modifier.
   */
  CheckModifier modifier() default @CheckModifier;

  /**
   * Check the method's return type.
   */
  CheckType returnType() default @CheckType;

  /**
   * If it's nonnegative, check the method's parameter count.
   */
  int argCount() default -1;

  /**
   * Check the method's parameters. The n<sup>th</sup> {@code CheckParam} will be used to check the
   * n<sup>th</sup> parameter.
   */
  CheckParam[] args() default {};

  /**
   * Custom error message for this check
   */
  String message() default "";
}
