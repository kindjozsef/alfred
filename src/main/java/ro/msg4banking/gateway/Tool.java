package ro.msg4banking.gateway;

import java.util.Map;

public interface Tool {

  String name();

  String description();

  Map<String, Object> parameters();

  String execute(String arguments);

  default Map<String, Object> definition() {
    return Map.of(
        "type",
        "function",
        "function",
        Map.of("name", name(), "description", description(), "parameters", parameters()));
  }
}
