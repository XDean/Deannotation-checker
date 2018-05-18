package xdean.deannotation.checker.checkMethod;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class BadUsage4 {
  @CheckMethod(argTypes = {
      @CheckType(value = Number.class, type = Type.EXTEND)
  })
  public @interface Anno {
  }

  @Anno
  public void func(String s) {
  }
}