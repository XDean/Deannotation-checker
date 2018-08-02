package xdean.deannotation.checker.checkEnclose;

import static javax.lang.model.element.Modifier.PRIVATE;
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

  @CheckEnclose(
      fields = @CheckField(modifier = @CheckModifier(require = PROTECTED), type = @CheckType(value = int.class)),
      constructors = @CheckConstructor(modifier = @CheckModifier(require = PUBLIC)),
      methods = @CheckMethod(argCount = 0, returnType = @CheckType(void.class), name = @CheckName("not")),
      classes = @CheckClass(type = @CheckType(value = Anno.class, type = Type.EXTEND_ALL)),
      type = CheckEnclose.Type.ONE)
  @interface Anno2 {
  }

  @CheckEnclose(
      fields = @CheckField(modifier = @CheckModifier(require = PROTECTED), type = @CheckType(value = void.class)),
      constructors = @CheckConstructor(modifier = @CheckModifier(require = PRIVATE)),
      methods = @CheckMethod(argCount = 0, returnType = @CheckType(void.class), name = @CheckName("func")),
      classes = @CheckClass(type = @CheckType(value = Comparator.class, type = Type.EXTEND_ALL)),
      type = CheckEnclose.Type.ONE)
  @interface Anno3 {
  }

  @CheckEnclose(
      constructors = {
          @CheckConstructor(modifier = @CheckModifier(require = PRIVATE)),
          @CheckConstructor(modifier = @CheckModifier(require = PUBLIC))
      },
      type = CheckEnclose.Type.ONE)
  @interface Anno4 {
  }

  @Anno
  @Anno2
  @Anno3
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
