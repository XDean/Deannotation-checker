package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.Checker;
import xdean.deannotation.checker.processor.common.CheckerInject;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckField.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class FieldChecker extends Checker<CheckField> {

  private @CheckerInject NameChecker nameChecker;
  private @CheckerInject AnnotationChecker annotationChecker;
  private @CheckerInject ModifierChecker modifierChecker;
  private @CheckerInject TypeChecker typeChecker;

  @Override
  public CheckResult check(RoundEnvironment env, CheckField cf, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element.getKind() == ElementKind.FIELD).doNoThing();
    return CheckResult.Builder.create(element)
        .add(nameChecker.check(env, cf.name(), mid, element))
        .add(annotationChecker.check(env, cf.annotation(), mid, element))
        .add(modifierChecker.check(env, cf.modifier(), mid, element))
        .add(typeChecker.check(env, cf.type(), mid, element))
        .build(cf.message());
  }

  @Override
  public List<String> checkDefine(CheckField t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    list.addAll(attributeBadDefine(nameChecker.checkDefine(t.name(), annotatedElement), "name"));
    list.addAll(attributeBadDefine(annotationChecker.checkDefine(t.annotation(), annotatedElement), "annotation"));
    list.addAll(attributeBadDefine(modifierChecker.checkDefine(t.modifier(), annotatedElement), "modifier"));
    list.addAll(attributeBadDefine(typeChecker.checkDefine(t.type(), annotatedElement), "type"));
    return list;
  }
}
