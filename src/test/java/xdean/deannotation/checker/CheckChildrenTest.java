package xdean.deannotation.checker;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.ChildrenChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckChildrenTest extends CompileTestCase {
  @Compiled(
      processors = ChildrenChecker.class,
      sources = {
          "checkChildren/InheritAnno.java",
          "checkChildren/NotInheritAnno.java",
          "checkChildren/A.java",
          "checkChildren/B.java",
      })
  public void testGoldenUsage(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Compiled(
      processors = ChildrenChecker.class,
      sources = {
          "checkChildren/InheritAnno.java",
          "checkChildren/NotInheritAnno.java",
          "checkChildren/A.java",
          "checkChildren/C.java",
      })
  public void testInherit(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
