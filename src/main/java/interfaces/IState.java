package interfaces;

import automatabuilder.Symbol;

public interface IState {
    public IState transition(Symbol a);
    public String getName();
    public boolean isFinal();
}