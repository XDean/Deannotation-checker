package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the target field correct defined.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckField {
  /**
   * Check the field's name
   */
  CheckName name() default @CheckName;

  /**
   * Check the field's annotation.
   */
  CheckAnnotation annotation() default @CheckAnnotation;

  /**
   * Check the field's modifier.
   */
  CheckModifier modifier() default @CheckModifier;

  /**
   * Check the field's type.
   */
  CheckType type() default @CheckType;
}
