package xdean.deannotation.checker.checkType;

import java.io.Serializable;

import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class GoldenUsage {
  @CheckType(value = Number.class)
  public @interface Anno1 {
  }

  @CheckType(value = Number.class, type = Type.EXTEND)
  public @interface Anno2 {
  }

  @CheckType(value = Number.class, type = Type.SUPER)
  public @interface Anno3 {
  }

  @Anno2
  static abstract class A extends Number {
  }

  public void func(@Anno1 Number i) {
  }

  @Anno3
  Serializable o;
}
