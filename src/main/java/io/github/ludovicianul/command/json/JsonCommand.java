package io.github.ludovicianul.command.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.ludovicianul.io.Dvt;
import io.github.ludovicianul.utils.Utils;
import java.io.IOException;
import picocli.CommandLine;

@CommandLine.Command(
    name = "json",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    description = "Manipulate JSON files",
    helpCommand = true,
    version = "1.0.0")
public class JsonCommand implements Runnable {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  @CommandLine.Parameters(index = "0", paramLabel = "<json>", description = "The given JSON")
  String input;

  @CommandLine.Option(
      names = {"-p", "--pretty"},
      description = "Pretty print output. Default: ${DEFAULT-VALUE}")
  boolean pretty;

  @Override
  public void run() {
    try {
      String theJson = this.getString();
      if (pretty) {
        Dvt.println(GSON.toJson(theJson));
      } else {
        Dvt.println(theJson);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String getString() throws IOException {
    if (input != null) {
      return input;
    } else {
      return Utils.parseSystemInAsString();
    }
  }
}
