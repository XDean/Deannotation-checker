package xdean.deannotation.checker;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import xdean.annotation.processor.toolkit.test.CompileTestCase;
import xdean.annotation.processor.toolkit.test.Compiled;
import xdean.deannotation.checker.processor.ModifierChecker;

public class CheckModifierTest extends CompileTestCase {
  @Test
  @Compiled(
      processors = ModifierChecker.class,
      sources = "checkModifier/GoldenUsage.java")
  public void test(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).succeeded();
  }

  @Test
  @Compiled(
      processors = ModifierChecker.class,
      sources = "checkModifier/BadUsage1.java")
  public void testBadUsage1(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }

  @Test
  @Compiled(
      processors = ModifierChecker.class,
      sources = "checkModifier/BadUsage2.java")
  public void testBadUsage2(Compilation c) throws Exception {
    CompilationSubject.assertThat(c).hadErrorCount(1);
  }
}
