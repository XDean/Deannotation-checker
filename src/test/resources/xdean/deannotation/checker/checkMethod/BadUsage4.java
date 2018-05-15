package xdean.deannotation.checker.checkMethod;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.TypeRestrict;
import xdean.deannotation.checker.TypeRestrict.Type;

public class BadUsage4 {
  @CheckMethod(argTypes = {
      @TypeRestrict(value = Number.class, type = Type.EXTEND)
  })
  public @interface Anno {
  }

  @Anno
  public void func(String s) {
  }
}
