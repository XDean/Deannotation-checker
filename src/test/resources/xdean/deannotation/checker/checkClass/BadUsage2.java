package xdean.deannotation.checker.checkClass;

import javax.lang.model.element.Modifier;

import xdean.deannotation.checker.CheckClass;
import xdean.deannotation.checker.CheckModifier;

public class BadUsage2 {
  @CheckClass(modifier = @CheckModifier(forbid = Modifier.STATIC))
  public @interface Anno {
  }

  @Anno
  static class A {
  }
}
