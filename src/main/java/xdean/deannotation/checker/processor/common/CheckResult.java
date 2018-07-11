package xdean.deannotation.checker.processor.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CheckResult {

  public static final CheckResult EMPTY = new CheckResult(Collections.emptyList());

  public final List<String> errors;

  public CheckResult(List<String> errors) {
    this.errors = Collections.unmodifiableList(errors);
  }

  public boolean isPass() {
    return errors.isEmpty();
  }

  public String getMessage() {
    return errors.stream().collect(Collectors.joining("\n"));
  }

  public static class Builder {
    private final List<String> errors = new ArrayList<>();

    public static Builder create() {
      return new Builder();
    }

    public Builder add(String error) {
      errors.add(error);
      return this;
    }

    public Builder add(CheckResult other) {
      errors.addAll(other.errors);
      return this;
    }

    public Builder addIf(boolean b, String error) {
      if (b) {
        errors.add(error);
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
