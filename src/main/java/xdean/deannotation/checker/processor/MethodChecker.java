package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.CheckResult.Builder;
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
  public CheckResult check(RoundEnvironment env, CheckMethod am, Element element) throws AssertException {
    assertThat(element instanceof ExecutableElement).doNoThing();
    assertThat(element.getKind() == ElementKind.METHOD).doNoThing();

    Builder builder = CheckResult.Builder.create();
    ExecutableElement method = (ExecutableElement) element;
    builder.add(annotationChecker.check(env, am.annotation(), element))
        .add(modifierChecker.check(env, am.modifier(), element))
        .add(typeChecker.check(am.returnType(), method.getReturnType(), "Return type"));
    List<? extends VariableElement> parameters = method.getParameters();
    CheckParam[] argTypes = am.args();
    if (am.argCount() < 0) {
      builder.addIf(argTypes.length > parameters.size(), "Must have at least " + argTypes.length + " arguments");
    } else {
      builder.addIfNot(am.argCount() == parameters.size(), "Must only have " + am.argCount() + " arguments");
    }
    for (int i = 0; i < argTypes.length; i++) {
      CheckParam res = argTypes[i];
      VariableElement param = parameters.get(i);
      builder.add(paramChecker.check(env, res, param));
    }
    return builder.build();
  }

  @Override
  public List<String> checkDefine(CheckMethod t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    if (t.argCount() >= 0 && t.argCount() < t.args().length) {
      list.add("argCount must not smaller than argTypes length");
    }
    list.addAll(attributeBadDefine(annotationChecker.checkDefine(t.annotation(), annotatedElement), "annotation"));
    list.addAll(attributeBadDefine(modifierChecker.checkDefine(t.modifier(), annotatedElement), "modifier"));
    list.addAll(attributeBadDefine(typeChecker.checkDefine(t.returnType(), annotatedElement), "returnType"));
    Arrays.stream(t.args())
        .forEach(cp -> list.addAll(attributeBadDefine(paramChecker.checkDefine(cp, annotatedElement), "args")));
    return list;
  }
}
