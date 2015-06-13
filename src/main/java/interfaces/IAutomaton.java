package interfaces;

import exceptions.InvalidWordException;

public interface IAutomaton {
  public boolean test(String word) throws InvalidWordException;
}