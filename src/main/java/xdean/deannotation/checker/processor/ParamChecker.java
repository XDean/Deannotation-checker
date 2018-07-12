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
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.Checker;
import xdean.deannotation.checker.processor.common.CheckerInject;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckParam.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParamChecker extends Checker<CheckParam> {

  @CheckerInject
  AnnotationChecker annotationChecker;

  @CheckerInject
  TypeChecker typeChecker;

  @Override
  public CheckResult check(RoundEnvironment env, CheckParam cp, Element element) throws AssertException {
    assertThat(element.getKind() == ElementKind.PARAMETER).doNoThing();
    return CheckResult.Builder.create(element)
        .add(annotationChecker.check(env, cp.annotation(), element))
        .add(typeChecker.check(env, cp.type(), element))
        .build();
  }

  @Override
  public List<String> checkDefine(CheckParam t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    list.addAll(attributeBadDefine(annotationChecker.checkDefine(t.annotation(), annotatedElement), "annotation"));
    list.addAll(attributeBadDefine(typeChecker.checkDefine(t.type(), annotatedElement), "type"));
    return list;
  }
}
