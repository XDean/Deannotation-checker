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

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.ElementUtil;
import xdean.annotation.processor.toolkit.TypeUtil;
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
    TypeMirror type = element.asType();
    /*
     * give package or executable to Types will lead error, see
     * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/com/sun/tools/
     * javac/model/JavacTypes.java#JavacTypes.isAssignable%28javax.lang.model.type.TypeMirror%
     * 2Cjavax.lang.model.type. TypeMirror%29
     */
    assertThat(!(type.getKind() == TypeKind.PACKAGE || type.getKind() == TypeKind.EXECUTABLE)).doNoThing();
    check(ct, element, type, "Type");
  }

  /**
   * Check the type on the element with given {@link CheckType}.
   * 
   * @param ct the annotation
   * @param element the annotated element
   * @param type the type to check
   * @param name name for this type to log
   */
  public void check(CheckType ct, Element element, TypeMirror type, String name) {
    TypeMirror target = TypeUtil.erasure(types, ElementUtil.getAnnotationClassValue(elements, ct, r -> r.value()));
    if (types.isSameType(target, irrelevant)) {
      return;
    }
    type = TypeUtil.erasure(types, type);
    switch (ct.type()) {
    case EQUAL:
      assertThat(types.isSameType(type, target)).log(name + " must be " + target.toString(), element);
      break;
    case EXTEND:
      assertThat(types.isAssignable(type, target)).log(name + "must extend " + target.toString(), element);
      break;
    case SUPER:
      assertThat(types.isAssignable(target, type)).log(name + "must super " + target.toString(), element);
      break;
    default:
      throw new IllegalStateException();
    }
  }
}
