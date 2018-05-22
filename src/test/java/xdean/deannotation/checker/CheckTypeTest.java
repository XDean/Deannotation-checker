package xdean.deannotation.checker;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.TypeChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckTypeTest extends CompileTestCase {
  @Compiled(
      processors = TypeChecker.class,
      sources = "checkType/GoldenUsage.java")
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Compiled(
      processors = TypeChecker.class,
      sources = "checkType/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Compiled(
      processors = TypeChecker.class,
      sources = "checkType/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Compiled(
      processors = TypeChecker.class,
      sources = "checkType/BadDefine1.java")
  public void testBadDefine1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Compiled(
      processors = TypeChecker.class,
      sources = "checkType/BadDefine2.java")
  public void testBadDefine2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
