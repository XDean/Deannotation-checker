package xdean.deannotation.checker.checkType;

import xdean.deannotation.checker.CheckType;

public class BadUsage1 {

  @CheckType(String.class)
  public @interface Anno {

  }

  public void func(@Anno int i) {
  }
}
