package xdean.deannotation.checker.checkField;

import static javax.lang.model.element.Modifier.TRANSIENT;
import static javax.lang.model.element.Modifier.VOLATILE;

import org.junit.Rule;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.CheckType;

public class GoldenUsage {
  @CheckField(
      modifier = @CheckModifier(require = TRANSIENT, forbid = VOLATILE),
      annotation = @CheckAnnotation(require = Rule.class),
      type = @CheckType(int.class))
  public @interface Anno {
  }

  @Rule
  @Anno
  transient int i;
}
