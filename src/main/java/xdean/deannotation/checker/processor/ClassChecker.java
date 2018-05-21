package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.ElementUtil;
import xdean.annotation.processor.toolkit.TypeUtil;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckClass;
import xdean.deannotation.checker.processor.common.Checker;
import xdean.deannotation.checker.processor.common.CheckerInject;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckClass.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ClassChecker extends Checker<CheckClass> {

  @CheckerInject
  AnnotationChecker annotationChecker;

  @CheckerInject
  ModifierChecker modifierChecker;

  @Override
  protected void process(RoundEnvironment env, CheckClass cc, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof TypeElement).doNoThing();
    annotationChecker.process(env, cc.annotation(), mid, element);
    modifierChecker.process(env, cc.modifier(), mid, element);
    TypeMirror type = TypeUtil.erasure(types, element.asType());
    ElementUtil.getAnnotationClassArray(elements, cc, c -> c.implement())
        .forEach(tm -> {
          TypeMirror target = TypeUtil.erasure(types, tm);
          assertThat(types.isAssignable(type, target)).log("Must implements: " + tm.toString(), element);
        });
  }

  @Override
  public List<String> checkDefine(CheckClass t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    list.addAll(attributeBadDefine(annotationChecker.checkDefine(t.annotation(), annotatedElement), "annotation"));
    list.addAll(attributeBadDefine(modifierChecker.checkDefine(t.modifier(), annotatedElement), "modifier"));
    return list;
  }
}
