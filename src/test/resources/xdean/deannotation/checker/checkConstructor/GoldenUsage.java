package xdean.deannotation.checker.checkConstructor;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class GoldenUsage {
  @CheckConstructor(
      modifier = @CheckModifier(require = PUBLIC, forbid = ABSTRACT),
      annotation = @CheckAnnotation(require = Other.class),
      argCount = 2, args = {
          @CheckParam(type = @CheckType(value = Number.class, type = Type.EXTEND_ALL)),
          @CheckParam(type = @CheckType(value = String.class, type = Type.SUPER))
      })
  public @interface Anno {
  }

  public @interface Other {
  }

  @Other
  @Anno
  public GoldenUsage(Integer i, Object o) {
  }
}
