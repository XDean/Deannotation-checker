package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.annotation.processor.toolkit.test.CompileTestCase;
import xdean.annotation.processor.toolkit.test.Compiled;
import xdean.deannotation.checker.processor.AnnotationChecker;

public class CheckAnnotationTest extends CompileTestCase {
  @Test
  @Compiled(
      processors = AnnotationChecker.class,
      sources = "checkAnnotation/GoldenUsage.java")
  public void test(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Test
  @Compiled(
      processors = AnnotationChecker.class,
      sources = "checkAnnotation/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = AnnotationChecker.class,
      sources = "checkAnnotation/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
