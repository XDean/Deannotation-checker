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

  private @CheckerInject NameChecker nameChecker;
  private @CheckerInject AnnotationChecker annotationChecker;
  private @CheckerInject ModifierChecker modifierChecker;
  private @CheckerInject TypeChecker typeChecker;
  private @CheckerInject ParamChecker paramChecker;

  @Override
  public CheckResult check(RoundEnvironment env, CheckMethod cm, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof ExecutableElement).doNoThing();
    assertThat(element.getKind() == ElementKind.METHOD).doNoThing();

    Builder builder = CheckResult.Builder.create(element);
    ExecutableElement method = (ExecutableElement) element;
    builder
        .add(nameChecker.check(env, cm.name(), mid, element))
        .add(annotationChecker.check(env, cm.annotation(), mid, element))
        .add(modifierChecker.check(env, cm.modifier(), mid, element))
        .add(typeChecker.check(element, cm.returnType(), method.getReturnType(), "Return type"));
    List<? extends VariableElement> parameters = method.getParameters();
    CheckParam[] argTypes = cm.args();
    if (cm.argCount() < 0) {
      builder.addIf(argTypes.length > parameters.size(), "Must have at least " + argTypes.length + " arguments");
    } else {
      builder.addIfNot(cm.argCount() == parameters.size(), "Must only have " + cm.argCount() + " arguments");
    }
    for (int i = 0; i < argTypes.length; i++) {
      CheckParam res = argTypes[i];
      VariableElement param = parameters.get(i);
      builder.add(paramChecker.check(env, res, mid, param));
    }
    return builder.build();
  }

  @Override
  public List<String> checkDefine(CheckMethod t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    if (t.argCount() >= 0 && t.argCount() < t.args().length) {
      list.add("argCount must not smaller than argTypes length");
    }
    list.addAll(attributeBadDefine(nameChecker.checkDefine(t.name(), annotatedElement), "name"));
    list.addAll(attributeBadDefine(annotationChecker.checkDefine(t.annotation(), annotatedElement), "annotation"));
    list.addAll(attributeBadDefine(modifierChecker.checkDefine(t.modifier(), annotatedElement), "modifier"));
    list.addAll(attributeBadDefine(typeChecker.checkDefine(t.returnType(), annotatedElement), "returnType"));
    Arrays.stream(t.args())
        .forEach(cp -> list.addAll(attributeBadDefine(paramChecker.checkDefine(cp, annotatedElement), "args")));
    return list;
  }
}
