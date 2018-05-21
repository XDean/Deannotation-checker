package xdean.deannotation.checker.processor;

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
import xdean.deannotation.checker.CheckParam;
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
  protected void process(RoundEnvironment env, CheckParam cp, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element.getKind() == ElementKind.PARAMETER).doNoThing();
    annotationChecker.process(env, cp.annotation(), mid, element);
    typeChecker.process(env, cp.type(), mid, element);
  }

  @Override
  public List<String> checkDefine(CheckParam t, Element annotatedElement) {
    return attributeBadDefine(typeChecker.checkDefine(t.type(), annotatedElement), "type");
  }
}
