package xdean.deannotation.checker.checkMethod;

import static javax.lang.model.element.Modifier.SYNCHRONIZED;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckModifier;

public class BadUsage2 {
  @CheckMethod(modifier = @CheckModifier(required = SYNCHRONIZED))
  public @interface Anno {
  }

  @Anno
  public void func() {
  }
}
