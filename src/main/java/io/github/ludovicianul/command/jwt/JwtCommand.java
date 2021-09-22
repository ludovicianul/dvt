package io.github.ludovicianul.command.jwt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.ludovicianul.io.Dvt;
import io.github.ludovicianul.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import picocli.CommandLine;

@CommandLine.Command(
    name = "jwt",
    mixinStandardHelpOptions = true,
    usageHelpWidth = 100,
    description = "Verify and decode JWTs",
    helpCommand = true,
    version = "1.0.0")
public class JwtCommand implements Runnable {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  @CommandLine.Parameters(index = "0", paramLabel = "<JWT>", description = "The given JWT")
  String input;

  @CommandLine.Option(
      names = {"-d", "--decode"},
      description = "Decode given JWT. Default: ${DEFAULT-VALUE}")
  boolean decode = true;

  @CommandLine.Option(
      names = {"-p", "--pretty"},
      description = "Pretty print output. Default: ${DEFAULT-VALUE}")
  boolean pretty;

  @CommandLine.Option(
      names = {"-k", "--key"},
      description = "The key used to sign the JWT")
  String secret;

  @Override
  public void run() {
    try {
      String jwt = getString();
      if (secret == null) {
        this.decodeUnsignedJwt(jwt);
      } else {
        this.decodeSignedJwt(jwt);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void decodeSignedJwt(String jwt) {
    Jwt<JwsHeader, Claims> parseClaimsJws =
        Jwts.parserBuilder()
            .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
            .build()
            .parseClaimsJws(jwt);
    this.print(parseClaimsJws);
  }

  private void decodeUnsignedJwt(String jwt) {
    Jwt<Header, Claims> parsedJwt =
        Jwts.parserBuilder()
            .setAllowedClockSkewSeconds(Long.MAX_VALUE / 1000)
            .build()
            .parseClaimsJwt(this.removeSignature(jwt));
    this.print(parsedJwt);
  }

  private void print(Jwt<? extends Header, Claims> parseClaimsJws) {
    if (pretty) {
      Dvt.println(GSON.toJson(parseClaimsJws.getHeader()));
      Dvt.println(GSON.toJson(parseClaimsJws.getBody()));
    } else {
      Dvt.println(parseClaimsJws.getHeader());
      Dvt.println(parseClaimsJws.getBody());
    }
  }

  private String removeSignature(String fullJwt) {
    String[] splitJwt = fullJwt.split("\\.");
    return splitJwt[0] + "." + splitJwt[1] + ".";
  }

  private String getString() throws IOException {
    if (input != null) {
      return input;
    } else {
      return Utils.parseSystemInAsString();
    }
  }
}
