package xdean.deannotation.checker.checkParam;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;

public class GoldenUsage {
  @CheckParam(
      annotation = @CheckAnnotation(require = Anno2.class),
      type = @CheckType(int.class))
  @interface Anno {
  }

  @interface Anno2 {
  }

  public void func(@Anno @Anno2 int i) {

  }
}
