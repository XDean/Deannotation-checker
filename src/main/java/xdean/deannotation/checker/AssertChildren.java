package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Inherited
public @interface AssertChildren {
  Class<? extends Annotation>[] annotated() default {};

  Class<?>[] implement() default {};
}
