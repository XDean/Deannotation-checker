package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.annotation.processor.toolkit.test.CompileTestCase;
import xdean.annotation.processor.toolkit.test.Compiled;
import xdean.deannotation.checker.processor.MethodChecker;

public class CheckMethodTest extends CompileTestCase {
  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java"
      })
  public void test(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java",
          "checkMethod/BadUsage1.java"
      })
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java",
          "checkMethod/BadUsage2.java"
      })
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java",
          "checkMethod/BadUsage3.java"
      })
  public void testBadUsage3(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java",
          "checkMethod/BadUsage4.java"
      })
  public void testBadUsage4(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java",
          "checkMethod/BadUsage5.java"
      })
  public void testBadUsage5(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java",
          "checkMethod/BadUsage6.java"
      })
  public void testBadUsage6(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = MethodChecker.class,
      sources = {
          "checkMethod/Init.java",
          "checkMethod/GoldenUsage.java",
          "checkMethod/BadDefine1.java"
      })
  public void testBadDefine1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
