package xdean.deannotation.checker.processor.common;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.CommonUtil;
import xdean.annotation.processor.toolkit.meta.AbstractMetaProcessor;

public abstract class Checker<T extends Annotation> extends AbstractMetaProcessor<T> {

  protected final List<Checker<?>> dependencies;

  public Checker() {
    dependencies = Arrays.stream(this.getClass().getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(CheckerInject.class))
        .map(f -> {
          assertThat(Checker.class.isAssignableFrom(f.getType())).message("@CheckerInject must annotated on Checker field: " + f);
          return CommonUtil.uncheck(() -> {
            Object dependency = f.getType().newInstance();
            f.setAccessible(true);
            f.set(this, dependency);
            return (Checker<?>) dependency;
          });
        })
        .collect(Collectors.toList());
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    dependencies.forEach(c -> c.init(processingEnv));
  }

  @Override
  protected final void processMeta(RoundEnvironment env, T t, Element element) throws AssertException {
    super.processMeta(env, t, element);
    List<String> errors = checkDefine(t, element);
    assertThat(errors.isEmpty())
        .todo(() -> error().log(errors.stream().collect(Collectors.joining("\n")), element, metaClass));
  }

  @Override
  protected final void process(RoundEnvironment env, T t, AnnotationMirror mid, Element element) throws AssertException {
    check(env, t, mid, element).errors
        .forEach((e, errors) -> error().log(errors.stream().collect(Collectors.joining("\n")), e));
  }

  public abstract CheckResult check(RoundEnvironment env, T t, AnnotationMirror mid, Element element) throws AssertException;

  /**
   * Check the checker annotation's definition.
   *
   * @param t the annotation value
   * @param annotatedElement the annotated element
   * @return error messages.
   */
  public List<String> checkDefine(T t, Element annotatedElement) {
    return Collections.emptyList();
  }

  public static List<String> attributeBadDefine(List<String> errors, String attribute) {
    return errors.stream().map(s -> "Attribute '" + attribute + "': " + s).collect(Collectors.toList());
  }

  protected <K> Optional<K> handleAssert(Supplier<K> task) {
    try {
      return Optional.of(task.get());
    } catch (AssertException e) {
      if (e.getMessage() != null && !e.getMessage().isEmpty()) {
        error().log(e.getMessage());
      }
      return Optional.empty();
    }
  }

  protected String getMessage(AnnotationMirror mid, String error) {
    if (mid == null) {
      return error;
    } else {
      return "Constraint by @" + mid.getAnnotationType().asElement().getSimpleName() + ": " + error;
    }
  }
}
