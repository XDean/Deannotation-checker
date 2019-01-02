package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.Arrays;
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
import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckParam;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.CheckResult.Builder;
import xdean.deannotation.checker.processor.common.Checker;
import xdean.deannotation.checker.processor.common.CheckerInject;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckConstructor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ConstructorChecker extends Checker<CheckConstructor> {

  @CheckerInject
  AnnotationChecker annotationChecker;

  @CheckerInject
  ModifierChecker modifierChecker;

  @CheckerInject
  ParamChecker paramChecker;

  @Override
  public CheckResult check(RoundEnvironment env, CheckConstructor cc, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof ExecutableElement).doNoThing();
    assertThat(element.getKind() == ElementKind.CONSTRUCTOR).doNoThing();

    Builder builder = CheckResult.Builder.create(element);
    ExecutableElement method = (ExecutableElement) element;
    builder.add(annotationChecker.check(env, cc.annotation(), mid, element))
        .add(modifierChecker.check(env, cc.modifier(), mid, element));
    List<? extends VariableElement> parameters = method.getParameters();
    CheckParam[] argTypes = cc.args();
    if (cc.argCount() < 0) {
      builder.addIf(argTypes.length > parameters.size(), "Must have at least " + argTypes.length + " arguments");
    } else {
      builder.addIfNot(cc.argCount() == parameters.size(), "Must only have " + cc.argCount() + " arguments");
    }
    for (int i = 0; i < argTypes.length && i < parameters.size(); i++) {
      CheckParam res = argTypes[i];
      VariableElement param = parameters.get(i);
      builder.add(paramChecker.check(env, res, mid, param));
    }
    return builder.build();
  }

  @Override
  public List<String> checkDefine(CheckConstructor cc, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    if (cc.argCount() >= 0 && cc.argCount() < cc.args().length) {
      list.add("argCount must not smaller than argTypes length");
    }
    list.addAll(attributeBadDefine(annotationChecker.checkDefine(cc.annotation(), annotatedElement), "annotation"));
    list.addAll(attributeBadDefine(modifierChecker.checkDefine(cc.modifier(), annotatedElement), "modifier"));
    Arrays.stream(cc.args())
        .forEach(cp -> list.addAll(attributeBadDefine(paramChecker.checkDefine(cp, annotatedElement), "args")));
    return list;
  }
}
