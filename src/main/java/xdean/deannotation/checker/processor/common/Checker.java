package xdean.deannotation.checker.processor.common;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    super();
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
  protected void process(RoundEnvironment env, T t, AnnotationMirror mid, Element element) throws AssertException {
  }

  @Override
  protected final void processMeta(RoundEnvironment env, T t, Element element) throws AssertException {
    super.processMeta(env, t, element);
    List<String> checkDefine = checkDefine(t, element);
    checkDefine.forEach(s -> error().log(s, element, metaClass));
    assertThat(checkDefine.isEmpty()).doNoThing();
  }

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
}
