package ro.msg4banking;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import ro.msg4banking.gateway.Config;
import ro.msg4banking.gateway.LlmClient;

@WireMockTest
class AgentMemoryTest {

  @Test
  void sendsThePreviousQuestionsAndAnswersAgain(WireMockRuntimeInfo wm) {
    stubFor(post("/chat/completions").willReturn(okJson(LlmResponses.text("Hi Jozsef!"))));
    Agent agent =
        new Agent(new LlmClient(new Config(wm.getHttpBaseUrl(), "key", "model")), List.of());

    agent.ask("My name is Jozsef.");
    agent.ask("What is my name?");

    verify(
        1,
        postRequestedFor(urlEqualTo("/chat/completions"))
            .withRequestBody(containing("My name is Jozsef."))
            .withRequestBody(containing("Hi Jozsef!"))
            .withRequestBody(containing("What is my name?")));
  }
}
