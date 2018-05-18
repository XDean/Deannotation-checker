package xdean.deannotation.checker.checkType;

import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class BadUsage2 {
  @CheckType(value = Number.class, type = Type.SUPER)
  public @interface Anno {
  }

  public void func(@Anno Integer i) {
  }
}
