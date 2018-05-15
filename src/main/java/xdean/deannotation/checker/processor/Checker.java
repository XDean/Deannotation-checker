package xdean.deannotation.checker.processor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;

import xdean.annotation.processor.toolkit.meta.AbstractMetaProcessor;

public abstract class Checker<T extends Annotation> extends AbstractMetaProcessor<T> {

  protected final List<Checker<?>> dependencies;

  public Checker() {
    super();
    dependencies = Arrays.stream(this.getClass().getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(CheckerInject.class))
        .map(f -> {
          if (!(Checker.class.isAssignableFrom(f.getType()))) {
            throw new Error("@CheckerInject must annotated on Checker field: " + f);
          }
          try {
            return (Checker<?>) f.getType().newInstance();
          } catch (InstantiationException | IllegalAccessException e) {
            throw new Error(e);
          }
        })
        .collect(Collectors.toList());
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    dependencies.forEach(c -> c.init(processingEnv));

  }
}
