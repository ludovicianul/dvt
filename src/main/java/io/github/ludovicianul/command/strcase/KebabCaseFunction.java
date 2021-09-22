package io.github.ludovicianul.command.strcase;

import io.github.ludovicianul.utils.Utils;
import java.util.Locale;
import java.util.function.Function;

public class KebabCaseFunction implements Function<String, String> {

  @Override
  public String apply(String string) {
    return Utils.replaceWithSpecial(string).replaceAll("#", "-").toLowerCase(Locale.ROOT);
  }
}
