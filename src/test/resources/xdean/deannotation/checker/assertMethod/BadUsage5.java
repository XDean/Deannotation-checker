package xdean.deannotation.checker.assertMethod;

import xdean.deannotation.checker.AssertMethod;

public class BadUsage5 {
  @AssertMethod(argCount = 2)
  public void func(Integer i) {
  }
}
