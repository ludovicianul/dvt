package io.github.ludovicianul.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Utils {

  public static List<String> parseSystemInAsList() throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
      return in.lines().collect(Collectors.toList());
    }
  }

  public static String parseSystemInAsString() throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
      return in.lines().collect(Collectors.joining());
    }
  }

  public static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase(Locale.ROOT) + s.substring(1).toLowerCase(Locale.ROOT);
  }

  public static String replaceWithSpecial(String s) {
    return s.replaceAll("^[^A-Za-z0-9]+|[^A-Za-z0-9]+$", "").replaceAll("[^A-Za-z0-9]", "#");
  }
}
