package ro.msg4banking;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import ro.msg4banking.gateway.Config;
import ro.msg4banking.gateway.LlmClient;

@CommandLine.Command(
    name = "alfred",
    mixinStandardHelpOptions = true,
    versionProvider = App.ManifestVersion.class,
    description = "A tiny coding agent that works on the project in --workdir.")
public class App implements Callable<Integer> {

  @CommandLine.Parameters(index = "0", description = "What you want to ask alfred.")
  private String task;

  @Override
  public Integer call() {
    Agent agent = new Agent(new LlmClient(Config.load()));
    System.out.println(agent.ask(task));
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
