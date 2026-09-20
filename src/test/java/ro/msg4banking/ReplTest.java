package ro.msg4banking;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import ro.msg4banking.gateway.Config;
import ro.msg4banking.gateway.LlmClient;

@WireMockTest
class ReplTest {

  @Test
  void answersEveryLineUntilExit(WireMockRuntimeInfo wm) {
    stubFor(post("/chat/completions").willReturn(okJson(LlmResponses.text("Hi there!"))));
    Agent agent =
        new Agent(new LlmClient(new Config(wm.getHttpBaseUrl(), "key", "model")), List.of());
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    new Repl(agent)
        .run(new Scanner("Hello\nHow are you?\nexit\nNever asked\n"), new PrintStream(out));

    assertTrue(out.toString().contains("Hi there!"));
    verify(2, postRequestedFor(urlEqualTo("/chat/completions")));
  }
}
