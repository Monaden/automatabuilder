package interfaces;

import exceptions.InvalidWordException;
import java.io.PrintStream;

public interface IAutomaton {
  public boolean test(String word) throws InvalidWordException;
  public void printTo(PrintStream ps);
}