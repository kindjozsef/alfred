package ro.msg4banking;

import java.util.List;
import ro.msg4banking.gateway.LlmClient;
import ro.msg4banking.gateway.vo.Message;
import ro.msg4banking.gateway.vo.ModelResponse;

public class Agent {

  private static final String SYSTEM_PROMPT =
      "You are alfred, a helpful coding assistant. Keep your answers short.";

  private final LlmClient llm;

  public Agent(LlmClient llm) {
    this.llm = llm;
  }

  public String ask(String question) {
    List<Message> messages = List.of(Message.system(SYSTEM_PROMPT), Message.user(question));
    return llm.chat(messages).content();
  }

  public ModelResponse listModels() {
    return llm.models();
  }
}
