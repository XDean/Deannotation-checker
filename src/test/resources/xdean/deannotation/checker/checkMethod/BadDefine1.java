package xdean.deannotation.checker.checkMethod;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

@CheckMethod(argCount = 1, argTypes = {
    @CheckType(value = Number.class, type = Type.EXTEND),
    @CheckType(value = String.class, type = Type.SUPER)
})
public @interface BadDefine1 {
}
