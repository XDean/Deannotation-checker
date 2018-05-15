package xdean.deannotation.checker.processor;

import java.util.Arrays;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.processor.common.Checker;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckModifier.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ModifierChecker extends Checker<CheckModifier> {

  @Override
  protected void process(RoundEnvironment env, CheckModifier am, AnnotationMirror mid, Element element) throws AssertException {
    Set<Modifier> modifiers = element.getModifiers();
    Arrays.stream(am.require())
        .forEach(m -> assertThat(modifiers.contains(m)).log("Modifier required: " + m, element));
    Arrays.stream(am.forbid())
        .forEach(m -> assertThat(!modifiers.contains(m)).log("Modifier forbidden: " + m, element));
  }
}
