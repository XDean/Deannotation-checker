package xdean.deannotation.checker.assertMethod;

import xdean.deannotation.checker.AssertMethod;
import xdean.deannotation.checker.TypeRestrict;
import xdean.deannotation.checker.TypeRestrict.Type;

public class BadUsage6 {
  @AssertMethod(argTypes = {
      @TypeRestrict(value = Number.class, type = Type.EXTEND)
  })
  public void func(String s) {
  }
}
