package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.annotation.processor.toolkit.annotation.Meta;

@Meta
@Documented
@Retention(CLASS)
@Target(ANNOTATION_TYPE)
public @interface CheckMethod {
  
  CheckAnnotation annotation() default @CheckAnnotation;

  CheckModifier modifier() default @CheckModifier;

  CheckType returnType() default @CheckType;

  int argCount() default -1;

  CheckParam[] argTypes() default {};
}
