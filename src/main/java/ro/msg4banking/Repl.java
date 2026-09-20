package ro.msg4banking;

import java.io.PrintStream;
import java.util.Scanner;

public class Repl {

  private final Agent agent;

  public Repl(Agent agent) {
    this.agent = agent;
  }

  public void run(Scanner in, PrintStream out) {
    while (true) {
      out.print("> ");
      if (!in.hasNextLine()) {
        return;
      }
      String line = in.nextLine().trim();
      if (line.equals("exit")) {
        return;
      }
      if (!line.isEmpty()) {
        out.println(agent.ask(line));
      }
    }
  }
}
