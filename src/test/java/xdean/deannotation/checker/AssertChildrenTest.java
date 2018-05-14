package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.annotation.processor.toolkit.test.CompileTestCase;
import xdean.annotation.processor.toolkit.test.Compiled;

public class AssertChildrenTest extends CompileTestCase {
  @Test
  @Compiled(
      processors = AssertChildrenProcessor.class,
      sources = {
          "assertChildren/InheritAnno.java",
          "assertChildren/NotInheritAnno.java",
          "assertChildren/A.java",
          "assertChildren/B.java",
      })
  public void test(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Test
  @Compiled(
      processors = AssertChildrenProcessor.class,
      sources = {
          "assertChildren/InheritAnno.java",
          "assertChildren/NotInheritAnno.java",
          "assertChildren/A.java",
          "assertChildren/C.java",
      })
  public void testInherit(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
