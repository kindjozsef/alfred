package ro.msg4banking.gateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import tools.jackson.databind.json.JsonMapper;

public class ReadFileTool implements Tool {

  private static final JsonMapper JSON = new JsonMapper();

  private final Path workdir;

  record Arguments(String path) {}

  public ReadFileTool(Path workdir) {
    this.workdir = workdir;
  }

  @Override
  public String name() {
    return "read_file";
  }

  @Override
  public String description() {
    return "Read a text file of the project.";
  }

  @Override
  public Map<String, Object> parameters() {
    return Map.of(
        "type", "object",
        "properties",
            Map.of(
                "path",
                Map.of("type", "string", "description", "Path relative to the project root")),
        "required", List.of("path"));
  }

  @Override
  public String execute(String arguments) {
    Arguments args = JSON.readValue(arguments, Arguments.class);
    try {
      return Files.readString(workdir.resolve(args.path()));
    } catch (IOException e) {
      return "Error: cannot read " + args.path();
    }
  }
}
