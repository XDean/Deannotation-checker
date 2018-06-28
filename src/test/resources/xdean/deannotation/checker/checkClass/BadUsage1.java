package xdean.deannotation.checker.checkClass;

import xdean.deannotation.checker.CheckClass;
import xdean.deannotation.checker.CheckType;

public class BadUsage1 {

  @CheckClass(type = @CheckType(String.class))
  public @interface Anno {
  }

  @Anno
  static class A {
  }
}
