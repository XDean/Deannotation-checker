package xdean.deannotation.checker.checkParam;

import javax.annotation.CheckForNull;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckParam;

public class BadUsage1 {

  @CheckParam(annotation = @CheckAnnotation(require = CheckForNull.class))
  public @interface Anno {

  }

  public void func(@Anno int i) {
  }
}
