package io.github.ludovicianul.command.strcase;

import io.github.ludovicianul.io.Dvt;
import io.github.ludovicianul.utils.Utils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import picocli.CommandLine;

@CommandLine.Command(
    name = "case",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    description = "Convert strings to different cases",
    helpCommand = true,
    version = "1.0.0")
public class CaseCommand implements Runnable {
  @CommandLine.Parameters(
      index = "0",
      paramLabel = "<strings>",
      description = "The given strings",
      arity = "0..*")
  String[] input;

  @CommandLine.Option(
      names = {"-u", "--upper"},
      description = "Transform string(s) to UPPER case")
  boolean upper;

  @CommandLine.Option(
      names = {"-l", "--lower"},
      description = "Transform string(s) to lower case")
  boolean lower;

  @CommandLine.Option(
      names = {"-s", "--snake"},
      description = "Transform string(s) to snake_case")
  boolean snake;

  @CommandLine.Option(
      names = {"-k", "--kebab"},
      description = "Transform string(s) to kebab-case")
  boolean kebab;

  @CommandLine.Option(
      names = {"-c", "--camel"},
      description = "Transform string(s) to camelCase")
  boolean camel;

  @CommandLine.Option(
      names = {"-t", "--start"},
      description = "Transform string(s) to StartCase")
  boolean start;

  @Override
  public void run() {
    try {
      List<String> strings = this.getSpecialCase(this.getStrings());
      strings = this.getLowerUpper(strings);
      strings.forEach(Dvt::println);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private List<String> getSpecialCase(List<String> strings) {
    Function<String, String> transformation = Function.identity();

    if (camel) {
      transformation = new CamelCaseFunction();
    } else if (kebab) {
      transformation = new KebabCaseFunction();
    } else if (snake) {
      transformation = new SnakeCaseFunction();
    } else if (start) {
      transformation = new StartCaseFunction();
    }

    return strings.stream().map(transformation).collect(Collectors.toList());
  }

  private List<String> getLowerUpper(List<String> strings) {
    Function<String, String> transformation = Function.identity();
    if (upper) {
      transformation = string -> string.toUpperCase(Locale.ROOT);
    } else if (lower) {
      transformation = string -> string.toLowerCase(Locale.ROOT);
    }
    strings = strings.stream().map(transformation).collect(Collectors.toList());
    return strings;
  }

  private List<String> getStrings() throws IOException {
    if (input != null) {
      return Arrays.asList(input);
    } else {
      return Utils.parseSystemInAsList();
    }
  }
}
