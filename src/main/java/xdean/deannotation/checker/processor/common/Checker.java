package xdean.deannotation.checker.processor.common;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;

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
}
