package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the target element's type. This annotation usually use in other check annotations.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckType {

  /**
   * @see CheckType#value()
   */
  interface Irrelevant {
  }

  enum Type {
    /**
     * The target type must super the base type.
     */
    SUPER,
    /**
     * The target type must same as the base type.
     */
    EQUAL,
    /**
     * The target type must extend all the base types.
     */
    EXTEND_ALL,
    /**
     * The target type must extend one of the base types.
     */
    EXTEND_ONE;
  }

  /**
   * The base type to do the check. If it's {@link Irrelevant}, the check will always pass.
   */
  Class<?>[] value() default { Irrelevant.class };

  /**
   * The check type.
   * 
   * @see Type
   */
  Type type() default Type.EQUAL;
}
