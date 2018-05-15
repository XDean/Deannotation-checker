package xdean.deannotation.checker.checkMethod;

import xdean.deannotation.checker.CheckMethod;

public class BadUsage5 {
  @CheckMethod(argCount = 2)
  public void func(Integer i) {
  }
}
