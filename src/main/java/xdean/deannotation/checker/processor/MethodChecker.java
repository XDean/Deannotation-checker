package xdean.deannotation.checker.processor;

import java.util.List;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckType;
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

  @Override
  protected void process(RoundEnvironment env, CheckMethod am, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof ExecutableElement).doNoThing();
    ExecutableElement method = (ExecutableElement) element;
    annotationChecker.process(env, am.annotation(), mid, element);
    modifierChecker.process(env, am.modifier(), mid, element);
    typeChecker.check(am.returnType(), element, method.getReturnType());
    List<? extends VariableElement> parameters = method.getParameters();
    CheckType[] argTypes = am.argTypes();
    assertThat((am.argCount() < 0 && argTypes.length <= parameters.size()) || am.argCount() == parameters.size())
        .log("Must only have " + am.argCount() + " arguments.", element);
    for (int i = 0; i < argTypes.length; i++) {
      CheckType res = argTypes[i];
      VariableElement param = parameters.get(i);
      typeChecker.check(res, param, param.asType());
    }
  }

  @Override
  protected void processMeta(RoundEnvironment env, CheckMethod am, Element element) throws AssertException {
    assertThat(am.argCount() < 0 || am.argCount() >= am.argTypes().length)
        .log("argCount must not greater than argTypes length.", element, CheckMethod.class);
  }
}
