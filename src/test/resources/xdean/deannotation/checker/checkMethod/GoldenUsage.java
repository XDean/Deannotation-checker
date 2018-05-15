package xdean.deannotation.checker.checkMethod;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.SYNCHRONIZED;

import java.util.List;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.TypeRestrict;
import xdean.deannotation.checker.TypeRestrict.Type;

public class GoldenUsage {
  @CheckMethod(modifier = @CheckModifier(required = SYNCHRONIZED, forbidden = ABSTRACT),
      returnType = @TypeRestrict(List.class),
      argCount = 2, argTypes = {
          @TypeRestrict(value = Number.class, type = Type.EXTEND),
          @TypeRestrict(value = String.class, type = Type.SUPER)
      })
  public @interface Anno {
  }

  @Anno
  public synchronized List<String> func(Integer i, Object o) {
    return null;
  }
}
