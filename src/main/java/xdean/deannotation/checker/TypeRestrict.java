package xdean.deannotation.checker;

import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@Retention(CLASS)
public @interface TypeRestrict {

  class Irrelevant {
  }

  enum Type {
    SUPER,
    EQUAL,
    EXTEND;
  }

  Class<?> value() default Irrelevant.class;

  Type type() default Type.EQUAL;

  interface Handler {
    static boolean match(Class<?> clz, TypeRestrict res, Elements es, Types ts) {
      return match(es.getTypeElement(clz.getCanonicalName()).asType(), res, es, ts);
    }

    static boolean match(TypeMirror type, TypeRestrict res, Elements es, Types ts) {
      if (res.value() == Irrelevant.class) {
        return true;
      }
      type = ts.erasure(type);
      TypeMirror target = ts.erasure(es.getTypeElement(res.value().getCanonicalName()).asType());
      switch (res.type()) {
      case EQUAL:
        return ts.isSameType(type, target);
      case EXTEND:
        return ts.isAssignable(type, target);
      case SUPER:
        return ts.isAssignable(target, type);
      default:
        throw new IllegalStateException();
      }
    }
  }
}
