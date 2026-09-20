package ro.msg4banking;

import java.util.List;
import java.util.Map;
import tools.jackson.databind.json.JsonMapper;

final class LlmResponses {

  private static final JsonMapper JSON = new JsonMapper();

  private LlmResponses() {}

  static String text(String content) {
    return response(Map.of("role", "assistant", "content", content));
  }

  static String toolCall(String id, String name, String arguments) {
    Map<String, Object> call =
      Map.of(
        "id", id, "type", "function", "function", Map.of("name", name, "arguments", arguments));
    return response(Map.of("role", "assistant", "tool_calls", List.of(call)));
  }

  private static String response(Map<String, Object> message) {
    return JSON.writeValueAsString(Map.of("choices", List.of(Map.of("message", message))));
  }
}
