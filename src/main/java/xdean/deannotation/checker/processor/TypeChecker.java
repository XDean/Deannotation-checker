package xdean.deannotation.checker.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.ElementUtil;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.CheckType.Irrelevant;
import xdean.deannotation.checker.processor.common.Checker;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckType.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TypeChecker extends Checker<CheckType> {
  private TypeMirror irrelevant;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    irrelevant = elements.getTypeElement(Irrelevant.class.getCanonicalName()).asType();
  }

  @Override
  protected void process(RoundEnvironment env, CheckType ct, AnnotationMirror mid, Element element) throws AssertException {
    check(ct, element, element.asType());
  }

  /**
   * Check the type on the element with given {@link CheckType}.
   * 
   * @param ct the annotation
   * @param element the annotated element
   * @param type the type to check
   */
  public void check(CheckType ct, Element element, TypeMirror type) {
    TypeMirror target = erasure(types, ElementUtil.getAnnotationClassValue(elements, ct, r -> r.value()));
    if (types.isSameType(target, irrelevant)) {
      return;
    }
    type = erasure(types, type);
    switch (ct.type()) {
    case EQUAL:
      assertThat(types.isSameType(type, target)).log("Must be " + type.toString(), element);
      break;
    case EXTEND:
      assertThat(types.isAssignable(type, target)).log("Must extend " + type.toString(), element);
      break;
    case SUPER:
      assertThat(types.isAssignable(target, type)).log("Must super " + type.toString(), element);
      break;
    default:
      throw new IllegalStateException();
    }
  }

  public static TypeMirror erasure(Types types, TypeMirror tm) {
    return tm.getKind() == TypeKind.VOID ? tm : types.erasure(tm);
  }
}
