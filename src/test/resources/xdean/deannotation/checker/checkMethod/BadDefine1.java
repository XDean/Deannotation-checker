package xdean.deannotation.checker.checkMethod;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

@CheckMethod(argCount = 1, args = {
    @CheckParam(type = @CheckType(value = Number.class, type = Type.EXTEND_ALL)),
    @CheckParam(type = @CheckType(value = String.class, type = Type.SUPER))
})
public @interface BadDefine1 {
}
