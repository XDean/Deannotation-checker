package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.FieldChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckFieldTest extends CompileTestCase {
  @Test
  @Compiled(
      processors = FieldChecker.class,
      sources = "checkField/GoldenUsage.java")
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Test
  @Compiled(
      processors = FieldChecker.class,
      sources = "checkField/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = FieldChecker.class,
      sources = "checkField/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
