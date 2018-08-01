package xdean.deannotation.checker.checkName;

import xdean.deannotation.checker.CheckName;

public class GoldenUsage {

  @CheckName("test.*")
  @interface Anno {

  }

  @Anno
  void testMethod(@Anno int testParam) {

  }

  @Anno
  class TestClass {
    @Anno
    int testField;
  }
}
