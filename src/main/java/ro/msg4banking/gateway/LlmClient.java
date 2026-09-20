package ro.msg4banking.gateway;

import java.net.http.HttpClient;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ro.msg4banking.gateway.vo.ChatRequest;
import ro.msg4banking.gateway.vo.ChatResponse;
import ro.msg4banking.gateway.vo.Message;

public class LlmClient {

  private final RestClient http =
      RestClient.builder()
          .requestFactory(
              new JdkClientHttpRequestFactory(
                  HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()))
          .build();
  private final Config config;

  public LlmClient(Config config) {
    this.config = config;
  }

  public Message chat(List<Message> messages, List<Tool> tools) {
    ChatRequest request =
        new ChatRequest(
            config.model(),
            messages,
            tools.isEmpty() ? null : tools.stream().map(Tool::definition).toList());
    ChatResponse response =
        http.post()
            .uri(config.baseUrl() + "/chat/completions")
            .header("Authorization", "Bearer " + config.apiKey())
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(ChatResponse.class);
    return response.choices().getFirst().message();
  }
}
