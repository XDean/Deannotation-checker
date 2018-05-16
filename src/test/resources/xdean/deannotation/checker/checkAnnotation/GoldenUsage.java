package xdean.deannotation.checker.checkAnnotation;

import java.util.List;

import org.junit.Test;

import xdean.deannotation.checker.CheckAnnotation;

public class GoldenUsage {
  @CheckAnnotation(require = Test.class, forbid = Override.class)
  public @interface Anno {
  }

  @Test
  @Anno
  public synchronized List<String> func(Integer i, Object o) {
    return null;
  }
}
