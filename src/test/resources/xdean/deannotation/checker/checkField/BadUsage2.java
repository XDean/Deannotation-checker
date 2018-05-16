package xdean.deannotation.checker.checkField;

import java.util.List;

import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.TypeRestrict;

public class BadUsage2 {
  @CheckField(type = @TypeRestrict(List.class))
  public @interface Anno {
  }

  @Anno
  int i;
}
