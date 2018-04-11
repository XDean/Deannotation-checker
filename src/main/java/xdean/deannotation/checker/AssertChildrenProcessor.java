package xdean.deannotation.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.ElementUtil;
import xdean.annotation.processor.toolkit.NestCompileFile;
import xdean.annotation.processor.toolkit.XAbstractProcessor;
import xdean.annotation.processor.toolkit.annotation.SupportedAnnotation;

@AutoService(Processor.class)
@SupportedAnnotation(AssertChildren.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AssertChildrenProcessor extends XAbstractProcessor {
  private static final String RECORD_FILE = "META-INF/xdean/deannotation/checker/AssertChildren";
  private final NestCompileFile file = new NestCompileFile(RECORD_FILE);

  @Override
  public boolean processActual(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) throws AssertException {
    if (roundEnv.processingOver()) {
      return false;
    }
    List<TypeElement> types = new ArrayList<>();
    roundEnv.getElementsAnnotatedWith(AssertChildren.class)
        .stream()
        .filter(e -> TypeElement.class.isInstance(e))
        .map(e -> (TypeElement) e)
        .forEach(types::add);
    assertDo(() -> file.readLines()
        .map(s -> elements.getTypeElement(s))
        .forEach(types::add))
            .todo(() -> error().log("Error to read " + RECORD_FILE));
    types.forEach(e -> check(e, roundEnv));
    return true;
  }

  private void check(TypeElement type, RoundEnvironment roundEnv) {
    AssertChildren ac = type.getAnnotation(AssertChildren.class);
    List<TypeMirror> annotated = ElementUtil.getAnnotationClassArray(elements, ac, a -> a.annotated());
    ElementUtil.getAllSubClasses(types, roundEnv, type.asType())
        .filter(te -> ac.includeInterface() || te.getKind() != ElementKind.INTERFACE)
        .filter(te -> !Objects.equals(te, type))
        .forEach(te -> handleAssert(() -> {
          Set<TypeMirror> annotationTypes = ElementUtil.getInheritAnnotationMirrors(te)
              .stream()
              .map(am -> (TypeMirror) am.getAnnotationType())
              .collect(Collectors.toSet());
          ArrayList<TypeMirror> copy = new ArrayList<>(annotated);
          copy.removeAll(annotationTypes);
          if (!copy.isEmpty()) {
            error().log("Must annotated with " + copy.stream().map(tm -> "@" + tm.toString())
                .collect(Collectors.joining(", ")), te);
          }
        }));
  }
}
