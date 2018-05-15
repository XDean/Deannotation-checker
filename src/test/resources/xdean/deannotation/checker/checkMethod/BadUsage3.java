package xdean.deannotation.checker.checkMethod;

import static javax.lang.model.element.Modifier.ABSTRACT;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckModifier;

public abstract class BadUsage3 {

  @CheckMethod(modifier = @CheckModifier(forbidden = ABSTRACT))
  public abstract void func();
}
