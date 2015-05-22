package interfaces;

import automatabuilder.Symbol;

import java.util.List;

public interface IState {
    public IState transition(Symbol a);
    public String getName();
    public List<ITransition> getTransitions();
    public boolean isFinal();
}