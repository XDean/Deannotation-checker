package xdean.deannotation.checker.checkClass;

import xdean.deannotation.checker.CheckClass;

public class BadUsage1 {

  @CheckClass(implement = String.class)
  public @interface Anno {
  }

  @Anno
  static class A {
  }
}
