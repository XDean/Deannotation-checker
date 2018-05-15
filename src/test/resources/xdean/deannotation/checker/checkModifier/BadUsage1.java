package xdean.deannotation.checker.checkModifier;

import static javax.lang.model.element.Modifier.SYNCHRONIZED;

import xdean.deannotation.checker.CheckModifier;

public class BadUsage1 {
  @CheckModifier(require = SYNCHRONIZED)
  public @interface Anno {
  }

  @Anno
  public void func() {
  }
}
