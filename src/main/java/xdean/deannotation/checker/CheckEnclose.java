package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Check the element directly enclosed elements.
 * 
 * For class, it encloses its fields, constructors, methods and sub-types.
 * 
 * For package, it encloses its classes.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckEnclose {

  /**
   * Check type
   */
  enum Type {
    /**
     * Only one of the element should be declared
     */
    ONE,
    /**
     * All the elements should be declared
     */
    ALL;
  }

  /**
   * Check enclosed fields
   */
  CheckField[] fields() default {};

  /**
   * Check enclosed constructors
   */
  CheckConstructor[] constructors() default {};

  /**
   * Check enclosed methods
   */
  CheckMethod[] methods() default {};

  /**
   * Check enclosed classes
   */
  CheckClass[] classes() default {};

  /**
   * Type of the check
   * 
   * @see Type
   */
  Type type() default Type.ALL;
}
