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
    // Step 4: if the answer has tool calls, run the tool and add the result to the history
    return answer.content();
  }

  private Message runTool(ToolCall call) {
    throw new UnsupportedOperationException(
        "Step 4: find the tool by name, execute it with the arguments"
            + " and return Message.tool(call.id(), result)");
  }
}
