package ro.msg4banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ro.msg4banking.gateway.ReadFileTool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadFileToolTest {

  @Test
  void readsAFileOfTheProject(@TempDir Path dir) throws IOException {
    Files.writeString(dir.resolve("Hello.java"), "class Hello {}");

    String result = new ReadFileTool(dir).execute("{\"path\":\"Hello.java\"}");

    assertEquals("class Hello {}", result);
  }

  @Test
  void returnsAnErrorForAMissingFile(@TempDir Path dir) {
    String result = new ReadFileTool(dir).execute("{\"path\":\"Missing.java\"}");

    assertTrue(result.startsWith("Error"));
  }
}
