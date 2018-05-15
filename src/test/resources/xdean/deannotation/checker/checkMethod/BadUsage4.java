package xdean.deannotation.checker.checkMethod;

import java.util.List;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.TypeRestrict;

public class BadUsage4 {
  @CheckMethod(returnType = @TypeRestrict(List.class))
  public void func() {
  }
}
