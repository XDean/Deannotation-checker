package xdean.deannotation.checker.checkModifier;

import static javax.lang.model.element.Modifier.ABSTRACT;

import xdean.deannotation.checker.CheckModifier;

public abstract class BadUsage2 {

  @CheckModifier(forbid = ABSTRACT)
  public @interface Anno {
  }

  @Anno
  public abstract void func();
}
