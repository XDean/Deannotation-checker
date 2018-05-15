package xdean.deannotation.checker.processor;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.ElementUtil;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckAnnotation;
import xdean.deannotation.checker.processor.common.Checker;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckAnnotation.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationChecker extends Checker<CheckAnnotation> {

  @Override
  protected void process(RoundEnvironment env, CheckAnnotation ca, AnnotationMirror mid, Element element) throws AssertException {
    ElementUtil.getAnnotationClassArray(elements, ca, c -> c.require())
        .forEach(m -> assertThat(ElementUtil.getAnnotationMirror(element, m).isPresent())
            .log("Annotation required: " + m, element));
    ElementUtil.getAnnotationClassArray(elements, ca, c -> c.forbid())
        .forEach(m -> assertThat(!ElementUtil.getAnnotationMirror(element, m).isPresent())
            .log("Annotation forbidden: " + m, element));
  }
}
