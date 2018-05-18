package xdean.deannotation.checker.checkMethod;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.SYNCHRONIZED;

import java.util.List;

import org.junit.Test;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class GoldenUsage {
  @CheckMethod(
      modifier = @CheckModifier(require = SYNCHRONIZED, forbid = ABSTRACT),
      annotation = @CheckAnnotation(require = Test.class),
      returnType = @CheckType(List.class),
      argCount = 2, argTypes = {
          @CheckParam(type = @CheckType(value = Number.class, type = Type.EXTEND)),
          @CheckParam(type = @CheckType(value = String.class, type = Type.SUPER))
      })
  public @interface Anno {
  }

  @Test
  @Anno
  public synchronized List<String> func(Integer i, Object o) {
    return null;
  }
}
