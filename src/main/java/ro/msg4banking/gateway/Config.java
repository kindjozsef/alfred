package ro.msg4banking.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public record Config(String baseUrl, String apiKey, String model) {

  public static Config load() {
    Properties properties = new Properties();
    try (InputStream in = Config.class.getResourceAsStream("/llm.properties")) {
      if (in == null) {
        throw new IllegalStateException(
            "Missing src/main/resources/llm.properties, copy llm.properties.example");
      }
      properties.load(in);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return new Config(
        properties.getProperty("base-url"),
        properties.getProperty("api-key"),
        properties.getProperty("model"));
  }
}
