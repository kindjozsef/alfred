package ro.msg4banking;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import ro.msg4banking.gateway.*;

@CommandLine.Command(
    name = "alfred",
    mixinStandardHelpOptions = true,
    versionProvider = App.ManifestVersion.class,
    description = "A tiny coding agent that works on the project in --workdir.")
public class App implements Callable<Integer> {

  @CommandLine.Parameters(
      index = "0",
      arity = "0..1",
      description = "What you want to ask alfred. Without it alfred starts an interactive session.")
  private String task;

  @CommandLine.Option(
      names = {"-w", "--workdir"},
      description = "The project alfred works on. Default: ${DEFAULT-VALUE}")
  private Path workdir = Path.of("../refactorme");

  @Override
  public Integer call() {
    List<Tool> tools =
        List.of(new ListFilesTool(workdir), new ReadFileTool(workdir), new WriteFileTool(workdir));
    Agent agent = new Agent(new LlmClient(Config.load()), tools);
    if (task != null) {
      System.out.println(agent.ask(task));
    } else {
      new Repl(agent).run(new Scanner(System.in), System.out);
    }
    return 0;
  }

  static class ManifestVersion implements CommandLine.IVersionProvider {
    public String[] getVersion() {
      String v = App.class.getPackage().getImplementationVersion();
      return new String[] {"alfred " + (v == null ? "dev" : v)};
    }
  }

  public static void main(String[] args) {
    System.exit(new CommandLine(new App()).execute(args));
  }
}
