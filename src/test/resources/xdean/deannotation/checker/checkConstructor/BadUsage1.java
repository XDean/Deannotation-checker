package xdean.deannotation.checker.checkConstructor;

import static javax.lang.model.element.Modifier.PUBLIC;

import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckModifier;

public class BadUsage1 {

  @CheckConstructor(argCount = 0, modifier = @CheckModifier(require = PUBLIC))
  public @interface Anno {

  }

  @Anno
  public BadUsage1(int i) {
  }
}
