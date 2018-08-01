package xdean.deannotation.checker;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.ConstructorChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckConstructorTest extends CompileTestCase {
  @Compiled(
      processors = ConstructorChecker.class,
      sources = "checkConstructor/GoldenUsage.java")
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Compiled(
      processors = ConstructorChecker.class,
      sources = "checkConstructor/BadDefine1.java")
  public void testBadDefine1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Compiled(
      processors = ConstructorChecker.class,
      sources = "checkConstructor/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Compiled(
      processors = ConstructorChecker.class,
      sources = "checkConstructor/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Compiled(
      processors = ConstructorChecker.class,
      sources = "checkConstructor/BadUsage3.java")
  public void testBadUsage3(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
