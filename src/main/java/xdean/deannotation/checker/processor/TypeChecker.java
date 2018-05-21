package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import xdean.deannotation.checker.CheckType.Type;
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

  @Override
  public List<String> checkDefine(CheckType ct, Element element) {
    List<String> list = new ArrayList<>();
    List<TypeMirror> baseTypes = getBaseTypes(ct);
    if (baseTypes.size() == 0) {
      list.add("@CheckType must define target type");
    }
    if (baseTypes.size() != 1 && (ct.type() == Type.EQUAL || ct.type() == Type.SUPER)) {
      list.add("@CheckType with type EQUAL or SUPER can only define one target type");
    }
    return list;
  }

  /**
   * Check the type on the element with given {@link CheckType}.
   * 
   * @param ct the annotation
   * @param element the annotated element
   * @param typeToCheck the type to check
   * @param name name for this type to log
   */
  public void check(CheckType ct, Element element, TypeMirror typeToCheck, String name) {
    List<TypeMirror> baseTypes = getBaseTypes(ct);
    if (baseTypes.stream().anyMatch(t -> types.isSameType(t, irrelevant))) {
      return;
    }
    TypeMirror type = TypeUtil.erasure(types, typeToCheck);
    TypeMirror target = baseTypes.stream().findFirst().get();
    switch (ct.type()) {
    case EQUAL:
      assertThat(types.isSameType(type, target)).log(name + " must be " + target.toString(), element);
      break;
    case SUPER:
      assertThat(types.isAssignable(target, type)).log(name + "must super " + target.toString(), element);
      break;
    case EXTEND_ALL:
      baseTypes.forEach(eachTarget -> handleAssert(
          () -> assertThat(types.isAssignable(type, eachTarget)).log(name + "must extend " + eachTarget.toString(), element)));
      break;
    case EXTEND_ONE:
      assertThat(baseTypes.stream().anyMatch(eachTarget -> types.isAssignable(type, eachTarget)))
          .log(name + " must extend one of (" + baseTypes.stream().map(tm -> tm.toString()).collect(Collectors.joining(", "))
              + ")", element);
      break;
    default:
      throw new IllegalStateException();
    }
  }

  private List<TypeMirror> getBaseTypes(CheckType ct) {
    return ElementUtil.getAnnotationClassArray(elements, ct, r -> r.value())
        .stream()
        .map(tm -> TypeUtil.erasure(types, tm))
        .collect(Collectors.toList());
  }
}
