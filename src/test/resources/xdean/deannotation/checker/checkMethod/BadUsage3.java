package xdean.deannotation.checker.checkMethod;

import xdean.deannotation.checker.CheckMethod;

public class BadUsage3 {
  @CheckMethod(argCount = 2)
  public @interface Anno {
  }

  @Anno
  public void func(Integer i) {
  }
}
