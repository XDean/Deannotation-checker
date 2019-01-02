package xdean.deannotation.checker;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.AnnotationChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckAnnotationTest extends CompileTestCase {
  @Compiled(
      processors = AnnotationChecker.class,
      sources = "checkAnnotation/GoldenUsage.java")
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Compiled(
      processors = AnnotationChecker.class,
      sources = "checkAnnotation/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject cs = CompilationSubject.assertThat(c);
    cs.hadErrorCount(1);
    cs.hadErrorContaining("Constraint by @Anno");
  }

  @Compiled(
      processors = AnnotationChecker.class,
      sources = "checkAnnotation/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject cs = CompilationSubject.assertThat(c);
    cs.hadErrorCount(1);
    cs.hadErrorContaining("custom");
  }
}
