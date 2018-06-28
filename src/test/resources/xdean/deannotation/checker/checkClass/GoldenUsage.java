package xdean.deannotation.checker.checkClass;

import java.io.Serializable;

import javax.lang.model.element.Modifier;

import org.junit.runner.RunWith;
import org.junit.runner.Runner;

import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.CheckClass;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class GoldenUsage {
  @CheckClass(annotation = @CheckAnnotation(require = RunWith.class),
      modifier = @CheckModifier(require = Modifier.PUBLIC, forbid = javax.lang.model.element.Modifier.ABSTRACT),
      type = @CheckType(value = { Serializable.class, A.class }, type = Type.EXTEND_ALL))
  public @interface Anno {
  }

  interface A extends Serializable {

  }

  @Anno
  @RunWith(Runner.class)
  public static class B implements A {

  }
}
