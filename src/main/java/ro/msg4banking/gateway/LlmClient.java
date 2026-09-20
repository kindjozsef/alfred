package ro.msg4banking.gateway;

import java.util.List;
import org.springframework.web.client.RestClient;
import ro.msg4banking.gateway.vo.Message;

public class LlmClient {

  private final RestClient http = RestClient.create();
  private final Config config;

  public LlmClient(Config config) {
    this.config = config;
  }

  public Message chat(List<Message> messages) {
    throw new UnsupportedOperationException(
        "Step 1: POST a ChatRequest to {base-url}/chat/completions"
            + " and return the message of the first choice");
  }
}
