package ro.msg4banking;

import java.io.PrintStream;
import java.util.Scanner;

public class Repl {

  private final Agent agent;

  public Repl(Agent agent) {
    this.agent = agent;
  }

  public void run(Scanner in, PrintStream out) {

    // In an infinite loop
    // read the line: if it is "exit" or empty then break the loop
    // otherwise, ask the agent and print the answer
    // you can use in.nextLine() to read a line and out.println() to print a line

    throw new UnsupportedOperationException(
        "Step 3: read a line, ask the agent, print the answer, repeat until 'exit'");
  }
}
