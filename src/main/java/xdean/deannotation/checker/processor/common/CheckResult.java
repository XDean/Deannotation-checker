package xdean.deannotation.checker.processor.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;

public class CheckResult {

  public static final CheckResult EMPTY = new CheckResult(Collections.emptyMap());

  public final Map<Element, List<String>> errors;

  public CheckResult(Map<Element, List<String>> errors) {
    this.errors = Collections.unmodifiableMap(errors);
  }

  public boolean isPass() {
    return errors.isEmpty();
  }

  public static class Builder {
    private final Element root;
    private final Map<Element, List<String>> errors = new LinkedHashMap<>();

    public static Builder create(Element root) {
      return new Builder(root);
    }

    private Builder(Element root) {
      this.root = root;
    }

    public Builder add(Element element, String error) {
      errors.computeIfAbsent(element, e -> new ArrayList<>()).add(error);
      return this;
    }

    public Builder add(String error) {
      return add(root, error);
    }

    public Builder add(CheckResult other) {
      other.errors.forEach((element, error) -> this.errors.computeIfAbsent(element, e -> new ArrayList<>()).addAll(error));
      return this;
    }

    public Builder addIf(boolean b, String error) {
      if (b) {
        add(error);
      }
      return this;
    }

    public Builder addIfNot(boolean b, String error) {
      return addIf(!b, error);
    }

    public CheckResult build() {
      return new CheckResult(errors);
    }
  }
}
