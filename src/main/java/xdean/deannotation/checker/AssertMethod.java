package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

import xdean.annotation.processor.toolkit.annotation.Meta;

@Meta
@Documented
@Retention(CLASS)
@Target(TYPE)
public @interface AssertMethod {

  interface Irrelevant {
  }

  int accessLevel() default Modifier.PRIVATE;

  Class<?> returnType() default Irrelevant.class;

  int argCount() default -1;

  Class<?>[] argTypes() default {};
}
