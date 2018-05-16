package xdean.deannotation.checker.checkField;

import static javax.lang.model.element.Modifier.PUBLIC;

import javax.annotation.CheckForNull;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckModifier;

public class BadUsage1 {

  @CheckField(modifier = @CheckModifier(require = PUBLIC), annotation = @CheckAnnotation(require = CheckForNull.class))
  public @interface Anno {

  }

  @Anno
  int i;
}
