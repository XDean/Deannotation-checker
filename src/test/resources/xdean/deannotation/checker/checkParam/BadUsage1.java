package xdean.deannotation.checker.checkParam;

import org.junit.Rule;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckParam;

public class BadUsage1 {

  @CheckParam(annotation = @CheckAnnotation(require = Rule.class))
  public @interface Anno {

  }

  public void func(@Anno int i) {
  }
}
