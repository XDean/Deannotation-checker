package xdean.deannotation.checker.assertMethod;

import xdean.deannotation.checker.AssertMethod;
import xdean.deannotation.checker.TypeRestrict;
import xdean.deannotation.checker.TypeRestrict.Type;

public class BadDefine1 {
  @AssertMethod(argCount = 1, argTypes = {
      @TypeRestrict(value = Number.class, type = Type.EXTEND),
      @TypeRestrict(value = String.class, type = Type.SUPER)
  })
  public void func() {
  }
}
