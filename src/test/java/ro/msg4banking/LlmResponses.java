package ro.msg4banking;

import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

final class LlmResponses {

  private static final JsonMapper JSON = new JsonMapper();

  private LlmResponses() {}

  static String text(String content) {
    return response(Map.of("role", "assistant", "content", content));
  }

  private static String response(Map<String, Object> message) {
    return JSON.writeValueAsString(Map.of("choices", List.of(Map.of("message", message))));
  }
}
