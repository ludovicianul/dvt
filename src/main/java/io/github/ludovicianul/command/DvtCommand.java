package io.github.ludovicianul.command;

import io.github.ludovicianul.command.base64.Base64Command;
import io.github.ludovicianul.command.hash.HashCommand;
import io.github.ludovicianul.command.html.HtmlCommand;
import io.github.ludovicianul.command.json.JsonCommand;
import io.github.ludovicianul.command.jwt.JwtCommand;
import io.github.ludovicianul.command.random.RandomCommand;
import io.github.ludovicianul.command.strcase.CaseCommand;
import io.github.ludovicianul.io.Dvt;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.inject.Inject;
import picocli.AutoComplete;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@QuarkusMain
@Command(
    name = "dvt",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    header = "dvt - command line dev tools; version 1.2.0\n",
    version = "dvt 1.2.0",
    subcommands = {
      AutoComplete.GenerateCompletion.class,
      Base64Command.class,
      CaseCommand.class,
      RandomCommand.class,
      HashCommand.class,
      JwtCommand.class,
      JsonCommand.class,
      HtmlCommand.class
    })
public class DvtCommand implements Runnable, QuarkusApplication {
  @Inject CommandLine.IFactory factory;

  @Override
  public void run() {
    Dvt.println("Hi! This is dvt! Please dvt -h to see all available sub-commands.");
  }

  @Override
  public int run(String... args) {
    return new CommandLine(this, factory).setCaseInsensitiveEnumValuesAllowed(true).execute(args);
  }
}
