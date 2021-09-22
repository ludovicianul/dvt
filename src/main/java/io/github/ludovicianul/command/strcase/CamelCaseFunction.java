package io.github.ludovicianul.command.strcase;

import io.github.ludovicianul.utils.Utils;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CamelCaseFunction implements Function<String, String> {

  @Override
  public String apply(String string) {
    String replaced = Utils.replaceWithSpecial(string);
    replaced =
        Arrays.stream(replaced.split("#")).map(Utils::capitalize).collect(Collectors.joining());
    return replaced.substring(0, 1).toLowerCase(Locale.ROOT) + replaced.substring(1);
  }
}
