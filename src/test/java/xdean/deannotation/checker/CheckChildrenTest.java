package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.deannotation.checker.processor.ChildrenChecker;
import xdean.test.compile.CompileTestCase;
import xdean.test.compile.Compiled;

public class CheckChildrenTest extends CompileTestCase {
  @Test
  @Compiled(
      processors = ChildrenChecker.class,
      sources = {
          "checkChildren/InheritAnno.java",
          "checkChildren/NotInheritAnno.java",
          "checkChildren/A.java",
          "checkChildren/B.java",
      })
  public void test(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Test
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
