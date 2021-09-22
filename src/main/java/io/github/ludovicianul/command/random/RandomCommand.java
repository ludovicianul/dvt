package io.github.ludovicianul.command.random;

import com.github.curiousoddman.rgxgen.RgxGen;
import io.github.ludovicianul.io.Dvt;
import java.util.Random;
import java.util.UUID;
import picocli.CommandLine;

@CommandLine.Command(
    name = "random",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    description = "Generate random data based on the supplied data type",
    helpCommand = true,
    version = "1.0.0")
public class RandomCommand implements Runnable {

  @CommandLine.Option(
      names = {"-a", "--amount"},
      description = "How many items to generate. Default: 1")
  int amount = 1;

  @CommandLine.Option(
      names = {"-t", "--type"},
      description = "The type of data to generate. Default: ${DEFAULT-VALUE}")
  Type type = Type.STRING;

  @CommandLine.Option(
      names = {"-m", "--min"},
      description = "Minimum/min length. Default: ${DEFAULT-VALUE}")
  int min;

  @CommandLine.Option(
      names = {"-x", "--max"},
      description = "Maximum/max length. Default: ${DEFAULT-VALUE}")
  int max = 100;

  @CommandLine.Option(
      names = {"-r", "--regex"},
      description =
          "Regex to use when generating. Only applicable for STRING. Default: ${DEFAULT-VALUE}")
  String regex = "[A-Za-z0-9]";

  @Override
  public void run() {
    int minimum = min < 0 ? 0 : min;
    int maximum = min < 0 ? 0 : max < min ? min : max;
    String finalRegex = regex + "{" + min + "," + max + "}";

    if (amount < 1) {
      Dvt.err("Amount must be bigger than 1");
    } else {
      for (int i = 0; i < amount; i++) {
        Dvt.println(this.generateString(minimum, maximum, finalRegex));
      }
    }
  }

  private String generateString(int minimum, int maximum, String finalRegex) {
    String toOutput = "";
    switch (type) {
      case STRING:
        toOutput = new RgxGen(finalRegex).generate();
        break;
      case INTEGER:
        toOutput = String.valueOf(new Random().nextInt(maximum - minimum) + minimum);
        break;
      case DECIMAL:
        toOutput = String.valueOf(minimum + Math.random() * (maximum - minimum));
        break;
      case UUID:
        toOutput = UUID.randomUUID().toString();
        break;
    }
    return toOutput;
  }

  enum Type {
    INTEGER,
    DECIMAL,
    STRING,
    UUID
  }
}
