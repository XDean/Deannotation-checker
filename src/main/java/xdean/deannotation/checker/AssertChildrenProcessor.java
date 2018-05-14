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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.ElementUtil;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.annotation.processor.toolkit.meta.AbstractMetaProcessor;

@AutoService(Processor.class)
@SupportedMetaAnnotation(AssertChildren.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AssertChildrenProcessor extends AbstractMetaProcessor<AssertChildren> {

  @Override
  protected void process(RoundEnvironment env, AssertChildren ac, AnnotationMirror mid, Element element) {
    TypeElement type = (TypeElement) element;
    List<TypeMirror> annotated = ElementUtil.getAnnotationClassArray(elements, ac, a -> a.annotated());
    ElementUtil.getAllSubClasses(types, env, type.asType())
        .filter(te -> ac.includeAbstract() || !te.getModifiers().contains(Modifier.ABSTRACT))
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
