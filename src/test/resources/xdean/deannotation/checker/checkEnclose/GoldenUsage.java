package xdean.deannotation.checker.checkEnclose;

import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;

import java.util.Comparator;

import xdean.deannotation.checker.CheckClass;
import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckEnclose;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.CheckName;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class GoldenUsage {

  @CheckEnclose(
      fields = @CheckField(modifier = @CheckModifier(require = PROTECTED), type = @CheckType(value = int.class)),
      constructors = @CheckConstructor(modifier = @CheckModifier(require = PUBLIC)),
      methods = @CheckMethod(argCount = 0, returnType = @CheckType(void.class), name = @CheckName("func")),
      classes = @CheckClass(type = @CheckType(value = Comparator.class, type = Type.EXTEND_ALL)))
  @interface Anno {

  }

  @Anno
  public static abstract class TestClass {
    protected int testField;

    public void func() {
    }

    public class Inner implements Comparator<TestClass> {
      @Override
      public int compare(TestClass o1, TestClass o2) {
        return 0;
      }
    }
  }
}
