package xdean.deannotation.checker.checkMethod;

import static javax.lang.model.element.Modifier.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.TypeRestrict;

@CheckMethod(argCount = 0, modifier = @CheckModifier(required = PUBLIC), returnType = @TypeRestrict(void.class))
@Retention(RUNTIME)
@Target(METHOD)
public @interface Init {

}
