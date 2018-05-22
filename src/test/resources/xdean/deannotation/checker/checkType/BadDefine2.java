package xdean.deannotation.checker.checkType;

import java.util.List;

import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

@CheckType(value = { List.class, int[].class }, type = Type.EQUAL)
public @interface BadDefine2 {
}
