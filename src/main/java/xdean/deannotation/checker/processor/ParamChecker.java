package xdean.deannotation.checker.processor;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckType;
import xdean.deannotation.checker.processor.common.Checker;
import xdean.deannotation.checker.processor.common.CheckerInject;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckType.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParamChecker extends Checker<CheckType> {

  @CheckerInject
  AnnotationChecker annotationChecker;

  @CheckerInject
  TypeChecker typeChecker;

  @Override
  protected void process(RoundEnvironment env, CheckType ct, AnnotationMirror mid, Element element) throws AssertException {

  }
}
