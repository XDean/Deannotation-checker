package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckClass;
import xdean.deannotation.checker.CheckConstructor;
import xdean.deannotation.checker.CheckEnclose;
import xdean.deannotation.checker.CheckEnclose.Type;
import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.Checker;
import xdean.deannotation.checker.processor.common.CheckerInject;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckEnclose.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EncloseChecker extends Checker<CheckEnclose> {

  private @CheckerInject FieldChecker fieldChecker;
  private @CheckerInject ConstructorChecker constructorChecker;
  private @CheckerInject MethodChecker methodChecker;
  private @CheckerInject ClassChecker classChecker;

  @Override
  public CheckResult check(RoundEnvironment env, CheckEnclose ce, AnnotationMirror mid, Element element) throws AssertException {
    assertThat(element instanceof TypeElement || element.getKind() == ElementKind.PACKAGE).doNoThing();

    boolean allMatch = ce.type() == Type.ALL;
    AtomicBoolean pass = new AtomicBoolean(allMatch);
    List<String> elementStrings = new ArrayList<>();

    List<? extends Element> encloses = element.getEnclosedElements();

    List<VariableElement> fields = ElementFilter.fieldsIn(encloses);
    Arrays.stream(ce.fields()).forEach(cf -> {
      boolean result = fields.stream()
          .anyMatch(field -> handleAssert(() -> fieldChecker.check(env, cf, mid, field)).map(CheckResult::isPass).orElse(false));
      if (result) {
        if (!allMatch) {
          pass.set(true);
        }
      } else {
        if (allMatch) {
          pass.set(false);
        }
        elementStrings.add("Field match: " + cf.toString());
      }
    });

    List<ExecutableElement> constructors = ElementFilter.constructorsIn(encloses);
    Arrays.stream(ce.constructors()).forEach(cc -> {
      boolean result = constructors.stream()
          .anyMatch(constructor -> handleAssert(() -> constructorChecker.check(env, cc, mid, constructor)).map(CheckResult::isPass)
              .orElse(false));
      if (result) {
        if (!allMatch) {
          pass.set(true);
        }
      } else {
        if (allMatch) {
          pass.set(false);
        }
        elementStrings.add("Constructor match: " + cc.toString());
      }
    });

    List<ExecutableElement> methods = ElementFilter.methodsIn(encloses);
    Arrays.stream(ce.methods()).forEach(cm -> {
      boolean result = methods.stream()
          .anyMatch(method -> handleAssert(() -> methodChecker.check(env, cm, mid, method)).map(CheckResult::isPass)
              .orElse(false));
      if (result) {
        if (!allMatch) {
          pass.set(true);
        }
      } else {
        if (allMatch) {
          pass.set(false);
        }
        elementStrings.add("Method match: " + cm.toString());
      }
    });

    List<TypeElement> classes = ElementFilter.typesIn(encloses);
    Arrays.stream(ce.classes()).forEach(cc -> {
      boolean result = classes.stream()
          .anyMatch(clz -> handleAssert(() -> classChecker.check(env, cc, mid, clz)).map(CheckResult::isPass)
              .orElse(false));
      if (result) {
        if (!allMatch) {
          pass.set(true);
        }
      } else {
        if (allMatch) {
          pass.set(false);
        }
        elementStrings.add("Class match: " + cc.toString());
      }
    });
    if (pass.get()) {
      return CheckResult.EMPTY;
    } else {
      return CheckResult.Builder.create(element)
          .add((allMatch ? "Must have all the enclosed element:" : "Must have one of the enclosed element:") + "\n"
              + elementStrings.stream().map(s -> "\t" + s).collect(Collectors.joining("\n")))
          .build();
    }
  }

  @Override
  public List<String> checkDefine(CheckEnclose t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    int i;
    CheckField[] fields = t.fields();
    for (i = 0; i < fields.length; i++) {
      CheckField cf = fields[i];
      list.addAll(attributeBadDefine(fieldChecker.checkDefine(cf, annotatedElement), "fields[" + i + "]"));
    }
    CheckConstructor[] constructors = t.constructors();
    for (i = 0; i < constructors.length; i++) {
      CheckConstructor cc = constructors[i];
      list.addAll(attributeBadDefine(constructorChecker.checkDefine(cc, annotatedElement), "constructors[" + i + "]"));
    }
    CheckMethod[] methods = t.methods();
    for (i = 0; i < methods.length; i++) {
      CheckMethod cm = methods[i];
      list.addAll(attributeBadDefine(methodChecker.checkDefine(cm, annotatedElement), "methods[" + i + "]"));
    }
    CheckClass[] classes = t.classes();
    for (i = 0; i < classes.length; i++) {
      CheckClass cc = classes[i];
      list.addAll(attributeBadDefine(classChecker.checkDefine(cc, annotatedElement), "classes[" + i + "]"));
    }
    return list;
  }
}
