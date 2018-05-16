package xdean.deannotation.checker.checkField;

import static javax.lang.model.element.Modifier.TRANSIENT;
import static javax.lang.model.element.Modifier.VOLATILE;

import javax.annotation.CheckForNull;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.TypeRestrict;

public class GoldenUsage {
  @CheckField(
      modifier = @CheckModifier(require = TRANSIENT, forbid = VOLATILE),
      annotation = @CheckAnnotation(require = CheckForNull.class),
      type = @TypeRestrict(int.class))
  public @interface Anno {
  }

  @CheckForNull
  @Anno
  transient int i;
}
