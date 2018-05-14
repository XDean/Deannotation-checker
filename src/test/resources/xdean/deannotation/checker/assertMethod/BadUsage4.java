package xdean.deannotation.checker.assertMethod;

import java.util.List;

import xdean.deannotation.checker.AssertMethod;
import xdean.deannotation.checker.TypeRestrict;

public class BadUsage4 {
  @AssertMethod(returnType = @TypeRestrict(List.class))
  public void func() {
  }
}
