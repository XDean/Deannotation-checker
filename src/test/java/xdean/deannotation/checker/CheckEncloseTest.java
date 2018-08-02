package xdean.deannotation.checker;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.EncloseChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckEncloseTest extends CompileTestCase {
  @Compiled(
      processors = EncloseChecker.class,
      sources = "checkEnclose/GoldenUsage.java")
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Compiled(
      processors = EncloseChecker.class,
      sources = "checkEnclose/BadDefine.java")
  public void testBadDefine(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Compiled(
      processors = EncloseChecker.class,
      sources = "checkEnclose/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(2);
  }

  @Compiled(
      processors = EncloseChecker.class,
      sources = "checkEnclose/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
