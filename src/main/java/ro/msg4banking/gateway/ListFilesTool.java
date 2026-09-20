package ro.msg4banking.gateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListFilesTool implements Tool {

  private static final Set<String> SKIPPED = Set.of(".git", ".gradle", ".idea", "build");

  private final Path workdir;

  public ListFilesTool(Path workdir) {
    this.workdir = workdir;
  }

  @Override
  public String name() {
    return "list_files";
  }

  @Override
  public String description() {
    return "List every file of the project. The paths are relative to the project root.";
  }

  @Override
  public Map<String, Object> parameters() {
    return Map.of("type", "object", "properties", Map.of());
  }

  @Override
  public String execute(String arguments) {
    try (Stream<Path> files = Files.walk(workdir)) {
      return files
          .filter(Files::isRegularFile)
          .map(workdir::relativize)
          .filter(path -> !SKIPPED.contains(path.getName(0).toString()))
          .map(Path::toString)
          .sorted()
          .collect(Collectors.joining("\n"));
    } catch (IOException e) {
      return "Error: " + e.getMessage();
    }
  }
}
