package ro.msg4banking.gateway.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import ro.msg4banking.gateway.ToolCall;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Message(
    String role,
    String content,
    @JsonProperty("tool_calls") List<ToolCall> toolCalls,
    @JsonProperty("tool_call_id") String toolCallId) {

  public static Message system(String content) {
    return new Message("system", content, null, null);
  }

  public static Message user(String content) {
    return new Message("user", content, null, null);
  }

  public static Message tool(String toolCallId, String content) {
    return new Message("tool", content, null, toolCallId);
  }

  public boolean hasToolCalls() {
    return toolCalls != null && !toolCalls.isEmpty();
  }
}
