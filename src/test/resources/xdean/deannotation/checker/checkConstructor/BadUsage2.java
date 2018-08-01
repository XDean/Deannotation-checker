package xdean.deannotation.checker.checkConstructor;

import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class BadUsage2 {
  @CheckConstructor(args = @CheckParam(type = @CheckType(value = Number.class, type = Type.EXTEND_ALL)))
  public @interface Anno {
  }

  @Anno
  public BadUsage2(String s) {
  }
}
