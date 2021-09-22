package io.github.ludovicianul.command.base64;

import io.github.ludovicianul.io.Dvt;
import io.github.ludovicianul.utils.Utils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import picocli.CommandLine;

@CommandLine.Command(
    name = "base64",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    description = "Encode/decode strings according to RFC 2045",
    helpCommand = true,
    version = "1.0.0")
public class Base64Command implements Runnable {

  @CommandLine.Parameters(
      index = "0",
      paramLabel = "<strings>",
      description = "The given strings",
      arity = "0..*")
  String[] input;

  @CommandLine.Option(
      names = {"-e", "--encode"},
      description = "Encode given string according to RFC 2045. Default: ${DEFAULT-VALUE}")
  boolean encode = true;

  @CommandLine.Option(
      names = {"-d", "--decode"},
      description = "Decode given string according to RFC 2045. Default: ${DEFAULT-VALUE}")
  boolean decode;

  @Override
  public void run() {
    try {
      List<String> strings = getStrings();
      if (decode) {
        decode(strings);
      } else {
        encode(strings);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void encode(List<String> strings) {
    strings.stream()
        .map(string -> Base64.getEncoder().encode(string.getBytes(StandardCharsets.UTF_8)))
        .map(String::new)
        .forEach(Dvt::println);
  }

  private void decode(List<String> strings) {
    strings.stream()
        .map(string -> Base64.getDecoder().decode(string))
        .map(String::new)
        .forEach(Dvt::println);
  }

  private List<String> getStrings() throws IOException {
    if (input != null) {
      return Arrays.asList(input);
    } else {
      return Utils.parseSystemInAsList();
    }
  }
}
