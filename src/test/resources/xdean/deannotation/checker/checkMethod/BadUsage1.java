package xdean.deannotation.checker.checkMethod;

import static javax.lang.model.element.Modifier.PUBLIC;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.CheckType;

public class BadUsage1 {

  @CheckMethod(argCount = 0, modifier = @CheckModifier(require = PUBLIC), returnType = @CheckType(void.class))
  public @interface Anno {

  }

  @Anno
  public void func(int i) {
  }
}
