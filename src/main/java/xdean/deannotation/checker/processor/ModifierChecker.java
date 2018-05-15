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
import xdean.annotation.processor.toolkit.meta.AbstractMetaProcessor;
import xdean.deannotation.checker.CheckModifier;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckModifier.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ModifierChecker extends AbstractMetaProcessor<CheckModifier> {

  @Override
  protected void process(RoundEnvironment env, CheckModifier am, AnnotationMirror mid, Element element) throws AssertException {
    Set<Modifier> modifiers = element.getModifiers();
    Arrays.stream(am.required())
        .forEach(m -> assertThat(modifiers.contains(m)).log("Modifier required: " + m, element));
    Arrays.stream(am.forbidden())
        .forEach(m -> assertThat(!modifiers.contains(m)).log("Modifier forbidden: " + m, element));
  }
}
