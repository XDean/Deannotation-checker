package xdean.deannotation.checker.processor;

import java.util.List;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.processor.common.Checker;
import xdean.deannotation.checker.processor.common.CheckerInject;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckMethod.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MethodChecker extends Checker<CheckMethod> {

  @CheckerInject
  AnnotationChecker annotationChecker;

  @CheckerInject
  ModifierChecker modifierChecker;

  @CheckerInject
  TypeChecker typeChecker;

  @CheckerInject
  ParamChecker paramChecker;

  @Override
  protected void process(RoundEnvironment env, CheckMethod am, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof ExecutableElement).doNoThing();
    assertThat(element.getKind() == ElementKind.METHOD).doNoThing();
    ExecutableElement method = (ExecutableElement) element;
    annotationChecker.process(env, am.annotation(), mid, element);
    modifierChecker.process(env, am.modifier(), mid, element);
    typeChecker.check(am.returnType(), element, method.getReturnType(), "Return type");
    List<? extends VariableElement> parameters = method.getParameters();
    CheckParam[] argTypes = am.args();
    assertThat((am.argCount() < 0 && argTypes.length <= parameters.size()) || am.argCount() == parameters.size())
        .log("Must only have " + am.argCount() + " arguments.", element);
    for (int i = 0; i < argTypes.length; i++) {
      CheckParam res = argTypes[i];
      VariableElement param = parameters.get(i);
      paramChecker.process(env, res, mid, param);
    }
  }

  @Override
  protected void processMeta(RoundEnvironment env, CheckMethod am, Element element) throws AssertException {
    assertThat(am.argCount() < 0 || am.argCount() >= am.args().length)
        .log("argCount must not greater than argTypes length.", element, CheckMethod.class);
  }
}
