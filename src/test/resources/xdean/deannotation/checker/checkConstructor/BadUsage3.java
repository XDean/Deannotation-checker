package xdean.deannotation.checker.checkConstructor;

import xdean.deannotation.checker.CheckConstructor;

public class BadUsage3 {
  @CheckConstructor(argCount = 2)
  public @interface Anno {
  }

  @Anno
  public BadUsage3(Integer i) {
  }
}
