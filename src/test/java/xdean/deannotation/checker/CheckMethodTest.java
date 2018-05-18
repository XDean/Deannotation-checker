package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.MethodChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckMethodTest extends CompileTestCase {
  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = "checkMethod/GoldenUsage.java")
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = "checkMethod/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = "checkMethod/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = "checkMethod/BadUsage3.java")
  public void testBadUsage3(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = "checkMethod/BadUsage4.java")
  public void testBadUsage4(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
