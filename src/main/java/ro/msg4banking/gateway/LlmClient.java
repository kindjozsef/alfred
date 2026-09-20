package ro.msg4banking.gateway;

import java.net.http.HttpClient;
import java.util.List;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
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

  public Message chat(List<Message> messages) {

    // use a post call, set a header named Authorization with the value Bearer {api-key}
    // set the content Type to MediaType.APPLICATION_JSON
    // the body should be the chat request
    // recieve the Chat Response

    throw new UnsupportedOperationException(
        "Step 1: POST a ChatRequest to {base-url}/chat/completions"
            + " and return the message of the first choice");
  }
}
