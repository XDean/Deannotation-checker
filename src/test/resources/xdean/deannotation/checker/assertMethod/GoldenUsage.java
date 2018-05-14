package xdean.deannotation.checker.assertMethod;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.SYNCHRONIZED;

import java.util.List;

import xdean.deannotation.checker.AssertMethod;
import xdean.deannotation.checker.TypeRestrict;
import xdean.deannotation.checker.TypeRestrict.Type;

public class GoldenUsage {
  @Init
  public void init() {
  }

  @AssertMethod(requiredModifiers = SYNCHRONIZED, forbiddenModifiers = ABSTRACT,
      returnType = @TypeRestrict(List.class),
      argCount = 2, argTypes = {
          @TypeRestrict(value = Number.class, type = Type.EXTEND),
          @TypeRestrict(value = String.class, type = Type.SUPER)
      })
  public synchronized List<String> func(Integer i, Object o) {
    return null;
  }
}
