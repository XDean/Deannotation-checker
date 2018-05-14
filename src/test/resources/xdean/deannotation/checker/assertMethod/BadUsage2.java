package xdean.deannotation.checker.assertMethod;

import static javax.lang.model.element.Modifier.SYNCHRONIZED;

import xdean.deannotation.checker.AssertMethod;

public class BadUsage2 {
  @AssertMethod(requiredModifiers = SYNCHRONIZED)
  public void func() {
  }
}
