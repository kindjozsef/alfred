package ro.msg4banking.gateway;

import java.util.List;
import org.springframework.web.client.RestClient;
import ro.msg4banking.gateway.vo.Message;
import ro.msg4banking.gateway.vo.ModelResponse;

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

  public ModelResponse models() {
    return http.get()
        .uri(config.baseUrl() + "/models")
        .header("Authorization", "Bearer " + config.apiKey())
        .retrieve()
        .body(ModelResponse.class);
  }
}
