package ro.msg4banking;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
    name = "alfred",
    mixinStandardHelpOptions = true,
    versionProvider = App.ManifestVersion.class,
    description = "Do something")
public class App implements Callable<Integer> {
  @CommandLine.Parameters(index = "0", description = "The file to be processed")
  private java.io.File file;

  @CommandLine.Option(
      names = {"-n", "--count"},
      description = "Number of repetitions.")
  private int count = 1;

  @Override
  public Integer call() {
    for (int i = 0; i < count; i++) {
      System.out.println(("processing: ") + file);
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
