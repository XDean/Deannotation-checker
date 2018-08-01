package xdean.deannotation.checker.checkConstructor;

import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

@CheckConstructor(argCount = 1, args = {
    @CheckParam(type = @CheckType(value = Number.class, type = Type.EXTEND_ALL)),
    @CheckParam(type = @CheckType(value = String.class, type = Type.SUPER))
})
public @interface BadDefine1 {
}
