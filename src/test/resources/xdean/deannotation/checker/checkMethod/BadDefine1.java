package xdean.deannotation.checker.checkMethod;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.TypeRestrict;
import xdean.deannotation.checker.TypeRestrict.Type;

@CheckMethod(argCount = 1, argTypes = {
    @TypeRestrict(value = Number.class, type = Type.EXTEND),
    @TypeRestrict(value = String.class, type = Type.SUPER)
})
public @interface BadDefine1 {
}
