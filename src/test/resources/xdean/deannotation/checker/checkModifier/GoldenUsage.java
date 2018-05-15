package xdean.deannotation.checker.checkModifier;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.SYNCHRONIZED;

import java.util.List;

import xdean.deannotation.checker.CheckModifier;

public class GoldenUsage {
  @CheckModifier(require = SYNCHRONIZED, forbid = ABSTRACT)
  public @interface Anno {
  }

  @Anno
  public synchronized List<String> func(Integer i, Object o) {
    return null;
  }
}
