package ro.msg4banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ro.msg4banking.gateway.WriteFileTool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WriteFileToolTest {

  @Test
  void overwritesAFile(@TempDir Path dir) throws IOException {
    Files.writeString(dir.resolve("Hello.java"), "class Hello {}");

    new WriteFileTool(dir).execute("{\"path\":\"Hello.java\",\"content\":\"class Hi {}\"}");

    assertEquals("class Hi {}", Files.readString(dir.resolve("Hello.java")));
  }

  @Test
  void createsANewFileInANewFolder(@TempDir Path dir) throws IOException {
    new WriteFileTool(dir).execute("{\"path\":\"src/Price.java\",\"content\":\"class Price {}\"}");

    assertEquals("class Price {}", Files.readString(dir.resolve("src/Price.java")));
  }
}
