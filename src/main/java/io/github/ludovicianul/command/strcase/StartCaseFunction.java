package io.github.ludovicianul.command.strcase;

import io.github.ludovicianul.utils.Utils;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StartCaseFunction implements Function<String, String> {

  @Override
  public String apply(String string) {
    String replaced = Utils.replaceWithSpecial(string);
    return Arrays.stream(replaced.split("#"))
        .map(Utils::capitalize)
        .collect(Collectors.joining(" "));
  }
}
