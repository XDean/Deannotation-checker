package xdean.deannotation.checker.checkEnclose;

import xdean.deannotation.checker.CheckClass;
import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckEnclose;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckName;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.CheckType;

public class BadDefine {
  @CheckEnclose(
      fields = @CheckField(name = @CheckName("**")),
      constructors = @CheckConstructor(argCount = 0, args = @CheckParam),
      methods = @CheckMethod(returnType = @CheckType({})),
      classes = @CheckClass(type = @CheckType({ int.class, byte.class })))
  @interface Anno {
  }
}
