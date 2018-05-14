package xdean.deannotation.checker.assertMethod;

import static javax.lang.model.element.Modifier.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.deannotation.checker.AssertMethod;
import xdean.deannotation.checker.TypeRestrict;

@AssertMethod(argCount = 0, requiredModifiers = PUBLIC, returnType = @TypeRestrict(void.class))
@Retention(RUNTIME)
@Target(METHOD)
public @interface Init {

}
