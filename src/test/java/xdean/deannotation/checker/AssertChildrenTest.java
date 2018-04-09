package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.annotation.processor.toolkit.test.CompileTest;
import xdean.annotation.processor.toolkit.test.Compiled;

public class AssertChildrenTest extends CompileTest {
  @Test
  @Compiled(
      processors = AssertChildrenProcessor.class,
      sources = {
          "assertChildren/InheritAnno.java",
          "assertChildren/NotInheritAnno.java",
          "assertChildren/A.java",
          "assertChildren/B.java",
          "assertChildren/C.java",
      })

  public void test(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
