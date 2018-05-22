package xdean.deannotation.checker.checkType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Type;

public class GoldenUsage {
  @CheckType(value = Number.class)
  public @interface Anno1 {
  }

  @CheckType(value = { Number.class, List.class }, type = Type.EXTEND_ALL)
  public @interface Anno2 {
  }

  @CheckType(value = Number.class, type = Type.SUPER)
  public @interface Anno3 {
  }

  @CheckType(value = { Number.class, List.class }, type = Type.EXTEND_ONE)
  public @interface Anno4 {
  }

  @Anno2
  static abstract class A extends Number implements List<Integer> {
  }

  public void func(@Anno1 Number i) {
  }

  @Anno3
  Serializable o;

  @Anno4
  ArrayList<String> al;
}
