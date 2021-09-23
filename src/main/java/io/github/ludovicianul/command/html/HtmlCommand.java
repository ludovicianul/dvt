package io.github.ludovicianul.command.html;

import io.github.ludovicianul.utils.Utils;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.select.Elements;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import us.codecraft.xsoup.Xsoup;

@Command(
    name = "html",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    description = "Manipulate HTML files",
    helpCommand = true,
    version = "1.0.0")
public class HtmlCommand implements Runnable {

  @Parameters(
      index = "0",
      paramLabel = "<selector>",
      defaultValue = "empty",
      description = "The CSS selector")
  String selector;

  @CommandLine.Option(
      names = {"-a", "--attribute"},
      paramLabel = "<attribute>",
      description = "Return only this attribute from the selected HTML elements")
  String attribute;

  @CommandLine.Option(
      names = {"-f", "--file"},
      paramLabel = "<FILE>",
      description = "The HTML input file. If not supplied it will default to stdin")
  String file;

  @CommandLine.Option(
      names = {"-o", "--output"},
      paramLabel = "<FILE>",
      description = "The output file. If not supplied it will default to stdout")
  String output;

  @CommandLine.Option(
      names = {"-x", "--xpath"},
      paramLabel = "<XPATH>",
      description = "Supply an XPath selector instead of CSS")
  String xpath;

  @CommandLine.Option(
      names = {"-t", "--text"},
      description = "Display only the inner text of the selected HTML top element")
  boolean text;

  @CommandLine.Option(
      names = {"-p", "--pretty"},
      description = "Force pretty printing the output")
  boolean prettyPrint;

  @CommandLine.Option(
      names = {"-s", "--sanitize"},
      paramLabel = "<POLICY>",
      description = "Sanitize the html input according to the given policy")
  Sanitize sanitize;

  @Override
  public void run() {
    try {
      String html;
      if (file == null) {
        html = this.parseSystemIn();
      } else {
        html = this.parseFile();
      }
      this.processHtml(html);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void processHtml(String html) throws IOException {
    Elements elements;
    Document document = Jsoup.parse(html);

    if (sanitize != null) {
      document = this.sanitize(document);
    }

    this.setPrettyPrint(document);
    elements = this.evaluateSelector(document);

    this.printResult(elements);
  }

  private Document sanitize(Document document) {
    return new Cleaner(sanitize.safelist()).clean(document);
  }

  private void setPrettyPrint(Document document) {
    if (prettyPrint) {
      document.outputSettings().indentAmount(4).outline(true);
    } else {
      document.outputSettings().prettyPrint(false);
    }
  }

  private Elements evaluateSelector(Document document) {
    Elements elements;
    if (xpath != null) {
      elements = Xsoup.compile(xpath).evaluate(document).getElements();
    } else {
      elements = document.select(selector);
    }
    return elements;
  }

  private void printResult(Elements elements) throws IOException {
    if (attribute != null) {
      List<String> elementsWithAttribute = elements.eachAttr(attribute);
      Utils.writeToOutput(
          output,
          elementsWithAttribute.stream().collect(Collectors.joining(System.lineSeparator())));
    } else if (text) {
      Utils.writeToOutput(output, elements.text());
    } else {
      Utils.writeToOutput(output, elements.outerHtml());
    }
  }

  private String parseFile() throws IOException {
    return Utils.parseInputRaw(new FileReader(file));
  }

  private String parseSystemIn() throws IOException {
    return Utils.parseInputRaw(new InputStreamReader(System.in));
  }
}
