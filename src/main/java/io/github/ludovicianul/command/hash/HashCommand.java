package io.github.ludovicianul.command.hash;

import io.github.ludovicianul.io.Dvt;
import io.github.ludovicianul.utils.Utils;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import picocli.CommandLine;

@CommandLine.Command(
    name = "hash",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    description = "Perform hashing operations",
    helpCommand = true,
    version = "1.0.0")
public class HashCommand implements Runnable {

  @CommandLine.Parameters(
      index = "0",
      paramLabel = "<strings>",
      description = "The given strings",
      arity = "0..*")
  String[] input;

  @CommandLine.Option(
      names = {"-l", "--list"},
      description = "List all available hashing algorithms")
  boolean list;

  @CommandLine.Option(
      names = {"-a", "--algorithm"},
      description = "The algorithm used for hashing")
  String algorithm;

  @Override
  public void run() {
    try {
      if (list) {
        listAvailableAlgs();
      } else {
        doHashing();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void doHashing() throws IOException, NoSuchAlgorithmException {
    List<String> strings = getStrings();
    MessageDigest digest = MessageDigest.getInstance(algorithm);

    strings.stream().map(string -> hash(digest, string)).map(String::new).forEach(Dvt::println);
  }

  private void listAvailableAlgs() {
    Dvt.println(this.getAvailableAlgorithms());
  }

  private String hash(MessageDigest digest, String string) {
    digest.update(string.getBytes(StandardCharsets.UTF_8));
    return new BigInteger(1, digest.digest()).toString(16);
  }

  private List<String> getAvailableAlgorithms() {
    return Arrays.stream(Security.getProviders())
        .flatMap(provider -> provider.getServices().stream())
        .filter(s -> MessageDigest.class.getSimpleName().equals(s.getType()))
        .map(Provider.Service::getAlgorithm)
        .map(String::toLowerCase)
        .collect(Collectors.toList());
  }

  private List<String> getStrings() throws IOException {
    if (input != null) {
      return Arrays.asList(input);
    } else {
      return Utils.parseSystemInAsList();
    }
  }
}
