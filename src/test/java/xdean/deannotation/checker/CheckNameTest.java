package xdean.deannotation.checker;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.NameChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckNameTest extends CompileTestCase {
  @Compiled(
      processors = NameChecker.class,
      sources = "checkName/GoldenUsage.java")
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Compiled(
      processors = NameChecker.class,
      sources = "checkName/BadUsage.java")
  public void testBadUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(5);
  }

  @Compiled(
      processors = NameChecker.class,
      sources = "checkName/BadDefine.java")
  public void testBadDefine(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
