package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

/**
 * Beta.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Meta
@Documented
@Retention(CLASS)
@Target({ TYPE, ANNOTATION_TYPE })
public @interface CheckChildren {
  Class<? extends Annotation>[] annotated() default {};

  boolean includeAbstract() default false;
}
