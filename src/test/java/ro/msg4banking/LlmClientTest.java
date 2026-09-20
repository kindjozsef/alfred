package ro.msg4banking;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import ro.msg4banking.gateway.Config;
import ro.msg4banking.gateway.LlmClient;
import ro.msg4banking.gateway.vo.Message;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest
class LlmClientTest {

  @Test
  void sendsTheMessagesAndReturnsTheAnswer(WireMockRuntimeInfo wm) {
    stubFor(post("/chat/completions").willReturn(okJson(LlmResponses.text("Hello!"))));
    LlmClient llm = new LlmClient(new Config(wm.getHttpBaseUrl(), "test-key", "test-model"));

    Message answer = llm.chat(List.of(Message.user("Hi")));

    assertEquals("Hello!", answer.content());
    verify(
        postRequestedFor(urlEqualTo("/chat/completions"))
            .withHeader("Authorization", equalTo("Bearer test-key"))
            .withRequestBody(matchingJsonPath("$.model", equalTo("test-model")))
            .withRequestBody(matchingJsonPath("$.messages[0].role", equalTo("user")))
            .withRequestBody(matchingJsonPath("$.messages[0].content", equalTo("Hi"))));
  }
}
