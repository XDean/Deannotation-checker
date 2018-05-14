package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

@Meta
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface AssertChildren {
  Class<? extends Annotation>[] annotated() default {};

  boolean includeAbstract() default false;
}
