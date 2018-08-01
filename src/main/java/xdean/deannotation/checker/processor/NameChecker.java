package xdean.deannotation.checker.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;

import com.google.auto.service.AutoService;

import xdean.annotation.processor.toolkit.AssertException;
import xdean.annotation.processor.toolkit.annotation.SupportedMetaAnnotation;
import xdean.deannotation.checker.CheckName;
import xdean.deannotation.checker.processor.common.CheckResult;
import xdean.deannotation.checker.processor.common.CheckResult.Builder;
import xdean.deannotation.checker.processor.common.Checker;

@AutoService(Processor.class)
@SupportedMetaAnnotation(CheckName.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameChecker extends Checker<CheckName> {

  @Override
  public CheckResult check(RoundEnvironment env, CheckName cn, Element element) throws AssertException {
    Builder builder = CheckResult.Builder.create(element);
    Pattern p = Pattern.compile(cn.value(), cn.ignoreCase() ? Pattern.CASE_INSENSITIVE : 0);
    builder.addIfNot(p.matcher(element.getSimpleName().toString()).matches(), "Element name must match regex: " + cn.value());
    return builder.build();
  }

  @Override
  public List<String> checkDefine(CheckName t, Element annotatedElement) {
    List<String> list = new ArrayList<>();
    try {
      Pattern.compile(t.value());
    } catch (PatternSyntaxException e) {
      list.add("Invalid regex: " + e.getMessage());
    }
    return list;
  }
}
