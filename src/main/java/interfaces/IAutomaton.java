package interfaces;

import exceptions.InvalidWordException;

import java.io.File;
import java.io.PrintStream;

public interface IAutomaton {
  public boolean test(String word) throws InvalidWordException;
  public String testFile(File testFile);
  public void printTo(PrintStream ps);
}