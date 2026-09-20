package ro.msg4banking;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ro.msg4banking.gateway.Config;
import ro.msg4banking.gateway.LlmClient;
import ro.msg4banking.gateway.ReadFileTool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest
class AgentToolLoopTest {

  @Test
  void runsTheToolAndSendsTheResultBack(WireMockRuntimeInfo wm, @TempDir Path dir)
      throws IOException {
    Files.writeString(dir.resolve("Hello.java"), "class Hello {}");
    stubFor(
        post("/chat/completions")
            .inScenario("tool loop")
            .whenScenarioStateIs(STARTED)
            .willReturn(
                okJson(LlmResponses.toolCall("call_1", "read_file", "{\"path\":\"Hello.java\"}")))
            .willSetStateTo("file read"));
    stubFor(
        post("/chat/completions")
            .inScenario("tool loop")
            .whenScenarioStateIs("file read")
            .willReturn(okJson(LlmResponses.text("Hello.java has an empty class."))));
    LlmClient llm = new LlmClient(new Config(wm.getHttpBaseUrl(), "key", "model"));
    Agent agent = new Agent(llm, List.of(new ReadFileTool(dir)));

    String answer = agent.ask("What is in Hello.java?");

    assertEquals("Hello.java has an empty class.", answer);
    verify(2, postRequestedFor(urlEqualTo("/chat/completions")));
    verify(
        postRequestedFor(urlEqualTo("/chat/completions"))
            .withRequestBody(matchingJsonPath("$.tools[0].function.name", equalTo("read_file")))
            .withRequestBody(matchingJsonPath("$.messages[2].tool_calls[0].id", equalTo("call_1")))
            .withRequestBody(matchingJsonPath("$.messages[3].role", equalTo("tool")))
            .withRequestBody(matchingJsonPath("$.messages[3].tool_call_id", equalTo("call_1")))
            .withRequestBody(matchingJsonPath("$.messages[3].content", equalTo("class Hello {}"))));
  }
}
