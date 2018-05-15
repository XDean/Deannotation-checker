package xdean.deannotation.checker.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.annotation.processor.toolkit.meta.AbstractMetaProcessor;
import xdean.deannotation.checker.AssertMethod;
import xdean.deannotation.checker.TypeRestrict;

@AutoService(Processor.class)
@SupportedMetaAnnotation(AssertMethod.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AssertMethodProcessor extends AbstractMetaProcessor<AssertMethod> {

  @Override
  protected void process(RoundEnvironment env, AssertMethod am, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof ExecutableElement).doNoThing();
    ExecutableElement method = (ExecutableElement) element;
    Set<Modifier> modifiers = method.getModifiers();
    Arrays.stream(am.requiredModifiers())
        .forEach(m -> assertThat(modifiers.contains(m)).log("Modifier required: " + m, element));
    Arrays.stream(am.forbiddenModifiers())
        .forEach(m -> assertThat(!modifiers.contains(m)).log("Modifier forbidden: " + m, element));
    assertThat(TypeRestrict.Handler.match(method.getReturnType(), am.returnType(), elements, types))
        .log("Must return " + TypeRestrict.Handler.toString(am.returnType(), elements, types), element);
    List<? extends VariableElement> parameters = method.getParameters();
    TypeRestrict[] argTypes = am.argTypes();
    assertThat((am.argCount() < 0 && argTypes.length <= parameters.size()) || am.argCount() == parameters.size())
        .log("Must only have " + am.argCount() + " arguments.", element);
    for (int i = 0; i < argTypes.length; i++) {
      TypeRestrict res = argTypes[i];
      VariableElement param = parameters.get(i);
      assertThat(TypeRestrict.Handler.match(param.asType(), res, elements, types))
          .log("Argument must " + TypeRestrict.Handler.toString(res, elements, types), param);
    }
  }

  @Override
  protected void processMeta(RoundEnvironment env, AssertMethod am, Element element) throws AssertException {
    assertThat(am.argCount() < 0 || am.argCount() >= am.argTypes().length)
        .log("argCount must not greater than argTypes length.", element, AssertMethod.class);
  }
}
