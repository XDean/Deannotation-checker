package xdean.deannotation.checker;

import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import xdean.annotation.processor.toolkit.ElementUtil;

@Retention(CLASS)
public @interface CheckType {

  class Irrelevant {
    private static transient TypeMirror TYPE;

    private static TypeMirror get(Elements es, Types ts) {
      if (TYPE == null) {
        synchronized (Irrelevant.class) {
          if (TYPE == null) {
            TYPE = es.getTypeElement(Irrelevant.class.getCanonicalName()).asType();
          }
        }
      }
      return TYPE;
    }
  }

  enum Type {
    SUPER, EQUAL, EXTEND;
  }

  Class<?> value() default Irrelevant.class;

  Type type() default Type.EQUAL;

  interface Handler {
    static boolean match(Class<?> clz, CheckType res, Elements es, Types ts) {
      return match(es.getTypeElement(clz.getCanonicalName()).asType(), res, es, ts);
    }

    static boolean match(TypeMirror type, CheckType res, Elements es, Types ts) {
      TypeMirror target = erasure(ts, ElementUtil.getAnnotationClassValue(es, res, r -> r.value()));
      TypeMirror irr = Irrelevant.get(es, ts);
      if (ts.isSameType(target, irr)) {
        return true;
      }
      type = erasure(ts, type);
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

    static String toString(CheckType res, Elements es, Types ts) {
      TypeMirror tm = erasure(ts, ElementUtil.getAnnotationClassValue(es, res, r -> r.value()));
      switch (res.type()) {
      case EQUAL:
        return "type " + tm.toString();
      case EXTEND:
        return "type extends " + tm.toString();
      case SUPER:
        return "type super " + tm.toString();
      default:
        throw new IllegalStateException();
      }
    }

    static TypeMirror erasure(Types types, TypeMirror tm) {
      return tm.getKind() == TypeKind.VOID ? tm : types.erasure(tm);
    }
  }
}
