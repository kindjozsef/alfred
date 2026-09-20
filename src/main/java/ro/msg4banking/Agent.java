package ro.msg4banking;

import java.util.ArrayList;
import java.util.List;
import ro.msg4banking.gateway.LlmClient;
import ro.msg4banking.gateway.vo.Message;

public class Agent {

  private static final String SYSTEM_PROMPT =
      "You are alfred, a helpful coding assistant. Keep your answers short.";

  private final LlmClient llm;
  private final List<Message> history = new ArrayList<>();

  public Agent(LlmClient llm) {
    this.llm = llm;
    history.add(Message.system(SYSTEM_PROMPT));
  }

  public String ask(String question) {
    history.add(Message.user(question));
    Message answer = llm.chat(history);
    history.add(answer);
    return answer.content();
  }
}
