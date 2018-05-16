package xdean.deannotation.checker.checkAnnotation;

import org.junit.Test;

import xdean.deannotation.checker.CheckAnnotation;

public class BadUsage1 {
  @CheckAnnotation(require = Test.class)
  public @interface Anno {
  }

  @Anno
  public void func() {
  }
}
