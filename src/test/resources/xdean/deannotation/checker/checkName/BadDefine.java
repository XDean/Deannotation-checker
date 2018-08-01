package xdean.deannotation.checker.checkName;

import xdean.deannotation.checker.CheckName;

public class BadDefine {
  @CheckName(value = "***", ignoreCase = false)
  @interface Anno {
  }
}
