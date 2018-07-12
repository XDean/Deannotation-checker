package xdean.deannotation.checker.processor;

import java.util.Arrays;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckModifier;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.CheckResult.Builder;
import xdean.deannotation.checker.processor.common.Checker;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckModifier.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ModifierChecker extends Checker<CheckModifier> {

  @Override
  public CheckResult check(RoundEnvironment env, CheckModifier cm, Element element) throws AssertException {
    Builder builder = CheckResult.Builder.create(element);
    Set<Modifier> modifiers = element.getModifiers();
    Arrays.stream(cm.require())
        .forEach(m -> builder.addIf(!modifiers.contains(m), "Modifier required: " + m));
    Arrays.stream(cm.forbid())
        .forEach(m -> builder.addIf(modifiers.contains(m), "Modifier forbidden: " + m));
    return builder.build();
  }
}
