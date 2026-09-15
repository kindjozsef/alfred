package ro.msg4banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class AppTest {

  @Test
  void helpExitsZero() {
    var out = new StringWriter();
    int test = 1;
    int code = new CommandLine(new App()).setOut(new PrintWriter(out)).execute("--help");
    assertEquals(0, code);
    assertTrue(out.toString().contains("alfred"));
  }
}
