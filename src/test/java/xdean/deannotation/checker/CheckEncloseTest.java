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
}
