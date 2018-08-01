package xdean.deannotation.checker.checkName;

import xdean.deannotation.checker.CheckName;

public class BadUsage {

  @CheckName(value = "test.*", ignoreCase = false)
  @interface Anno {

  }

  @Anno
  void func(@Anno int p) {

  }

  @Anno
  class TestClass {
    @Anno
    int i1, i2;
  }
}
