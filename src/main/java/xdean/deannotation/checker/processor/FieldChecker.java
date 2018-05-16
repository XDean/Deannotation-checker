package xdean.deannotation.checker.processor;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckType;
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

  @Override
  protected void process(RoundEnvironment env, CheckField cf, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof VariableElement).doNoThing();
    annotationChecker.process(env, cf.annotation(), mid, element);
    modifierChecker.process(env, cf.modifier(), mid, element);
    assertThat(CheckType.Handler.match(element.asType(), cf.type(), elements, types))
        .log("Field must be " + CheckType.Handler.toString(cf.type(), elements, types), element);
  }
}
