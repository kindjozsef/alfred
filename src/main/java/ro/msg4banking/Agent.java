package ro.msg4banking;

import java.util.ArrayList;
import java.util.List;
import ro.msg4banking.gateway.LlmClient;
import ro.msg4banking.gateway.Tool;
import ro.msg4banking.gateway.ToolCall;
import ro.msg4banking.gateway.vo.Message;

public class Agent {

  private static final String SYSTEM_PROMPT =
      """
    You are alfred, a coding agent. You work on a Java project through your tools.
    Look at the files before you answer or change anything. Keep your answers short.
    """;

  private final LlmClient llm;
  private final List<Tool> tools;
  private final List<Message> history = new ArrayList<>();

  public Agent(LlmClient llm, List<Tool> tools) {
    this.llm = llm;
    this.tools = tools;
    history.add(Message.system(SYSTEM_PROMPT));
  }

  public String ask(String question) {
    history.add(Message.user(question));
    Message answer = llm.chat(history, tools);
    history.add(answer);
    while (answer.hasToolCalls()) {
      for (ToolCall call : answer.toolCalls()) {
        history.add(runTool(call));
      }
      answer = llm.chat(history, tools);
      history.add(answer);
    }
    return answer.content();
  }

  private Message runTool(ToolCall call) {
    String name = call.function().name();
    String arguments = call.function().arguments();
    System.out.println("[tool] " + name + " " + arguments);
    String result =
        tools.stream()
            .filter(tool -> tool.name().equals(name))
            .findFirst()
            .map(tool -> tool.execute(arguments))
            .orElse("Error: unknown tool " + name);
    return Message.tool(call.id(), result);
  }
}
