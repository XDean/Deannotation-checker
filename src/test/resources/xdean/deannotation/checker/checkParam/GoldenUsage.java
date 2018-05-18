package xdean.deannotation.checker.checkParam;

import javax.annotation.CheckForNull;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;

public class GoldenUsage {
  @CheckParam(
      annotation = @CheckAnnotation(require = CheckForNull.class),
      type = @CheckType(int.class))
  public @interface Anno {
  }

  public void func(@Anno @CheckForNull int i) {

  }
}
