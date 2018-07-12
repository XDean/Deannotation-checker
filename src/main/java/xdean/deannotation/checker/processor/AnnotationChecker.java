package xdean.deannotation.checker.processor;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.ElementUtil;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.CheckResult.Builder;
import xdean.deannotation.checker.processor.common.Checker;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckAnnotation.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationChecker extends Checker<CheckAnnotation> {

  @Override
  public CheckResult check(RoundEnvironment env, CheckAnnotation ca, Element element) throws AssertException {
    Builder builder = CheckResult.Builder.create(element);
    ElementUtil.getAnnotationClassArray(elements, ca, CheckAnnotation::require)
        .forEach(m -> builder.addIf(!ElementUtil.getAnnotationMirror(element, m).isPresent(), "Annotation required: " + m));
    ElementUtil.getAnnotationClassArray(elements, ca, CheckAnnotation::forbid)
        .forEach(m -> builder.addIf(ElementUtil.getAnnotationMirror(element, m).isPresent(), "Annotation forbidden: " + m));
    return builder.build();
  }
}
