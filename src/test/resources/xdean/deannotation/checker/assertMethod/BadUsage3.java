package xdean.deannotation.checker.assertMethod;

import static javax.lang.model.element.Modifier.ABSTRACT;

import xdean.deannotation.checker.AssertMethod;

public abstract class BadUsage3 {

  @AssertMethod(forbiddenModifiers = ABSTRACT)
  public abstract void func();
}
