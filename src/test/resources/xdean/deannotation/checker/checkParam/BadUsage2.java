package xdean.deannotation.checker.checkParam;

import java.util.List;

import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;

public class BadUsage2 {
  @CheckParam(type = @CheckType(List.class))
  public @interface Anno {
  }

  public void func(@Anno int i) {
  }
}
