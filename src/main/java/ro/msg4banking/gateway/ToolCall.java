package ro.msg4banking.gateway;

public record ToolCall(String id, String type, Function function) {

  public record Function(String name, String arguments) {}
}
