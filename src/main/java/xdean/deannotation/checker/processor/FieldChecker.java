package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
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

  @CheckerInject
  AnnotationChecker annotationChecker;

  @CheckerInject
  ModifierChecker modifierChecker;

  @CheckerInject
  TypeChecker typeChecker;

  @Override
  public CheckResult check(RoundEnvironment env, CheckField cf, Element element) throws AssertException {
    assertThat(element.getKind() == ElementKind.FIELD).doNoThing();
    return CheckResult.Builder.create(element)
        .add(annotationChecker.check(env, cf.annotation(), element))
        .add(modifierChecker.check(env, cf.modifier(), element))
        .add(typeChecker.check(env, cf.type(), element))
        .build();
  }

  @Override
  public List<String> checkDefine(CheckField t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    list.addAll(attributeBadDefine(annotationChecker.checkDefine(t.annotation(), annotatedElement), "annotation"));
    list.addAll(attributeBadDefine(modifierChecker.checkDefine(t.modifier(), annotatedElement), "modifier"));
    list.addAll(attributeBadDefine(typeChecker.checkDefine(t.type(), annotatedElement), "type"));
    return list;
  }
}
