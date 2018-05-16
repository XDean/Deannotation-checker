package xdean.deannotation.checker.checkMethod;

import java.util.List;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckType;

public class BadUsage2 {
  @CheckMethod(returnType = @CheckType(List.class))
  public @interface Anno {
  }

  @Anno
  public void func() {
  }
}
