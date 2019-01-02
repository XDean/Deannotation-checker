package xdean.deannotation.checker.checkAnnotation;

import org.junit.Test;

import xdean.deannotation.checker.CheckAnnotation;

public abstract class BadUsage2 {

  @CheckAnnotation(forbid = Test.class, message = "custom")
  public @interface Anno {
  }

  @Test
  @Anno
  public abstract void func();
}
