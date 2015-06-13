package interfaces;

import exceptions.InvalidWordException;

import java.util.List;

public interface IAutomaton {
    public boolean test(String word) throws InvalidWordException;
    public List<IState> getStates();
    public IAlphabet getAlphabet();
}