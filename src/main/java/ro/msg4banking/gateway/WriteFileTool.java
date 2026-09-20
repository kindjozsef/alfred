package ro.msg4banking.gateway;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import tools.jackson.databind.json.JsonMapper;

public class WriteFileTool implements Tool {

  private static final JsonMapper JSON = new JsonMapper();

  private final Path workdir;

  record Arguments(String path, String content) {}

  public WriteFileTool(Path workdir) {
    this.workdir = workdir;
  }

  @Override
  public String name() {
    return "write_file";
  }

  @Override
  public String description() {
    return "Create or overwrite a text file of the project with the given content.";
  }

  @Override
  public Map<String, Object> parameters() {
    return Map.of(
        "type", "object",
        "properties",
            Map.of(
                "path",
                Map.of("type", "string", "description", "Path relative to the project root"),
                "content",
                Map.of("type", "string", "description", "The complete new content of the file")),
        "required", List.of("path", "content"));
  }

  @Override
  public String execute(String arguments) {
    throw new UnsupportedOperationException(
        "Step 5: write the content to the file (create missing folders too)"
            + " and return a short confirmation");
  }
}
