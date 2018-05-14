package xdean.deannotation.checker;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.lang.model.element.Modifier;

import xdean.annotation.processor.toolkit.annotation.Meta;

@Meta
@Documented
@Retention(CLASS)
@Target({ METHOD, ANNOTATION_TYPE })
public @interface AssertMethod {

  Modifier[] requiredModifiers() default {};

  Modifier[] forbiddenModifiers() default {};

  TypeRestrict returnType() default @TypeRestrict;

  int argCount() default -1;

  TypeRestrict[] argTypes() default {};
}
